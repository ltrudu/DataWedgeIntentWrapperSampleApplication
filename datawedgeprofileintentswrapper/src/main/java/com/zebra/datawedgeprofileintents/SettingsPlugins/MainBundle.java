package com.zebra.datawedgeprofileintents.SettingsPlugins;

import com.zebra.datawedgeprofileenums.MB_E_CONFIG_MODE;

import java.util.List;
import java.util.Map;

public class MainBundle
{
    /////////////////////////////////////////////////////////////////////////////////////////
    // MAIN Bundle
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
       Set if the profile should be enabled or not
    */
    public Boolean PROFILE_ENABLED = null;

    /*
    Set how the profile will be processed
     */
    public MB_E_CONFIG_MODE CONFIG_MODE = null;

    /////////////////////////////////////////////////////////////////////////////////////////
    // APP LIST Bundle
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
        Set a specific package name if you want to create a profile for a different package than the current one
    */
    public String PACKAGE_NAME = null;

    /*
        Define the activity that will receive the intents from DataWedge
     */
    public String[] ACTIVITY_LIST = null;
}
