package com.zebra.datawedgeprofileintents.SettingsPlugins;

import com.zebra.datawedgeprofileenums.MB_E_CONFIG_MODE;

public class MainBundle
{
    /////////////////////////////////////////////////////////////////////////////////////////
    // MAIN Bundle
    /////////////////////////////////////////////////////////////////////////////////////////
        /*
        Set if the profile should be enabled or not
         */
    public boolean PROFILE_ENABLED = true;

    /*
    Set how the profile will be processed
     */
    public MB_E_CONFIG_MODE CONFIG_MODE = MB_E_CONFIG_MODE.CREATE_IF_NOT_EXIST;

    /////////////////////////////////////////////////////////////////////////////////////////
    // APP LIST Bundle
    /////////////////////////////////////////////////////////////////////////////////////////
        /*
        Set a specific package name if you want to create a profile for a different package than the current one
         */
    public String PACKAGE_NAME = "";

    /*
    Define the activity that will receive the intents from DataWedge
     */
    public String[] ACTIVITY_LIST = null;
}
