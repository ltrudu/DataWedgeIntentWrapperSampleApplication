package com.zebra.datawedgeprofileintents.SettingsPlugins;

import android.os.Bundle;

import com.zebra.datawedgeprofileenums.KEY_E_ACTION_CHAR;
import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;

/////////////////////////////////////////////////////////////////////////////////////////
// Keystroke Plugin...
/////////////////////////////////////////////////////////////////////////////////////////
public class PluginKeystroke
{
    /*
    Enable the keystroke to output something or not
     */
    public boolean keystroke_output_enabled = false;

    /*
    Action to execute after keystroke
     */
    public KEY_E_ACTION_CHAR keystroke_action_character = KEY_E_ACTION_CHAR.ASCII_NO_VALUE;

    public int keystroke_delay_control_characters = 0;

    public int keystroke_character_delay = 0;

    public boolean keystroke_delay_multibyte_chars_only = false;

    public Bundle getKeyStrokePluginBundle(boolean resetConfig)
    {
        // KEYSTROKE plugin configuration -> Disabled
        Bundle keystrokePluginConfig = new Bundle();
        keystrokePluginConfig.putString("PLUGIN_NAME", "KEYSTROKE");
        keystrokePluginConfig.putString("RESET_CONFIG", resetConfig ? "true" : "false");
        Bundle keystrokeProps = new Bundle();
        setupKeystrokePlugin(keystrokeProps);
        keystrokePluginConfig.putBundle("PARAM_LIST", keystrokeProps);
        return keystrokePluginConfig;
    }
    
    private void setupKeystrokePlugin(Bundle keystrokeProps)
    {
        if(keystroke_output_enabled != BaseSettings.mKeystrokeBaseSettings.keystroke_output_enabled)
            keystrokeProps.putString("keystroke_output_enabled", keystroke_output_enabled ? "true" : "false");

        if(keystroke_action_character != BaseSettings.mKeystrokeBaseSettings.keystroke_action_character)
            keystrokeProps.putString("keystroke_action_character", keystroke_action_character.toString());

        if(keystroke_delay_control_characters != BaseSettings.mKeystrokeBaseSettings.keystroke_delay_control_characters)
            keystrokeProps.putInt("keystroke_delay_control_characters", keystroke_delay_control_characters);

        if(keystroke_character_delay != BaseSettings.mKeystrokeBaseSettings.keystroke_character_delay)
            keystrokeProps.putInt("keystroke_character_delay", keystroke_character_delay);

        if(keystroke_delay_multibyte_chars_only != BaseSettings.mKeystrokeBaseSettings.keystroke_delay_multibyte_chars_only)
            keystrokeProps.putString("keystroke_delay_multibyte_chars_only", keystroke_delay_multibyte_chars_only ? "true" : "false");

    }
}
