package com.zebra.datawedgeprofileintents;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Pair;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

public class DWSynchronousMethodsNT {
    
    private String mLastMessage = "";
    private DWSynchronousMethods.EResults mLastResult = DWSynchronousMethods.EResults.NONE;
    private CountDownLatch mJobDoneLatch = null;
    private Context mContext = null;

    public DWSynchronousMethodsNT(Context context)
    {
        mContext = context;
    }

    private class SynchronousNTRunnable implements Runnable
    {
        private String mMethodName;
        private String mStringParam = null;
        private DWProfileSetConfigSettings mSettingsParams = null;
        private DWProfileSwitchBarcodeParamsSettings mSwitchSettingsParams = null;
        private Context mContext;
        public Pair<DWSynchronousMethods.EResults, String> mResults = null;
        public boolean mHasFinished = false;

        public SynchronousNTRunnable(Context context, String methodName, DWProfileSetConfigSettings settingsParams)
        {
            mMethodName = methodName;
            mStringParam = null;
            mSettingsParams = settingsParams;
            mSwitchSettingsParams = null;
            mContext = context;
        }

        public SynchronousNTRunnable(Context context, String methodName, String stringParam)
        {
            mMethodName = methodName;
            mStringParam = stringParam;
            mSettingsParams = null;
            mSwitchSettingsParams = null;
            mContext = context;
        }

        public SynchronousNTRunnable(Context context, String methodName, DWProfileSwitchBarcodeParamsSettings switchBarcodeParamsSettings)
        {
            mMethodName = methodName;
            mStringParam = null;
            mSettingsParams = null;
            mSwitchSettingsParams = switchBarcodeParamsSettings;
            mContext = context;
        }

        @Override
        public void run() {
            try {
                mHasFinished = false;
                Method method;
                Object result = null;
                DWSynchronousMethods dwSynchronousMethods = new DWSynchronousMethods(mContext);
                if(mStringParam != null) {
                    method = DWSynchronousMethods.class.getMethod(mMethodName, String.class);
                    result = method.invoke(dwSynchronousMethods, mStringParam);
                }
                else if(mSettingsParams != null){
                    method = DWSynchronousMethods.class.getMethod(mMethodName, DWProfileSetConfigSettings.class);
                    result = method.invoke(dwSynchronousMethods, mSettingsParams);
                }
                else
                {
                    method = DWSynchronousMethods.class.getMethod(mMethodName, DWProfileSwitchBarcodeParamsSettings.class);
                    result = method.invoke(dwSynchronousMethods, mSwitchSettingsParams);
                }

                if(result != null)
                {
                    mResults = (Pair<DWSynchronousMethods.EResults, String>)result;
                }
                else
                    mResults = null;
                mHasFinished = true;
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Return message left by the last executed method
     * it can be an error message if the method returned an Error result.
     * @return
     */
    public String getLastMessage()
    {
        return mLastMessage;
    }

    public Pair<DWSynchronousMethods.EResults,String> runInNewThread(String methodName, String stringParams)
    {
        SynchronousNTRunnable synchronousNTRunnable = new SynchronousNTRunnable(mContext, methodName, stringParams);
        Thread synchronizedThread = new Thread(synchronousNTRunnable);
        synchronizedThread.start();
        while (synchronousNTRunnable.mHasFinished == false) {
            try {
                Thread.sleep(100L);
            } catch (Throwable e) {
                // on android this may not be allowed, that's why we
                // catch throwable the wait should be very short because we are
                // just waiting for the bind of the socket
            }
        }     
        return synchronousNTRunnable.mResults;
    }

    public Pair<DWSynchronousMethods.EResults,String> runInNewThread(String methodName, DWProfileSetConfigSettings settingsParams)
    {
        SynchronousNTRunnable synchronousNTRunnable = new SynchronousNTRunnable(mContext, methodName, settingsParams);
        Thread synchronizedThread = new Thread(synchronousNTRunnable);
        synchronizedThread.start();
        while (synchronousNTRunnable.mHasFinished == false) {
            try {
                Thread.sleep(100L);
            } catch (Throwable e) {
                // on android this may not be allowed, that's why we
                // catch throwable the wait should be very short because we are
                // just waiting for the bind of the socket
            }
        }
        return synchronousNTRunnable.mResults;
    }

    public Pair<DWSynchronousMethods.EResults,String> runInNewThread(String methodName, DWProfileSwitchBarcodeParamsSettings settingsParams)
    {
        SynchronousNTRunnable synchronousNTRunnable = new SynchronousNTRunnable(mContext, methodName, settingsParams);
        Thread synchronizedThread = new Thread(synchronousNTRunnable);
        synchronizedThread.start();
        while (synchronousNTRunnable.mHasFinished == false) {
            try {
                Thread.sleep(100L);
            } catch (Throwable e) {
                // on android this may not be allowed, that's why we
                // catch throwable the wait should be very short because we are
                // just waiting for the bind of the socket
            }
        }
        return synchronousNTRunnable.mResults;
    }

    public Pair<DWSynchronousMethods.EResults,String> setupDWProfile(final DWProfileSetConfigSettings settings) 
    {
        return runInNewThread("setupDWProfile", settings);
    }
    
    public Pair<DWSynchronousMethods.EResults, String> enablePlugin()
    {
        return runInNewThread("enablePlugin", mContext.getPackageName());
    }

    public Pair<DWSynchronousMethods.EResults, String> enablePlugin(String profileName)
    {
        return runInNewThread("enablePlugin", profileName);
    }

    public Pair<DWSynchronousMethods.EResults, String> disablePlugin()
    {
        return runInNewThread("disablePlugin", mContext.getPackageName());
    }
    
    public Pair<DWSynchronousMethods.EResults, String> disablePlugin(String profileName)
    {
        return runInNewThread("disablePlugin", profileName);
    }

    public Pair<DWSynchronousMethods.EResults, String> startScan()
    {
        return runInNewThread("startScan", mContext.getPackageName());
    }
    
    public Pair<DWSynchronousMethods.EResults, String> startScan(String profileName)
    {
        return runInNewThread("startScan", profileName);
    }

    public Pair<DWSynchronousMethods.EResults, String> stopScan()
    {
        return runInNewThread("stopScan", mContext.getPackageName());
    }
    
    public Pair<DWSynchronousMethods.EResults, String> stopScan(String profileName)
    {
        return runInNewThread("stopScan", profileName);
    }


    public Pair<DWSynchronousMethods.EResults, String> profileExists()
    {
        return runInNewThread("profileExists", mContext.getPackageName());
    }

    public Pair<DWSynchronousMethods.EResults, String> profileExists(String profileName)
    {
        return runInNewThread("profileExists", profileName);
    }

    public Pair<DWSynchronousMethods.EResults, String> deleteProfile()
    {
        return runInNewThread("deleteProfile", mContext.getPackageName());
    }

    public Pair<DWSynchronousMethods.EResults, String> deleteProfile(String profileName)
    {
        return runInNewThread("deleteProfile", profileName);
    }

    public Pair<DWSynchronousMethods.EResults, String> switchBarcodeParams(DWProfileSwitchBarcodeParamsSettings settings)
    {
        return runInNewThread("switchBarcodeParams", settings);
    }

}
