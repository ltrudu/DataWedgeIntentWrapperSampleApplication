package com.symbol.dwprofileasyncclasses;

/*
Add more initialisation parameters here
 */
public class DWProfileSetConfigSettings extends DWProfileBaseSettings
{
    /*
    Set to true for a supper aggressive scanning mode
     */
    public boolean mStartInAggressiveContinuousMode = false;

    /*
    Set a specific package name if you want to create a profile for a different package than the current one
     */
    public String mPackageName = "";

    /*
    Define the activity that will receive the intents from DataWedge
     */
    public String[] mActivityList = null;
}
