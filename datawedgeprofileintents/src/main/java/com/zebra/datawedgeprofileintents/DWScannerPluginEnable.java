package com.zebra.datawedgeprofileintents;

import android.content.Context;

/**
 * Created by laure on 16/04/2018.
 */

public class DWScannerPluginEnable extends DWProfileCommandBase {

    public DWScannerPluginEnable(Context aContext) {
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
        Enable plugin
         */
        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.DWAPI_ACTION_SCANNERINPUTPLUGIN, DataWedgeConstants.EXTRA_PARAMETER, DataWedgeConstants.DWAPI_PARAMETER_SCANNERINPUTPLUGIN_ENABLE);
     }
}
