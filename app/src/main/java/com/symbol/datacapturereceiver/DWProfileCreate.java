package com.symbol.datacapturereceiver;

import android.content.Context;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileCreate extends DWProfileCommandBase {
    protected static String mIntentAction = "com.symbol.datacapturereceiver.RECVR";
    protected static String mIntentCategory = "android.intent.category.DEFAULT";


    public DWProfileCreate(Context aContext, String aProfile, long aTimeOut) {
        super(aContext, aProfile, aTimeOut);
    }


    @Override
    public void execute(onProfileCommandResult callback)
    {
        /*
        Call base class execute to register command result
        broadcast receiver and launch timeout mechanism
         */
        super.execute(callback);

        /*
        Create the profile
         */
        createProfile(mProfileName);
     }

    private void createProfile(String profileName)
    {
        // On crée le profil via l'intent CREATE_PROFILE
        // NB : on peut envoyer cet intent sans soucis même si le profil existe déjà
        // On perdrait plus de temps si on recherchait si le profil existe avant de lancer la création
        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2,
                DataWedgeConstants.EXTRA_CREATE_PROFILE,
                profileName);

    }
}
