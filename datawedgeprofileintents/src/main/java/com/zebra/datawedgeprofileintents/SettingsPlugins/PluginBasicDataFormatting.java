package com.zebra.datawedgeprofileintents.SettingsPlugins;

import android.os.Bundle;

import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;

public class PluginBasicDataFormatting
{
    /////////////////////////////////////////////////////////////////////////////////////////
    // BDF Plugin
    /////////////////////////////////////////////////////////////////////////////////////////
        /*
        Enable or disable BDF
         */
    public boolean bdf_enabled  = false;

    /*
    Prefix to acquired data
     */
    public String bdf_prefix  = "";

    /*
    Suffix to acquired data
     */
    public String bdf_suffix  = "";

    /*
    Send Data ? set to false if you want only hex for exemple
    */
    public boolean bdf_send_data  = true;

    /*
    Send as Hexadecimal data
    */
    public boolean bdf_send_hex  = false;

    /*
    Send a TAB after the data
    */
    public boolean bdf_send_tab  = false;

    /*
    Send a Enter after the data
    */
    public boolean bdf_send_enter  = false;

    public Bundle getBDFPluginBundle(boolean resetConfig, String outputPluginName)
    {
        // Basic Data Formatting plugin configuration
        Bundle bdfPluginConfig = new Bundle();
        bdfPluginConfig.putString("PLUGIN_NAME","BDF");
        bdfPluginConfig.putString("RESET_CONFIG",resetConfig ? "true" : "false");
        bdfPluginConfig.putString("OUTPUT_PLUGIN_NAME",outputPluginName);

        // param_list bundle properties
        Bundle bParams = new Bundle();
        if(bdf_enabled != BaseSettings.mBDFBaseSettings.bdf_enabled)
            bParams.putString("bdf_enabled", bdf_enabled ? "true" : "false");
        if(bdf_prefix != BaseSettings.mBDFBaseSettings.bdf_prefix)
            bParams.putString("bdf_prefix",bdf_prefix);
        if(bdf_suffix  != BaseSettings.mBDFBaseSettings.bdf_suffix )
            bParams.putString("bdf_suffix",bdf_suffix );
        if(bdf_send_data  != BaseSettings.mBDFBaseSettings.bdf_send_data )
            bParams.putString("bdf_send_data", bdf_send_data ? "true" : "false");
        if(bdf_send_hex  != BaseSettings.mBDFBaseSettings.bdf_send_hex )
            bParams.putString("bdf_send_hex",bdf_send_hex  ? "true" : "false");
        if(bdf_send_tab != BaseSettings.mBDFBaseSettings.bdf_send_tab)
            bParams.putString("bdf_send_tab",bdf_send_tab ? "true" : "false");
        if(bdf_send_enter  != BaseSettings.mBDFBaseSettings.bdf_send_enter )
            bParams.putString("bdf_send_enter",bdf_send_enter  ? "true" : "false");

        bdfPluginConfig.putBundle("PARAM_LIST", bParams);
        return bdfPluginConfig;
    }
}


