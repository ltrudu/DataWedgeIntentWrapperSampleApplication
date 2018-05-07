package com.symbol.datacapturereceiver;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileSwitchContinuousMode extends DWProfileCommandBase {

    private boolean mContinuousMode = false;

    public DWProfileSwitchContinuousMode(Context aContext, String aProfile, boolean aContinuousMode, long aTimeOut) {
        super(aContext, aProfile, aTimeOut);
        mContinuousMode = aContinuousMode;
    }

    public void execute(onProfileCommandResult callbacks)
    {
        /*
        Call base class execute to register command result
        broadcast receiver and launch timeout mechanism
         */
        super.execute(callbacks);

        /*
        Create the profile
         */
        switchToContinuousMode(mProfileName);
     }

    private void switchToContinuousMode(String profileName)
    {
        Bundle barcodeProps = new Bundle();
        if(mContinuousMode)
        {
            barcodeProps.putString("aim_type", "5");
            barcodeProps.putString("beam_timer", "0");
            barcodeProps.putString("different_barcode_timeout", "0");
            barcodeProps.putString("same_barcode_timeout", "0");
        }
        else
        {
            barcodeProps.putString("aim_type", "0");
            barcodeProps.putString("beam_timer", "5000");
            barcodeProps.putString("different_barcode_timeout", "500");
            barcodeProps.putString("same_barcode_timeout", "500");
        }

        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2, DataWedgeConstants.EXTRA_SWITCH_SCANNER_PARAMS, barcodeProps);
    }
}
