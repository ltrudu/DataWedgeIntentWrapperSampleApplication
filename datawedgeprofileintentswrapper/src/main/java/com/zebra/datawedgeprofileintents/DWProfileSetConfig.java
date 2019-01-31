package com.zebra.datawedgeprofileintents;

import android.content.Context;
import android.os.Bundle;

import com.zebra.datawedgeprofileenums.MB_E_CONFIG_MODE;
import com.zebra.datawedgeprofileintents.SettingsPlugins.BaseSettings;

import java.util.ArrayList;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileSetConfig extends DWProfileCommandBase {
    public DWProfileSetConfig(Context aContext) {
        super(aContext);
    }

    private DWProfileSetConfigSettings mBaseSettings = new DWProfileSetConfigSettings();

    public void execute(DWProfileSetConfigSettings settings, onProfileCommandResult callback)
    {
        /*
        Call base class execute to register command result
        broadcast receiver and launch timeout mechanism
         */
        super.execute(settings, callback);

        /*
        Create the profile
         */
        setProfileConfig(settings);
     }

    private void setProfileConfig(DWProfileSetConfigSettings settings)
    {
        Bundle profileConfig = new Bundle();
        profileConfig.putString("PROFILE_NAME", settings.mProfileName);
        profileConfig.putString("PROFILE_ENABLED", settings.MainBundle.PROFILE_ENABLED ? "true" : "false");
        profileConfig.putString("CONFIG_MODE", settings.MainBundle.CONFIG_MODE.toString());
        
        // Setup associated application and activities
        Bundle appConfig = new Bundle();
        appConfig.putString("PACKAGE_NAME", settings.MainBundle.PACKAGE_NAME);
        appConfig.putStringArray("ACTIVITY_LIST", ((settings.MainBundle.ACTIVITY_LIST != null && settings.MainBundle.ACTIVITY_LIST.length > 0) ? settings.MainBundle.ACTIVITY_LIST : new String[]{"*"}));
        // We only add the app list if we are "not in update" mode.
        // Having an APP_LIST set when in update mode throws an APP_ALREADY_ASSOCIATED error.
        if(settings.MainBundle.CONFIG_MODE != MB_E_CONFIG_MODE.UPDATE)
            profileConfig.putParcelableArray("APP_LIST", new Bundle[]{appConfig});

        // Array that will hold all the DW plugins
        ArrayList<Bundle> pluginConfigs = new ArrayList<Bundle>();

        // Add barcode plugin config
        pluginConfigs.add(settings.ScannerPlugin.getBarcodePluginBundleForSetConfig(true, BaseSettings.mScannerBaseSettings));

        // Add keystroke plugin config (disabled in this case)
        pluginConfigs.add(settings.KeystrokePlugin.getKeyStrokePluginBundle(true));

        // Add BDF plugin
        pluginConfigs.add(settings.BasicDataFormatting.getBDFPluginBundle(true, "INTENT"));

        // Setup intent delivery by broadcast for this case
        pluginConfigs.add(settings.IntentPlugin.getIntentPluginBundle(true));

        // Send Plugin configuration intent
        profileConfig.putParcelableArrayList("PLUGIN_CONFIG", pluginConfigs);

        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2,
                DataWedgeConstants.EXTRA_SET_CONFIG,
                profileConfig);
    }

}
