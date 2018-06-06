package com.zebra.datawedgeprofileintents;

import android.content.Context;

/**
 * Created by laure on 16/04/2018.
 */

public class DWScannerStopScan extends DWProfileCommandBase {

    public DWScannerStopScan(Context aContext) {
        super(aContext);
    }

    public void execute(DWProfileBaseSettings settings, onProfileCommandResult callback)
    {
        // This command does not need a timeout
        // Ensure that the mechanism is disabled
        settings.mEnableTimeOutMechanism = false;

        /*
        Call base class execute to register command result
        broadcast receiver and launch timeout mechanism
         */
        super.execute(settings, callback);

        /*
        Stop Scan
         */
        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.DWAPI_ACTION_SOFTSCANTRIGGER, DataWedgeConstants.EXTRA_PARAMETER, DataWedgeConstants.DWAPI_STOP_SCANNING);
     }
}
