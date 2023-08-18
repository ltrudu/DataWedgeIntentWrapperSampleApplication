package com.symbol.datacapturereceiver;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.zebra.criticalpermissionshelper.CriticalPermissionsHelper;
import com.zebra.criticalpermissionshelper.EPermissionType;
import com.zebra.criticalpermissionshelper.IResultCallbacks;

public class MainApplication extends Application {

    private static String TAG = "DataCaptureReceiver";


    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Enable permission
            CriticalPermissionsHelper.grantPermission(this, EPermissionType.MANAGE_EXTERNAL_STORAGE, new IResultCallbacks() {
                @Override
                public void onSuccess(String message, String resultXML) {
                    Log.d(TAG, EPermissionType.MANAGE_EXTERNAL_STORAGE.toString() + " granted with success.");
                }

                @Override
                public void onError(String message, String resultXML) {
                    Log.d(TAG, "Error granting " + EPermissionType.MANAGE_EXTERNAL_STORAGE.toString() + " permission.\n" + message);
                }

                @Override
                public void onDebugStatus(String message) {
                    Log.d(TAG, "Debug Grant Permission " + EPermissionType.MANAGE_EXTERNAL_STORAGE.toString() + ": " + message);
                }
            });
        }
    }
}
