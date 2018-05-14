package com.symbol.datacapturereceiver;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileSetConfig extends DWProfileCommandBase {
    protected static String mIntentAction = "com.symbol.datacapturereceiver.RECVR";
    protected static String mIntentCategory = "android.intent.category.DEFAULT";

    protected boolean mAggressiveContinuousMode = false;

    public DWProfileSetConfig(boolean aggressiveContinuous, Context aContext, String aProfile, long aTimeOut) {
        super(aContext, aProfile, aTimeOut);
        mAggressiveContinuousMode = aggressiveContinuous;
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
        setProfileConfig(mProfileName);
     }

    private void setProfileConfig(String profileName)
    {
        // (Re)Configuration du profil via l'intent SET_PROFILE
        // NB : on peut envoyer cet intent sans soucis même si le profil est déjà configuré
        Bundle profileConfig = new Bundle();
        profileConfig.putString("PROFILE_NAME", profileName);
        profileConfig.putString("PROFILE_ENABLED", "true");
        profileConfig.putString("CONFIG_MODE", "UPDATE");

        // Configuration des applications du profil
        Bundle appConfig = new Bundle();
        appConfig.putString("PACKAGE_NAME", mContext.getPackageName());
        appConfig.putStringArray("ACTIVITY_LIST", new String[]{"*"});
        profileConfig.putParcelableArray("APP_LIST", new Bundle[]{appConfig});

        // Configuration des différents plugins
        ArrayList<Bundle> pluginConfigs = new ArrayList<Bundle>();

        // Configuration du plugin BARCODE
        Bundle barcodePluginConfig = new Bundle();
        barcodePluginConfig.putString("PLUGIN_NAME", "BARCODE");
        barcodePluginConfig.putString("RESET_CONFIG", "true");

        Bundle barcodeProps = new Bundle();
        barcodeProps.putString("aim_mode", "on");
        barcodeProps.putString("lcd_mode", "3");

        // Use this for Datawedge < 6.7
        //barcodeProps.putString("scanner_selection", "auto");

        // Use this for Datawedge >= 6.7
        barcodeProps.putString("scanner_selection_by_identifier","INTERNAL_IMAGER");

        if (mAggressiveContinuousMode) {
            // Super aggressive continuous mode without beam timer and no timeouts
            barcodeProps.putString("aim_type", "5");
            barcodeProps.putInt("beam_timer", 0);
            barcodeProps.putString("different_barcode_timeout", "0");
            barcodeProps.putString("same_barcode_timeout", "0");

        } else {
            // Standard mode with beam timer and same/different timeout
            barcodeProps.putString("aim_type", "3");
            barcodeProps.putInt("beam_timer", 5000);
            barcodeProps.putString("different_barcode_timeout", "500");
            barcodeProps.putString("same_barcode_timeout", "500");
        }
        barcodePluginConfig.putBundle("PARAM_LIST", barcodeProps);
        pluginConfigs.add(barcodePluginConfig);


        // Configuration du plugin KEYSTROKE
        Bundle keystrokePluginConfig = new Bundle();
        keystrokePluginConfig.putString("PLUGIN_NAME", "KEYSTROKE");
        keystrokePluginConfig.putString("RESET_CONFIG", "true");
        Bundle keystrokeProps = new Bundle();
        keystrokeProps.putString("keystroke_output_enabled", "false");
        keystrokePluginConfig.putBundle("PARAM_LIST", keystrokeProps);
        pluginConfigs.add(keystrokePluginConfig);

        // Configuration du plugin INTENT
        Bundle intentPluginConfig = new Bundle();
        intentPluginConfig.putString("PLUGIN_NAME", "INTENT");
        intentPluginConfig.putString("RESET_CONFIG", "true");
        Bundle intentProps = new Bundle();
        intentProps.putString("intent_output_enabled", "true");
        intentProps.putString("intent_action", mIntentAction);
        intentProps.putString("intent_category", mIntentCategory);
        intentProps.putString("intent_delivery", "2");
        intentPluginConfig.putBundle("PARAM_LIST", intentProps);
        pluginConfigs.add(intentPluginConfig);

        // Envoi d'intent de configuration du profil
        profileConfig.putParcelableArrayList("PLUGIN_CONFIG", pluginConfigs);

        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2,
                DataWedgeConstants.EXTRA_SET_CONFIG,
                profileConfig);
    }
}
