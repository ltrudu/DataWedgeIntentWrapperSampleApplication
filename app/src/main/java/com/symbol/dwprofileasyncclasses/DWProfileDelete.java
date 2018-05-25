package com.symbol.dwprofileasyncclasses;

import android.content.Context;

import com.symbol.datacapturereceiver.DataWedgeConstants;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileDelete extends DWProfileCommandBase {

    public DWProfileDelete(Context aContext) {
        super(aContext);
    }

    public void execute(DWProfileDeleteSettings settings, onProfileCommandResult callback)
    {
        /*
        Call base class execute to register command result
        broadcast receiver and launch timeout mechanism
         */
        super.execute(settings, callback);

        /*
        Create the profile
         */
        createProfile(settings);
     }

    private void createProfile(DWProfileDeleteSettings settings)
    {
        // Delete profile using intent DELETE_PROFILE
        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2,
                DataWedgeConstants.EXTRA_DELETE_PROFILE,
                settings.mProfileName);

    }
}
