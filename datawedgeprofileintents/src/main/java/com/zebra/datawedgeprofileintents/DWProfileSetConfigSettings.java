package com.zebra.datawedgeprofileintents;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zebra.datawedgeprofileenums.*;

import  com.zebra.datawedgeprofileintents.SettingsPlugins.*;

import org.json.JSONObject;

import java.io.Reader;

/*
Add more initialisation parameters here
 */
public class DWProfileSetConfigSettings extends DWProfileBaseSettings
{
    public MainBundle MainBundle = new MainBundle();

    public PluginIntent IntentPlugin = new PluginIntent();

    public PluginBasicDataFormatting BasicDataFormatting = new PluginBasicDataFormatting();

    public PluginKeystroke KeystrokePlugin = new PluginKeystroke();

    public PluginScanner ScannerPlugin = new PluginScanner();

    public static DWProfileSetConfigSettings fromJson(String myJSONString)
    {
        Log.v("JSONBuilder:", myJSONString);
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        JSONObject j;
        DWProfileSetConfigSettings settings = null;

        try
        {
            j = new JSONObject(myJSONString);
            settings = gson.fromJson(j.toString(), DWProfileSetConfigSettings.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            settings  = null;
        }
        return settings;
    }

    public static String toJson(DWProfileSetConfigSettings mySettings) {
        Gson gson = new Gson();
        String j = gson.toJson(mySettings);
        return j;
    }
}
