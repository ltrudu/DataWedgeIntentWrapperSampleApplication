package com.symbol.dwprofileasyncclasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.Date;
import java.util.Set;

/**
 * Created by laure on 16/04/2018.
 */

public class DWProfileCommandBase extends DWProfileBase {

    /*
    A command identifier used if we request a result from DataWedge
    */
    protected String mCommandIdentifier = "";

    public DWProfileCommandBase(Context aContext) {
        super(aContext);
        mBroadcastReceiver = new dataWedgeActionResultReceiver();
    }

    /*
        An interface callback to be informed of the result
        when checking if a profile exists
         */
    public interface onProfileCommandResult
    {
        void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier, String error);
    }

    /*
    A store to keep the callback to be fired when we will get the
    result of the intent
     */
    private onProfileCommandResult mProfileCreateCallback = null;

    /*
    The receiver that we will register to retrieve DataWedge answer
     */
    private dataWedgeActionResultReceiver mBroadcastReceiver = null;

    protected void execute(DWProfileBaseSettings settings, onProfileCommandResult callback)
    {

        /*
        Launch timeout mechanism
         */
        super.execute(settings);

        mProfileCreateCallback = callback;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataWedgeConstants.ACTION_RESULT_DATAWEDGE_FROM_6_2);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        /*
        Register receiver for resutls
         */
        mContext.getApplicationContext().registerReceiver(mBroadcastReceiver, intentFilter);

     }

    protected void sendDataWedgeIntentWithExtraRequestResult(String action, String extraKey, String extraValue)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extraValue);
        dwIntent.putExtra("SEND_RESULT","true");
        mCommandIdentifier = mSettings.mProfileName + new Date().getTime();
        dwIntent.putExtra("COMMAND_IDENTIFIER",mCommandIdentifier);
        mContext.sendBroadcast(dwIntent);
    }

    protected void sendDataWedgeIntentWithExtraRequestResult(String action, String extraKey, Bundle extras)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extras);
        dwIntent.putExtra("SEND_RESULT","true");
        mCommandIdentifier = mSettings.mProfileName + new Date().getTime();
        dwIntent.putExtra("COMMAND_IDENTIFIER",mCommandIdentifier);
        mContext.sendBroadcast(dwIntent);
    }

    protected class dataWedgeActionResultReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(DataWedgeConstants.ACTION_RESULT_DATAWEDGE_FROM_6_2))
            {
                    // L'intent reçu est bien l'intent cible
                    // On trace le résultat
                    String action = intent.getAction();
                    String command = intent.getStringExtra("COMMAND");
                    String commandidentifier = intent.getStringExtra("COMMAND_IDENTIFIER");
                    String result = intent.getStringExtra("RESULT");

                    if(commandidentifier.equalsIgnoreCase(mCommandIdentifier) == false)
                        return;

                    Bundle bundle = new Bundle();
                    String resultInfo = "";
                    if (intent.hasExtra("RESULT_INFO")) {
                        bundle = intent.getBundleExtra("RESULT_INFO");
                        Set<String> keys = bundle.keySet();
                        for (String key : keys) {
                            resultInfo += key + ": " + bundle.getString(key) + "\n";
                        }
                    }

                    String text = "Action: " + action + "\n" +
                            "Command: " + command + "\n" +
                            "Result: " + result + "\n" +
                            "Result Info: " + resultInfo + "\n" +
                            "CID:" + commandidentifier;

                    if(mProfileCreateCallback != null)
                    {
                        mProfileCreateCallback.result(mSettings.mProfileName, action, command, result, resultInfo, commandidentifier, null);
                        cleanAll();
                    }
            }
        }
    }

    @Override
    protected void cleanAll()
    {
        mSettings.mProfileName = "";
        mProfileCreateCallback = null;
        mContext.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
        super.cleanAll();
    }

    /*
    This is what will happen if Datawedge does not answer before the timeout
     */
    @Override
    protected void onError(String error) {
        if(mProfileCreateCallback != null)
        {
            mProfileCreateCallback.result(mSettings.mProfileName, null, null, null, null, null, error);
            cleanAll();
        }
    }
}
