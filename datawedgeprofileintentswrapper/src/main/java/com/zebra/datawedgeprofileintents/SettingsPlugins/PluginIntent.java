package com.zebra.datawedgeprofileintents.SettingsPlugins;

import android.os.Bundle;

import com.zebra.datawedgeprofileenums.INT_E_DELIVERY;
import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;

public class PluginIntent
{
    /////////////////////////////////////////////////////////////////////////////////////////
    // INTENT Plugin
    /////////////////////////////////////////////////////////////////////////////////////////
        /*
        Determine if the output of this plugin should be enabled or not
        Default is true
         */
    public Boolean intent_output_enabled = null;

    /*
    The action associated with the broadcasted intent
     */
    public String intent_action = null;

    /*
    The category associated with the broadcast intent
     */
    public String intent_category = null;

    /*
    Delivery mode of the intent plugin
    // Default: INT_E_DELIVERY.BROADCAST
     */
    public INT_E_DELIVERY intent_delivery = null;

    public Bundle getIntentPluginBundle(boolean resetConfig)
    {
        // INTENT Plugin configuration
        Bundle intentPluginConfig = new Bundle();
        intentPluginConfig.putString("PLUGIN_NAME", "INTENT");
        intentPluginConfig.putString("RESET_CONFIG", resetConfig ? "true" : "false");

        Bundle intentProps = new Bundle();
        if(intent_output_enabled != null)
            intentProps.putString("intent_output_enabled", intent_output_enabled ? "true" : "false");
        if(intent_action != null)
            intentProps.putString("intent_action", intent_action);
        if(intent_category != null)
            intentProps.putString("intent_category", intent_category);
        if(intent_delivery != null)
            intentProps.putString("intent_delivery", intent_delivery.toString());
        intentPluginConfig.putBundle("PARAM_LIST", intentProps);
        return intentPluginConfig;
    }
}
