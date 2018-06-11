package com.zebra.datawedgeprofileintents;

import android.content.Context;
import android.os.Bundle;

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
        profileConfig.putString("PROFILE_ENABLED", settings.mMB_ProfileEnabled ? "true" : "false");
        profileConfig.putString("CONFIG_MODE", settings.mMB_ConfigMode.toString());
        
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
        if(settings.mBDF_Enabled != mBaseSettings.mBDF_Enabled)
            bParams.putString("bdf_enabled", settings.mBDF_Enabled ? "true" : "false");
        if(settings.mBDF_Prefix != mBaseSettings.mBDF_Prefix)
            bParams.putString("bdf_prefix",settings.mBDF_Prefix);
        if(settings.mBDF_Suffix != mBaseSettings.mBDF_Suffix)
            bParams.putString("bdf_suffix",settings.mBDF_Suffix);
        if(settings.mBDF_Send_Data != mBaseSettings.mBDF_Send_Data)
            bParams.putString("bdf_send_data", settings.mBDF_Send_Data ? "true" : "false");
        if(settings.mBDF_Send_Hex != mBaseSettings.mBDF_Send_Hex)
            bParams.putString("bdf_send_hex",settings.mBDF_Send_Hex ? "true" : "false");
        if(settings.mBDF_Send_Tab != mBaseSettings.mBDF_Send_Tab)
            bParams.putString("bdf_send_tab",settings.mBDF_Send_Tab ? "true" : "false");
        if(settings.mBDF_Send_Enter != mBaseSettings.mBDF_Send_Enter)
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
        if(settings.mSC_ScannerIdentifier != mBaseSettings.mSC_ScannerIdentifier)
            barcodeProps.putString("scanner_selection_by_identifier",settings.mSC_ScannerIdentifier.toString());

        if(settings.mSC_ScanningMode != mBaseSettings.mSC_ScanningMode)
            barcodeProps.putString("scanning_mode", settings.mSC_ScanningMode.toString());
        if(settings.mSC_Multi_Barcode_Count != mBaseSettings.mSC_Multi_Barcode_Count)
            barcodeProps.putInt("multi_barcode_count", settings.mSC_Multi_Barcode_Count);
        if(settings.mSC_Trigger_WakeUp != mBaseSettings.mSC_Trigger_WakeUp)
            barcodeProps.putString("trigger-wakeup", settings.mSC_Trigger_WakeUp ? "true" : "false");
        if(settings.mSC_Scanner_Input_Enabled != mBaseSettings.mSC_Scanner_Input_Enabled)
            barcodeProps.putString("scanner_input_enabled", settings.mSC_Scanner_Input_Enabled ? "true" : "false");

        setupDecoders(barcodeProps, settings);

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

    private void setupDecoders(Bundle barcodeProps, DWProfileSetConfigSettings settings) {
        if(settings.mSC_Decoder_Australian_Postal != mBaseSettings.mSC_Decoder_Australian_Postal) barcodeProps.putBoolean("decoder_australian_postal",settings.mSC_Decoder_Australian_Postal);
        if(settings.mSC_Decoder_Aztec != mBaseSettings.mSC_Decoder_Aztec) barcodeProps.putBoolean("decoder_aztec",settings.mSC_Decoder_Aztec);
        if(settings.mSC_Decoder_Canadian_Postal != mBaseSettings.mSC_Decoder_Canadian_Postal) barcodeProps.putBoolean("decoder_canadian_postal",settings.mSC_Decoder_Canadian_Postal);
        if(settings.mSC_Decoder_Chinese_2of5 != mBaseSettings.mSC_Decoder_Chinese_2of5) barcodeProps.putBoolean("decoder_chinese_2of5",settings.mSC_Decoder_Chinese_2of5);
        if(settings.mSC_Decoder_Codabar != mBaseSettings.mSC_Decoder_Codabar) barcodeProps.putBoolean("decoder_codabar",settings.mSC_Decoder_Codabar);
        if(settings.mSC_Decoder_Codabar_Clsi_Editing != mBaseSettings.mSC_Decoder_Codabar_Clsi_Editing) barcodeProps.putBoolean("decoder_codabar_clsi_editing",settings.mSC_Decoder_Codabar_Clsi_Editing);
        if(settings.mSC_Decoder_Codabar_Length1 != mBaseSettings.mSC_Decoder_Codabar_Length1) barcodeProps.putInt("decoder_codabar_length1",settings.mSC_Decoder_Codabar_Length1);
        if(settings.mSC_Decoder_Codabar_Length2 != mBaseSettings.mSC_Decoder_Codabar_Length2) barcodeProps.putInt("decoder_codabar_length2",settings.mSC_Decoder_Codabar_Length2);
        if(settings.mSC_Decoder_Codabar_Notis_Editing != mBaseSettings.mSC_Decoder_Codabar_Notis_Editing) barcodeProps.putBoolean("decoder_codabar_notis_editing",settings.mSC_Decoder_Codabar_Notis_Editing);
        if(settings.mSC_Decoder_Codabar_Redundancy != mBaseSettings.mSC_Decoder_Codabar_Redundancy) barcodeProps.putBoolean("decoder_codabar_redundancy",settings.mSC_Decoder_Codabar_Redundancy);
        if(settings.mSC_Decoder_Code11 != mBaseSettings.mSC_Decoder_Code11) barcodeProps.putBoolean("decoder_code11",settings.mSC_Decoder_Code11);
        if(settings.mSC_Decoder_Code11_Length1 != mBaseSettings.mSC_Decoder_Code11_Length1) barcodeProps.putInt("decoder_code11_length1",settings.mSC_Decoder_Code11_Length1);
        if(settings.mSC_Decoder_Code11_Length2 != mBaseSettings.mSC_Decoder_Code11_Length2) barcodeProps.putInt("decoder_code11_length2",settings.mSC_Decoder_Code11_Length2);
        if(settings.mSC_Decoder_Code11_Redundancy != mBaseSettings.mSC_Decoder_Code11_Redundancy) barcodeProps.putBoolean("decoder_code11_redundancy",settings.mSC_Decoder_Code11_Redundancy);
        if(settings.mSC_Decoder_Code11_Report_Check_Digit != mBaseSettings.mSC_Decoder_Code11_Report_Check_Digit) barcodeProps.putBoolean("decoder_code11_report_check_digit",settings.mSC_Decoder_Code11_Report_Check_Digit);
        if(settings.mSC_Decoder_Code11_Verify_Check_Digit != mBaseSettings.mSC_Decoder_Code11_Verify_Check_Digit) barcodeProps.putString("decoder_code11_verify_check_digit",settings.mSC_Decoder_Code11_Verify_Check_Digit.toString());
        if(settings.mSC_Decoder_Code128 != mBaseSettings.mSC_Decoder_Code128) barcodeProps.putBoolean("decoder_code128",settings.mSC_Decoder_Code128);
        if(settings.mSC_Decoder_Code128_Check_Isbt_Table != mBaseSettings.mSC_Decoder_Code128_Check_Isbt_Table) barcodeProps.putBoolean("decoder_code128_check_isbt_table",settings.mSC_Decoder_Code128_Check_Isbt_Table);
        if(settings.mSC_Decoder_Code128_Enable_Ean128 != mBaseSettings.mSC_Decoder_Code128_Enable_Ean128) barcodeProps.putBoolean("decoder_code128_enable_ean128",settings.mSC_Decoder_Code128_Enable_Ean128);
        if(settings.mSC_Decoder_Code128_Enable_Isbt128 != mBaseSettings.mSC_Decoder_Code128_Enable_Isbt128) barcodeProps.putBoolean("decoder_code128_enable_isbt128",settings.mSC_Decoder_Code128_Enable_Isbt128);
        if(settings.mSC_Decoder_Code128_Enable_Plain != mBaseSettings.mSC_Decoder_Code128_Enable_Plain) barcodeProps.putBoolean("decoder_code128_enable_plain",settings.mSC_Decoder_Code128_Enable_Plain);
        if(settings.mSC_Decoder_Code128_Isbt128_Concat_Mode != mBaseSettings.mSC_Decoder_Code128_Isbt128_Concat_Mode) barcodeProps.putString("decoder_code128_isbt128_concat_mode",settings.mSC_Decoder_Code128_Isbt128_Concat_Mode.toString());
        if(settings.mSC_Decoder_Code128_Length1 != mBaseSettings.mSC_Decoder_Code128_Length1) barcodeProps.putInt("decoder_code128_length1",settings.mSC_Decoder_Code128_Length1);
        if(settings.mSC_Decoder_Code128_Length2 != mBaseSettings.mSC_Decoder_Code128_Length2) barcodeProps.putInt("decoder_code128_length2",settings.mSC_Decoder_Code128_Length2);
        if(settings.mSC_Decoder_Code128_Redundancy != mBaseSettings.mSC_Decoder_Code128_Redundancy) barcodeProps.putBoolean("decoder_code128_redundancy",settings.mSC_Decoder_Code128_Redundancy);
        if(settings.mSC_Decoder_Code128_Security_Level != mBaseSettings.mSC_Decoder_Code128_Security_Level) barcodeProps.putString("decoder_code128_security_level",settings.mSC_Decoder_Code128_Security_Level.toString());
        if(settings.mSC_Decoder_Code39 != mBaseSettings.mSC_Decoder_Code39) barcodeProps.putBoolean("decoder_code39",settings.mSC_Decoder_Code39);
        if(settings.mSC_Decoder_Code39_Convert_To_Code32 != mBaseSettings.mSC_Decoder_Code39_Convert_To_Code32) barcodeProps.putBoolean("decoder_code39_convert_to_code32",settings.mSC_Decoder_Code39_Convert_To_Code32);
        if(settings.mSC_Decoder_Code39_Full_Ascii != mBaseSettings.mSC_Decoder_Code39_Full_Ascii) barcodeProps.putBoolean("decoder_code39_full_ascii",settings.mSC_Decoder_Code39_Full_Ascii);
        if(settings.mSC_Decoder_Code39_Length1 != mBaseSettings.mSC_Decoder_Code39_Length1) barcodeProps.putInt("decoder_code39_length1",settings.mSC_Decoder_Code39_Length1);
        if(settings.mSC_Decoder_Code39_Length2 != mBaseSettings.mSC_Decoder_Code39_Length2) barcodeProps.putInt("decoder_code39_length2",settings.mSC_Decoder_Code39_Length2);
        if(settings.mSC_Decoder_Code39_Redundancy != mBaseSettings.mSC_Decoder_Code39_Redundancy) barcodeProps.putBoolean("decoder_code39_redundancy",settings.mSC_Decoder_Code39_Redundancy);
        if(settings.mSC_Decoder_Code39_Report_Check_Digit != mBaseSettings.mSC_Decoder_Code39_Report_Check_Digit) barcodeProps.putBoolean("decoder_code39_report_check_digit",settings.mSC_Decoder_Code39_Report_Check_Digit);
        if(settings.mSC_Decoder_Code39_Report_Code32_Prefix != mBaseSettings.mSC_Decoder_Code39_Report_Code32_Prefix) barcodeProps.putBoolean("decoder_code39_report_code32_prefix",settings.mSC_Decoder_Code39_Report_Code32_Prefix);
        if(settings.mSC_Decoder_Code39_Security_Level != mBaseSettings.mSC_Decoder_Code39_Security_Level) barcodeProps.putString("decoder_code39_security_level",settings.mSC_Decoder_Code39_Security_Level.toString());
        if(settings.mSC_Decoder_Code39_Verify_Check_Digit != mBaseSettings.mSC_Decoder_Code39_Verify_Check_Digit) barcodeProps.putBoolean("decoder_code39_verify_check_digit",settings.mSC_Decoder_Code39_Verify_Check_Digit);
        if(settings.mSC_Decoder_Code93 != mBaseSettings.mSC_Decoder_Code93) barcodeProps.putBoolean("decoder_code93",settings.mSC_Decoder_Code93);
        if(settings.mSC_Decoder_Code93_Length1 != mBaseSettings.mSC_Decoder_Code93_Length1) barcodeProps.putInt("decoder_code93_length1",settings.mSC_Decoder_Code93_Length1);
        if(settings.mSC_Decoder_Code93_Length2 != mBaseSettings.mSC_Decoder_Code93_Length2) barcodeProps.putInt("decoder_code93_length2",settings.mSC_Decoder_Code93_Length2);
        if(settings.mSC_Decoder_Code93_Redundancy != mBaseSettings.mSC_Decoder_Code93_Redundancy) barcodeProps.putBoolean("decoder_code93_redundancy",settings.mSC_Decoder_Code93_Redundancy);
        if(settings.mSC_Decoder_Composite_Ab != mBaseSettings.mSC_Decoder_Composite_Ab) barcodeProps.putBoolean("decoder_composite_ab",settings.mSC_Decoder_Composite_Ab);
        if(settings.mSC_Decoder_Composite_Ab_Ucc_Link_Mode != mBaseSettings.mSC_Decoder_Composite_Ab_Ucc_Link_Mode) barcodeProps.putString("decoder_composite_ab_ucc_link_mode",settings.mSC_Decoder_Composite_Ab_Ucc_Link_Mode.toString());
        if(settings.mSC_Decoder_Composite_C != mBaseSettings.mSC_Decoder_Composite_C) barcodeProps.putBoolean("decoder_composite_c",settings.mSC_Decoder_Composite_C);
        if(settings.mSC_Decoder_D2of5 != mBaseSettings.mSC_Decoder_D2of5) barcodeProps.putBoolean("decoder_d2of5",settings.mSC_Decoder_D2of5);
        if(settings.mSC_Decoder_D2of5_Length1 != mBaseSettings.mSC_Decoder_D2of5_Length1) barcodeProps.putInt("decoder_d2of5_length1",settings.mSC_Decoder_D2of5_Length1);
        if(settings.mSC_Decoder_D2of5_Length2 != mBaseSettings.mSC_Decoder_D2of5_Length2) barcodeProps.putInt("decoder_d2of5_length24",settings.mSC_Decoder_D2of5_Length2);
        if(settings.mSC_Decoder_D2of5_Redundancy != mBaseSettings.mSC_Decoder_D2of5_Redundancy) barcodeProps.putBoolean("decoder_d2of5_redundancy",settings.mSC_Decoder_D2of5_Redundancy);
        if(settings.mSC_Decoder_Datamatrix != mBaseSettings.mSC_Decoder_Datamatrix) barcodeProps.putBoolean("decoder_datamatrix",settings.mSC_Decoder_Datamatrix);
        if(settings.mSC_Decoder_Dutch_Postal != mBaseSettings.mSC_Decoder_Dutch_Postal) barcodeProps.putBoolean("decoder_dutch_postal",settings.mSC_Decoder_Dutch_Postal);
        if(settings.mSC_Decoder_Ean13 != mBaseSettings.mSC_Decoder_Ean13) barcodeProps.putBoolean("decoder_ean13",settings.mSC_Decoder_Ean13);
        if(settings.mSC_Decoder_Ean8 != mBaseSettings.mSC_Decoder_Ean8) barcodeProps.putBoolean("decoder_ean8",settings.mSC_Decoder_Ean8);
        if(settings.mSC_Decoder_Gs1_Databar != mBaseSettings.mSC_Decoder_Gs1_Databar) barcodeProps.putBoolean("decoder_gs1_databar",settings.mSC_Decoder_Gs1_Databar);
        if(settings.mSC_Decoder_Gs1_Databar_Exp != mBaseSettings.mSC_Decoder_Gs1_Databar_Exp) barcodeProps.putBoolean("decoder_gs1_databar_exp",settings.mSC_Decoder_Gs1_Databar_Exp);
        if(settings.mSC_Decoder_Gs1_Databar_Lim != mBaseSettings.mSC_Decoder_Gs1_Databar_Lim) barcodeProps.putBoolean("decoder_gs1_databar_lim",settings.mSC_Decoder_Gs1_Databar_Lim);
        if(settings.mSC_Decoder_Gs1_Lim_Security_Level != mBaseSettings.mSC_Decoder_Gs1_Lim_Security_Level) barcodeProps.putString("decoder_gs1_lim_security_level",settings.mSC_Decoder_Gs1_Lim_Security_Level.toString());
        if(settings.mSC_Decoder_Hanxin != mBaseSettings.mSC_Decoder_Hanxin) barcodeProps.putBoolean("decoder_hanxin",settings.mSC_Decoder_Hanxin);
        if(settings.mSC_Decoder_Hanxin_Inverse != mBaseSettings.mSC_Decoder_Hanxin_Inverse) barcodeProps.putString("decoder_hanxin_inverse",settings.mSC_Decoder_Hanxin_Inverse.toString());
        if(settings.mSC_Decoder_I2of5 != mBaseSettings.mSC_Decoder_I2of5) barcodeProps.putBoolean("decoder_i2of5",settings.mSC_Decoder_I2of5);
        if(settings.mSC_Decoder_I2of5_Check_Digit != mBaseSettings.mSC_Decoder_I2of5_Check_Digit) barcodeProps.putString("decoder_i2of5_check_digit",settings.mSC_Decoder_I2of5_Check_Digit.toString());
        if(settings.mSC_Decoder_I2of5_Length1 != mBaseSettings.mSC_Decoder_I2of5_Length1) barcodeProps.putInt("decoder_i2of5_length1",settings.mSC_Decoder_I2of5_Length1);
        if(settings.mSC_Decoder_I2of5_Length2 != mBaseSettings.mSC_Decoder_I2of5_Length2) barcodeProps.putInt("decoder_i2of5_length2",settings.mSC_Decoder_I2of5_Length2);
        if(settings.mSC_Decoder_I2of5_Redundancy != mBaseSettings.mSC_Decoder_I2of5_Redundancy) barcodeProps.putBoolean("decoder_i2of5_redundancy",settings.mSC_Decoder_I2of5_Redundancy);
        if(settings.mSC_Decoder_I2of5_Report_Check_Digit != mBaseSettings.mSC_Decoder_I2of5_Report_Check_Digit) barcodeProps.putBoolean("decoder_i2of5_report_check_digit",settings.mSC_Decoder_I2of5_Report_Check_Digit);
        if(settings.mSC_Decoder_I2of5_Security_Level != mBaseSettings.mSC_Decoder_I2of5_Security_Level) barcodeProps.putString("decoder_i2of5_security_level",settings.mSC_Decoder_I2of5_Security_Level.toString());
        if(settings.mSC_Decoder_Itf14_Convert_To_Ean13 != mBaseSettings.mSC_Decoder_Itf14_Convert_To_Ean13) barcodeProps.putBoolean("decoder_itf14_convert_to_ean13",settings.mSC_Decoder_Itf14_Convert_To_Ean13);
        if(settings.mSC_Decoder_Japanese_Postal != mBaseSettings.mSC_Decoder_Japanese_Postal) barcodeProps.putBoolean("decoder_japanese_postal",settings.mSC_Decoder_Japanese_Postal);
        if(settings.mSC_Decoder_Korean_3of5 != mBaseSettings.mSC_Decoder_Korean_3of5) barcodeProps.putBoolean("decoder_korean_3of5",settings.mSC_Decoder_Korean_3of5);
        if(settings.mSC_Decoder_Mailmark != mBaseSettings.mSC_Decoder_Mailmark) barcodeProps.putBoolean("decoder_mailmark",settings.mSC_Decoder_Mailmark);
        if(settings.mSC_Decoder_Matrix_2of5 != mBaseSettings.mSC_Decoder_Matrix_2of5) barcodeProps.putBoolean("decoder_matrix_2of5",settings.mSC_Decoder_Matrix_2of5);
        if(settings.mSC_Decoder_Matrix_2of5_Length1 != mBaseSettings.mSC_Decoder_Matrix_2of5_Length1) barcodeProps.putInt("decoder_matrix_2of5_length10",settings.mSC_Decoder_Matrix_2of5_Length1);
        if(settings.mSC_Decoder_Matrix_2of5_Length2 != mBaseSettings.mSC_Decoder_Matrix_2of5_Length2) barcodeProps.putInt("decoder_matrix_2of5_length2",settings.mSC_Decoder_Matrix_2of5_Length2);
        if(settings.mSC_Decoder_Matrix_2of5_Redundancy != mBaseSettings.mSC_Decoder_Matrix_2of5_Redundancy) barcodeProps.putBoolean("decoder_matrix_2of5_redundancy",settings.mSC_Decoder_Matrix_2of5_Redundancy);
        if(settings.mSC_Decoder_Matrix_2of5_Report_Check_Digit != mBaseSettings.mSC_Decoder_Matrix_2of5_Report_Check_Digit) barcodeProps.putBoolean("decoder_matrix_2of5_report_check_digit",settings.mSC_Decoder_Matrix_2of5_Report_Check_Digit);
        if(settings.mSC_Decoder_Matrix_2of5_Verify_Check_Digit != mBaseSettings.mSC_Decoder_Matrix_2of5_Verify_Check_Digit) barcodeProps.putBoolean("decoder_matrix_2of5_verify_check_digit",settings.mSC_Decoder_Matrix_2of5_Verify_Check_Digit);
        if(settings.mSC_Decoder_Maxicode != mBaseSettings.mSC_Decoder_Maxicode) barcodeProps.putBoolean("decoder_maxicode",settings.mSC_Decoder_Maxicode);
        if(settings.mSC_Decoder_Micropdf != mBaseSettings.mSC_Decoder_Micropdf) barcodeProps.putBoolean("decoder_micropdf",settings.mSC_Decoder_Micropdf);
        if(settings.mSC_Decoder_Microqr != mBaseSettings.mSC_Decoder_Microqr) barcodeProps.putBoolean("decoder_microqr",settings.mSC_Decoder_Microqr);
        if(settings.mSC_Decoder_Msi != mBaseSettings.mSC_Decoder_Msi) barcodeProps.putBoolean("decoder_msi",settings.mSC_Decoder_Msi);
        if(settings.mSC_Decoder_Msi_Check_Digit != mBaseSettings.mSC_Decoder_Msi_Check_Digit) barcodeProps.putString("decoder_msi_check_digit",settings.mSC_Decoder_Msi_Check_Digit.toString());
        if(settings.mSC_Decoder_Msi_Check_Digit_Scheme != mBaseSettings.mSC_Decoder_Msi_Check_Digit_Scheme) barcodeProps.putString("decoder_msi_check_digit_scheme",settings.mSC_Decoder_Msi_Check_Digit_Scheme.toString());
        if(settings.mSC_Decoder_Msi_Length1 != mBaseSettings.mSC_Decoder_Msi_Length1) barcodeProps.putInt("decoder_msi_length1",settings.mSC_Decoder_Msi_Length1);
        if(settings.mSC_Decoder_Msi_Length2 != mBaseSettings.mSC_Decoder_Msi_Length2) barcodeProps.putInt("decoder_msi_length2",settings.mSC_Decoder_Msi_Length2);
        if(settings.mSC_Decoder_Msi_Redundancy != mBaseSettings.mSC_Decoder_Msi_Redundancy) barcodeProps.putBoolean("decoder_msi_redundancy",settings.mSC_Decoder_Msi_Redundancy);
        if(settings.mSC_Decoder_Msi_Report_Check_Digit != mBaseSettings.mSC_Decoder_Msi_Report_Check_Digit) barcodeProps.putBoolean("decoder_msi_report_check_digit",settings.mSC_Decoder_Msi_Report_Check_Digit);
        if(settings.mSC_Decoder_Pdf417 != mBaseSettings.mSC_Decoder_Pdf417) barcodeProps.putBoolean("decoder_pdf417",settings.mSC_Decoder_Pdf417);
        if(settings.mSC_Decoder_Qrcode != mBaseSettings.mSC_Decoder_Qrcode) barcodeProps.putBoolean("decoder_qrcode",settings.mSC_Decoder_Qrcode);
        if(settings.mSC_Decoder_Signature != mBaseSettings.mSC_Decoder_Signature) barcodeProps.putBoolean("decoder_signature",settings.mSC_Decoder_Signature);
        if(settings.mSC_Decoder_Tlc39 != mBaseSettings.mSC_Decoder_Tlc39) barcodeProps.putBoolean("decoder_tlc39",settings.mSC_Decoder_Tlc39);
        if(settings.mSC_Decoder_Trioptic39 != mBaseSettings.mSC_Decoder_Trioptic39) barcodeProps.putBoolean("decoder_trioptic39",settings.mSC_Decoder_Trioptic39);
        if(settings.mSC_Decoder_Uk_Postal != mBaseSettings.mSC_Decoder_Uk_Postal) barcodeProps.putBoolean("decoder_uk_postal",settings.mSC_Decoder_Uk_Postal);
        if(settings.mSC_Decoder_Uk_Postal_Report_Check_Digit != mBaseSettings.mSC_Decoder_Uk_Postal_Report_Check_Digit) barcodeProps.putBoolean("decoder_uk_postal_report_check_digit",settings.mSC_Decoder_Uk_Postal_Report_Check_Digit);
        if(settings.mSC_Decoder_Upca != mBaseSettings.mSC_Decoder_Upca) barcodeProps.putBoolean("decoder_upca",settings.mSC_Decoder_Upca);
        if(settings.mSC_Decoder_Upca_Preamble != mBaseSettings.mSC_Decoder_Upca_Preamble) barcodeProps.putString("decoder_upca_preamble",settings.mSC_Decoder_Upca_Preamble.toString());
        if(settings.mSC_Decoder_Upca_Report_Check_Digit != mBaseSettings.mSC_Decoder_Upca_Report_Check_Digit) barcodeProps.putBoolean("decoder_upca_report_check_digit",settings.mSC_Decoder_Upca_Report_Check_Digit);
        if(settings.mSC_Decoder_Upce0 != mBaseSettings.mSC_Decoder_Upce0) barcodeProps.putBoolean("decoder_upce0",settings.mSC_Decoder_Upce0);
        if(settings.mSC_Decoder_Upce0_Convert_To_Upca != mBaseSettings.mSC_Decoder_Upce0_Convert_To_Upca) barcodeProps.putBoolean("decoder_upce0_convert_to_upca",settings.mSC_Decoder_Upce0_Convert_To_Upca);
        if(settings.mSC_Decoder_Upce0_Preamble != mBaseSettings.mSC_Decoder_Upce0_Preamble) barcodeProps.putString("decoder_upce0_preamble",settings.mSC_Decoder_Upce0_Preamble.toString());
        if(settings.mSC_Decoder_Upce0_Report_Check_Digit != mBaseSettings.mSC_Decoder_Upce0_Report_Check_Digit) barcodeProps.putBoolean("decoder_upce0_report_check_digit",settings.mSC_Decoder_Upce0_Report_Check_Digit);
        if(settings.mSC_Decoder_Upce1 != mBaseSettings.mSC_Decoder_Upce1) barcodeProps.putBoolean("decoder_upce1",settings.mSC_Decoder_Upce1);
        if(settings.mSC_Decoder_Upce1_Convert_To_Upca != mBaseSettings.mSC_Decoder_Upce1_Convert_To_Upca) barcodeProps.putBoolean("decoder_upce1_convert_to_upca",settings.mSC_Decoder_Upce1_Convert_To_Upca);
        if(settings.mSC_Decoder_Upce1_Preamble != mBaseSettings.mSC_Decoder_Upce1_Preamble) barcodeProps.putString("decoder_upce1_preamble",settings.mSC_Decoder_Upce1_Preamble.toString());
        if(settings.mSC_Decoder_Upce1_Report_Check_Digit != mBaseSettings.mSC_Decoder_Upce1_Report_Check_Digit) barcodeProps.putBoolean("decoder_upce1_report_check_digit",settings.mSC_Decoder_Upce1_Report_Check_Digit);
        if(settings.mSC_Decoder_Us4state != mBaseSettings.mSC_Decoder_Us4state) barcodeProps.putBoolean("decoder_us4state",settings.mSC_Decoder_Us4state);
        if(settings.mSC_Decoder_Us4state_Fics != mBaseSettings.mSC_Decoder_Us4state_Fics) barcodeProps.putBoolean("decoder_us4state_fics",settings.mSC_Decoder_Us4state_Fics);
        if(settings.mSC_Decoder_Usplanet != mBaseSettings.mSC_Decoder_Usplanet) barcodeProps.putBoolean("decoder_usplanet",settings.mSC_Decoder_Usplanet);
        if(settings.mSC_Decoder_Usplanet_Report_Check_Digit != mBaseSettings.mSC_Decoder_Usplanet_Report_Check_Digit) barcodeProps.putBoolean("decoder_usplanet_report_check_digit",settings.mSC_Decoder_Usplanet_Report_Check_Digit);
        if(settings.mSC_Decoder_Uspostnet != mBaseSettings.mSC_Decoder_Uspostnet) barcodeProps.putBoolean("decoder_uspostnet",settings.mSC_Decoder_Uspostnet);

    }

}
