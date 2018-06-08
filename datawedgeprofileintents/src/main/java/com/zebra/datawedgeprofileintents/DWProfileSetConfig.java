package com.zebra.datawedgeprofileintents;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import static com.zebra.datawedgeprofileenums.DWScannerConfigEnumsHelper.*;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileSetConfig extends DWProfileCommandBase {
    public DWProfileSetConfig(Context aContext) {
        super(aContext);
    }

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
        profileConfig.putString("PROFILE_ENABLED", settings.mMB_ProfileEnabled ? "true" : "false");
        profileConfig.putString("CONFIG_MODE", MB_GetConfigMode(settings.mMB_ConfigMode));
        
        // Setup associated application and activities
        Bundle appConfig = new Bundle();
        appConfig.putString("PACKAGE_NAME", settings.mAPPL_PackageName);
        appConfig.putStringArray("ACTIVITY_LIST", ((settings.mAPPL_ActivityList != null && settings.mAPPL_ActivityList.length > 0) ? settings.mAPPL_ActivityList : new String[]{"*"}));
        profileConfig.putParcelableArray("APP_LIST", new Bundle[]{appConfig});

        // Array that will hold all the DW plugins
        ArrayList<Bundle> pluginConfigs = new ArrayList<Bundle>();

        // Add barcode plugin config
        pluginConfigs.add(getBarcodePluginConfig(settings));

        // Add keystroke plugin config (disabled in this case)
        pluginConfigs.add(getKeyStrokePluginConfig(settings));

        // Add BDF plugin if necessary
        if(settings.mBDF_Enabled)
        {
            pluginConfigs.add(getBDFPluginConfig(settings));
        }

        // Setup intent delivery by broadcast for this case
        pluginConfigs.add(getIntentPluginConfig(settings));

        // Send Plugin configuration intent
        profileConfig.putParcelableArrayList("PLUGIN_CONFIG", pluginConfigs);

        sendDataWedgeIntentWithExtraRequestResult(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2,
                DataWedgeConstants.EXTRA_SET_CONFIG,
                profileConfig);
    }

    private Bundle getBDFPluginConfig(DWProfileSetConfigSettings settings)
    {
        // Basic Data Formatting plugin configuration
        Bundle bdfPluginConfig = new Bundle();
        bdfPluginConfig.putString("PLUGIN_NAME","BDF");
        bdfPluginConfig.putString("RESET_CONFIG","true");
        bdfPluginConfig.putString("OUTPUT_PLUGIN_NAME","INTENT");

        // param_list bundle properties
        Bundle bParams = new Bundle();
        bParams.putString("bdf_enabled", settings.mBDF_Enabled ? "true" : "false");
        bParams.putString("bdf_prefix",settings.mBDF_Prefix);
        bParams.putString("bdf_suffix",settings.mBDF_Suffix);
        bParams.putString("bdf_send_data", settings.mBDF_Send_Data ? "true" : "false");
        bParams.putString("bdf_send_hex",settings.mBDF_Send_Hex ? "true" : "false");
        bParams.putString("bdf_send_tab",settings.mBDF_Send_Tab ? "true" : "false");
        bParams.putString("bdf_send_enter",settings.mBDF_Send_Enter ? "true" : "false");

        bdfPluginConfig.putBundle("PARAM_LIST", bParams);
        return bdfPluginConfig;
    }

    private Bundle getIntentPluginConfig(DWProfileSetConfigSettings settings)
    {
        // INTENT Plugin configuration
        Bundle intentPluginConfig = new Bundle();
        intentPluginConfig.putString("PLUGIN_NAME", "INTENT");
        intentPluginConfig.putString("RESET_CONFIG", "true");

        Bundle intentProps = new Bundle();
        intentProps.putString("intent_output_enabled", "true");
        intentProps.putString("intent_action", settings.mINT_IntentAction);
        intentProps.putString("intent_category", settings.mINT_IntentCategory);
        intentProps.putString("intent_delivery", "2");
        intentPluginConfig.putBundle("PARAM_LIST", intentProps);
        return intentPluginConfig;
    }

    private Bundle getKeyStrokePluginConfig(DWProfileSetConfigSettings settings)
    {
        // KEYSTROKE plugin configuration -> Disabled
        Bundle keystrokePluginConfig = new Bundle();
        keystrokePluginConfig.putString("PLUGIN_NAME", "KEYSTROKE");
        keystrokePluginConfig.putString("RESET_CONFIG", "true");
        Bundle keystrokeProps = new Bundle();
        keystrokeProps.putString("keystroke_output_enabled", "false");
        keystrokePluginConfig.putBundle("PARAM_LIST", keystrokeProps);
        return keystrokePluginConfig;
    }

    private Bundle getBarcodePluginConfig(DWProfileSetConfigSettings settings)
    {
        // Barcode plugin configuration
        Bundle barcodePluginConfig = new Bundle();
        barcodePluginConfig.putString("PLUGIN_NAME", "BARCODE");
        barcodePluginConfig.putString("RESET_CONFIG", "true");

        Bundle barcodeProps = new Bundle();
        // Use this for Datawedge < 6.7
        //barcodeProps.putString("scanner_selection", "AUTO");
        // Use this for Datawedge >= 6.7
        barcodeProps.putString("scanner_selection_by_identifier",SC_GetScannerIdentifier(settings.mSC_ScannerIdentifier));

        barcodeProps.putString("scanning_mode", SC_GetScanningMode(settings.mSC_ScanningMode));
        barcodeProps.putInt("multi_barcode_count", settings.mSC_Multi_Barcode_Count);
        //barcodeProps.putString("trigger-wakeup", settings.mSC_Trigger_WakeUp ? "true" : "false");
        barcodeProps.putString("scanner_input_enabled", settings.mSC_Scanner_Input_Enabled ? "true" : "false");

        barcodeProps.putString("aim_mode", "on");
        barcodeProps.putString("lcd_mode", "3");



        // Standard mode with beam timer and same/different timeout
        barcodeProps.putString("aim_type", "3");
        barcodeProps.putInt("beam_timer", 5000);
        barcodeProps.putString("different_barcode_timeout", "500");
        barcodeProps.putString("same_barcode_timeout", "500");

        barcodePluginConfig.putBundle("PARAM_LIST", barcodeProps);
        return barcodePluginConfig;
    }

}
