package com.zebra.datawedgeprofileintents;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileSwitchBarcodeParams extends DWProfileCommandBase {

    public DWProfileSwitchBarcodeParams(Context aContext) {
        super(aContext);
    }

    public void execute(DWProfileSwitchBarcodeParamsSettings settings, onProfileCommandResult callback)
    {
        /*
        Call base class execute to register command result
        broadcast receiver and launch timeout mechanism
         */
        super.execute(settings, callback);

        /*
        Create the profile
         */
        switchToContinuousMode(settings);
     }

    private void switchToContinuousMode(DWProfileSwitchBarcodeParamsSettings settings)
    {
        Bundle barcodeProps = new Bundle();
        if(settings.mAggressiveContinuousMode)
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
