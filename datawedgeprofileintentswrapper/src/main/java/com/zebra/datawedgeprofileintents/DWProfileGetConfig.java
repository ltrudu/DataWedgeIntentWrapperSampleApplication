package com.zebra.datawedgeprofileintents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileGetConfig extends DWProfileBase {

    public DWProfileGetConfig(Context aContext) {
        super(aContext);
        mBroadcastReceiver = new checkProfileReceiver();
    }

    /*
        An interface callback to be informed of the result
        when checking if a profile exists
         */
    public interface onGetProfileResult
    {
        void result(String profileName, Bundle extra);
        void timeOut(String profileName);
    }

    /*
    A store to keep the callback to be fired when we will get the
    result of the intent
     */
    private onGetProfileResult mProfileGetConfigCallback = null;

    /*
    The receiver that we will register to retrieve DataWedge answer
     */
    private checkProfileReceiver mBroadcastReceiver = null;

    public void execute(DWProfileGetSettings settings, onGetProfileResult callback)
    {
        /*
        Launch timeout mechanism
         */
        super.execute(settings);

        mProfileGetConfigCallback = callback;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataWedgeConstants.ACTION_RESULT_DATAWEDGE_FROM_6_2);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        /*
        Register receiver for resutls
         */
        mContext.registerReceiver(mBroadcastReceiver, intentFilter);

        /*
        Ask for DataWedge profile list
         */
        sendDataWedgeIntentWithExtra(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2, DataWedgeConstants.EXTRA_GET_CONFIG, DataWedgeConstants.EXTRA_EMPTY);

    }

    private class checkProfileReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(DataWedgeConstants.EXTRA_RESULT_GET_CONFIG))
            {
                //  6.2 API to GetProfileList
                Bundle bundleConfig = intent.getBundleExtra(DataWedgeConstants.EXTRA_RESULT_GET_CONFIG);
                //  Profile list for 6.2 APIs
                if(mProfileGetConfigCallback != null)
                {
                    mProfileGetConfigCallback.result(mSettings.mProfileName, bundleConfig);
                    cleanAll();
                }
            }
        }
    }

    @Override
    protected void cleanAll()
    {
        mSettings.mProfileName = "";
        mProfileGetConfigCallback = null;
        mContext.unregisterReceiver(mBroadcastReceiver);
        super.cleanAll();
    }

    /*
    This is what will happen if Datawedge does not answer before the timeout
     */
    @Override
    protected void onTimeOut() {
        if(mProfileGetConfigCallback != null)
        {
            mProfileGetConfigCallback.timeOut(mSettings.mProfileName);
            cleanAll();
        }
    }
}
