package com.symbol.dwprofileasyncclasses;

public class DWProfileBaseSettings
{
    /*
    The profile we are working on
     */
    public String mProfileName = "";

    /*
    Some method return only errors (StartScan, StopScan)
    We do not need a time out for them
     */
    public boolean mEnableTimeOutMechanism = true;

    /*
    A time out, in case we don't receive an answer
    from DataWedge
     */
    public long mTimeOutMS = 5000;


}
