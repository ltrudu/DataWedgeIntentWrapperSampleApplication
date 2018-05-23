package com.symbol.dwprofileasyncclasses;

import android.content.Context;

import com.symbol.datacapturereceiver.DataWedgeConstants;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileCreate extends DWProfileCommandBase {

    public DWProfileCreate(Context aContext) {
        super(aContext);
    }

    public void execute(DWProfileCreateSettings settings, onProfileCommandResult callback)
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

    private void createProfile(DWProfileCreateSettings settings)
    {
        // On crée le profil via l'intent CREATE_PROFILE
        // NB : on peut envoyer cet intent sans soucis même si le profil existe déjà
        // On perdrait plus de temps si on recherchait si le profil existe avant de lancer la création
        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2,
                DataWedgeConstants.EXTRA_CREATE_PROFILE,
                settings.mProfileName);

    }
}
