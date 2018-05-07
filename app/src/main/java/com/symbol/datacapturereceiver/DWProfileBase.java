package com.symbol.datacapturereceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Date;

/**
 * Created by laure on 16/04/2018.
 */

public abstract class DWProfileBase {
    /*
    A context to work with intents
     */
    protected Context mContext = null;

    /*
    The profile we are working on
     */
    protected String mProfileName = "";

    /*
    A time out, in case we don't receive an answer
    from DataWedge
     */
    protected long mTimeoutMS = 5000;

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


    public DWProfileBase(Context aContext, String aProfile, long aTimeOut)
    {
        mContext = aContext;
        mProfileName = aProfile;
        mTimeoutMS = aTimeOut;
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

    protected void execute()
    {
        /*
        Start time out mechanism
         */
        mTimeOutHandler.postDelayed(mTimeOutRunnable,
                mTimeoutMS);
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
