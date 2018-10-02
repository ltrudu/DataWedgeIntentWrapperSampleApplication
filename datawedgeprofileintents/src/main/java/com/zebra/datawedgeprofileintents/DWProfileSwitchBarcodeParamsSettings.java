package com.zebra.datawedgeprofileintents;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zebra.datawedgeprofileintents.SettingsPlugins.PluginScanner;

import org.json.JSONObject;

/*
 Add your own parameters here to setup the barcode reader as you wish
 */
public class DWProfileSwitchBarcodeParamsSettings extends DWProfileBaseSettings
{
    public PluginScanner ScannerPlugin = new PluginScanner();

    public static DWProfileSwitchBarcodeParamsSettings fromJson(String myJSONString)
    {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        JSONObject j;
        DWProfileSwitchBarcodeParamsSettings settings = null;

        try
        {
            j = new JSONObject(myJSONString);
            settings = gson.fromJson(j.toString(), DWProfileSwitchBarcodeParamsSettings.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            settings  = null;
        }
        return settings;
    }

    public static String toJson(DWProfileSwitchBarcodeParamsSettings mySettings) {
        Gson gson = new Gson();
        String j = gson.toJson(mySettings);
        return j;
    }
}
