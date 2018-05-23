package com.symbol.dwprofileasyncclasses;

public class DWProfileBaseSettings
{
    /*
    The profile we are working on
     */
    public String mProfileName = "";

    /*
    A time out, in case we don't receive an answer
    from DataWedge
     */
    public long mTimeOutMS = 5000;
}
