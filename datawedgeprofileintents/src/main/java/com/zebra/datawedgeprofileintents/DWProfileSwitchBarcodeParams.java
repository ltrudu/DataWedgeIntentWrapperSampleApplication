package com.zebra.datawedgeprofileintents;

import android.content.Context;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileSwitchBarcodeParams extends DWProfileCommandBase {

    public DWProfileSwitchBarcodeParams(Context aContext) {
        super(aContext);
    }

    public void execute(DWProfileSwitchBarcodeParamsSettings originalSettings, DWProfileSwitchBarcodeParamsSettings targetSettings, onProfileCommandResult callback)
    {
        /*
        Call base class execute to register command result
        broadcast receiver and launch timeout mechanism
         */
        super.execute(targetSettings, callback);

        /*
        Create the profile
         */
        switchToContinuousMode(originalSettings, targetSettings);
     }

    private void switchToContinuousMode(DWProfileSwitchBarcodeParamsSettings originalSettings, DWProfileSwitchBarcodeParamsSettings targetSettings)
    {
        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2, DataWedgeConstants.EXTRA_SWITCH_SCANNER_PARAMS, targetSettings.ScannerPlugin.getBarcodePluginBundleForSwitchParams(originalSettings.ScannerPlugin));
    }
}
