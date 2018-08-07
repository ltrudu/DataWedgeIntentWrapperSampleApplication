package com.zebra.datawedgeprofileintents;

import com.zebra.datawedgeprofileenums.*;

import  com.zebra.datawedgeprofileintents.SettingsPlugins.*;

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
}
