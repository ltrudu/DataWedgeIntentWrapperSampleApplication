package com.zebra.datawedgeprofileintents;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class DWScanReceiver {

    private String mIntentAction = "";
    private String mIntentCategory = "";
    private boolean mShowSpecialCharacters = false;

    /*
    An interface callback to receive the scanned data
     */
    public interface onScannedData
    {
        void scannedData(String source, String data, String typology);
    }

    private onScannedData mOnScannedDataCallback = null;

    /**
     * Local Broadcast receiver
     */
    private BroadcastReceiver mMessageReceiver = null;

    public DWScanReceiver(Activity myActivity, String intentAction, String intentCategory)
    {
        mIntentAction = intentAction;
        mIntentCategory = intentCategory;

        // Create the message receiver
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleDecodeData(intent);
            }
        };
    }

    public void setScannedDataCallback(onScannedData scannedDataCallback, boolean showSpecialChars)
    {
        mOnScannedDataCallback = scannedDataCallback;
        mShowSpecialCharacters = showSpecialChars;
    }

    public void startReceive(Activity myActivity)
    {
        // Register the internal broadcast receiver when we are alive
        IntentFilter myFilter = new IntentFilter();
        myFilter.addAction(mIntentAction);
        myFilter.addCategory(mIntentCategory);
        myActivity.getApplicationContext().registerReceiver(mMessageReceiver, myFilter);
    }

    public void stopReceive(Activity myActivity)
    {
        mIntentAction = "";
        mIntentCategory = "";
        mOnScannedDataCallback = null;
        // Unregister internal broadcast receiver when we are going in background
        myActivity.getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    // This method is responsible for getting the data from the intent
    // formatting it and adding it to the end of the edit box
    private boolean handleDecodeData(Intent i) {
        // check the intent action is for us
        if ( i.getAction().contentEquals(mIntentAction) ) {
            // define a string that will hold our output
            String out = "";
            // get the source of the data
            String source = i.getStringExtra(DataWedgeConstants.SOURCE_TAG);
            // save it to use later
            if (source == null) source = "scanner";
            // get the data from the intent
            String data = i.getStringExtra(DataWedgeConstants.DATA_STRING_TAG);

            String sLabelType = null;

            // check if the data has come from the barcode scanner
            if (source.equalsIgnoreCase("scanner")) {
                // check if there is anything in the data
                if (data != null && data.length() > 0) {
                    // we have some data, so let's get it's symbology
                    sLabelType = i.getStringExtra(DataWedgeConstants.LABEL_TYPE_TAG);
                    // check if the string is empty
                    if (sLabelType != null && sLabelType.length() > 0) {
                        // format of the label type string is LABEL-TYPE-SYMBOLOGY
                        // so let's skip the LABEL-TYPE- portion to get just the symbology
                        sLabelType = sLabelType.substring(11);
                    }
                    else {
                        // the string was empty so let's set it to "Unknown"
                        sLabelType = "Unknown";
                    }
                }
            }


            if(data != null && mShowSpecialCharacters)
            {
               data = showSpecialChars(data);
            }

            if(mOnScannedDataCallback != null)
            {
                mOnScannedDataCallback.scannedData(source, data, sLabelType);
            }

            return true;
        }
        return false;
    }

    private String showSpecialChars(String data)
    {
        String returnString="";
        char[] dataChar = data.toCharArray();
        for(char acar : dataChar)
        {
            if(!Character.isISOControl(acar))
            {
                returnString += acar;
            }
            else
            {
                returnString += "["+(int)acar+"]";
            }
        }

        return returnString;
    }

}
