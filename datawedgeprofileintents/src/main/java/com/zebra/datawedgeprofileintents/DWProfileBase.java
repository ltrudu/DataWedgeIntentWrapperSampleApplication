package com.zebra.datawedgeprofileintents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by laure on 16/04/2018.
 */

public abstract class DWProfileBase {
    /*
    A context to work with intents
     */
    protected Context mContext = null;

    protected DWProfileBaseSettings mSettings = null;

    /*
    A handler that will be used by the derived
    class to prevent waiting to loong for DW in case
    of problem
     */
    protected Handler mTimeOutHandler = new Handler();

    /*
    What will be done at the end of the TimeOut
     */
    protected Runnable mTimeOutRunnable = new Runnable() {
        @Override
        public void run() {
            onError("TimeOut Reached");
        }
    };


    public DWProfileBase(Context aContext)
    {
        mContext = aContext;
    }

    protected void sendDataWedgeIntentWithExtra(String action, String extraKey, String extraValue)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extraValue);
        mContext.sendBroadcast(dwIntent);
    }

    protected void sendDataWedgeIntentWithExtra(String action, String extraKey, Bundle extras)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extras);
        mContext.sendBroadcast(dwIntent);
    }

    protected void execute(DWProfileBaseSettings settings)
    {
        mSettings = settings;
        /*
        Start time out mechanism
        Enabled by default in DWProfileBaseSettings
         */
        if(settings.mEnableTimeOutMechanism) {
            mTimeOutHandler.postDelayed(mTimeOutRunnable,
                    mSettings.mTimeOutMS);
        }
    }

    protected abstract void onError(String error);

    protected void cleanAll()
    {
        if(mTimeOutHandler != null)
        {
            mTimeOutHandler.removeCallbacks(mTimeOutRunnable);
        }
    }
}
