package com.symbol.datacapturereceiver;

import android.app.Activity;

import com.zebra.datawedgeprofileenums.SC_E_AIM_TYPE;
import com.zebra.datawedgeprofileenums.SC_E_SCANNINGMODE;
import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;
import com.zebra.datawedgeprofileintents.DWProfileSwitchBarcodeParamsSettings;

public class DataWedgeSettingsHolder {

    protected static String mDemoProfileName = "com.symbol.datacapturereceiver";
    protected static String mDemoIntentAction = "com.symbol.datacapturereceiver.RECVR";
    protected static String mDemoIntentCategory = "android.intent.category.DEFAULT";
    protected static long mDemoTimeOutMS = 30000; //30s timeout...

    /**
     * This member will hold the profile settings that will be used to setup
     * the DataWedge configuration profile when doing a SetProfileConfig
     */
    protected static DWProfileSetConfigSettings mSetConfigSettings;

    /**
     * This member will be used to restore the original scanner configuration that we used
     * to setup the DataWedge profile when we created it
     */
    protected static DWProfileSwitchBarcodeParamsSettings mNormalSettingsForSwitchParams;

    /**
     * This member will hold the configuration for the aggressive mode
     */
    protected static DWProfileSwitchBarcodeParamsSettings mAggressiveSettingsForSwitchParams;

    public static void initSettings(final MainActivity myActivity)
    {
        mSetConfigSettings = new DWProfileSetConfigSettings()
        {{
            mProfileName = mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            MainBundle.PACKAGE_NAME = myActivity.getPackageName();
            IntentPlugin.intent_action = mDemoIntentAction;
            IntentPlugin.intent_category = mDemoIntentCategory;
            IntentPlugin.intent_output_enabled = true;
            KeystrokePlugin.keystroke_output_enabled = false;
            ScannerPlugin.scanner_input_enabled = true;
            ScannerPlugin.Decoders.decoder_aztec = true;
            ScannerPlugin.Decoders.decoder_code128 = true;
            ScannerPlugin.ReaderParams.scanning_mode = SC_E_SCANNINGMODE.MULTIBARCODE;
        }};

        mNormalSettingsForSwitchParams = new DWProfileSwitchBarcodeParamsSettings()
        {{
            mProfileName =mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            ScannerPlugin.ReaderParams.aim_type = SC_E_AIM_TYPE.TRIGGER;
            ScannerPlugin.ReaderParams.beam_timer = 5000;
            ScannerPlugin.ReaderParams.different_barcode_timeout = 500;
            ScannerPlugin.ReaderParams.same_barcode_timeout = 500;
        }};

        mAggressiveSettingsForSwitchParams = new DWProfileSwitchBarcodeParamsSettings()
        {{
            mProfileName = mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            ScannerPlugin.ReaderParams.aim_type = SC_E_AIM_TYPE.PRESS_AND_SUSTAIN;
            ScannerPlugin.ReaderParams.beam_timer = 0;
            ScannerPlugin.ReaderParams.different_barcode_timeout = 0;
            ScannerPlugin.ReaderParams.same_barcode_timeout = 0;
        }};
    }
}
