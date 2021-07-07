package com.symbol.datacapturereceiver;

import android.app.Activity;
import android.content.Intent;
import android.util.ArrayMap;

import com.zebra.datawedgeprofileenums.INT_E_DELIVERY;
import com.zebra.datawedgeprofileenums.MB_E_CONFIG_MODE;
import com.zebra.datawedgeprofileenums.SC_E_AIM_TYPE;
import com.zebra.datawedgeprofileenums.SC_E_I2OF5_CHECK_DIGIT;
import com.zebra.datawedgeprofileenums.SC_E_SCANNER_IDENTIFIER;
import com.zebra.datawedgeprofileenums.SC_E_SCANNINGMODE;
import com.zebra.datawedgeprofileenums.SC_E_UPCEAN_SUPPLEMENTAL_MODE;
import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;
import com.zebra.datawedgeprofileintents.DWProfileSwitchBarcodeParamsSettings;

import java.util.HashMap;
import java.util.List;

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
            MainBundle.APP_LIST = new HashMap<>();
            MainBundle.APP_LIST.put(myActivity.getPackageName(), null);
            MainBundle.APP_LIST.put("com.google.com", null);
            MainBundle.CONFIG_MODE = MB_E_CONFIG_MODE.CREATE_IF_NOT_EXIST;
            IntentPlugin.intent_action = mDemoIntentAction;
            IntentPlugin.intent_category = mDemoIntentCategory;
            IntentPlugin.intent_output_enabled = true;
            IntentPlugin.intent_delivery = INT_E_DELIVERY.BROADCAST;
            KeystrokePlugin.keystroke_output_enabled = false;
            ScannerPlugin.scanner_selection_by_identifier = SC_E_SCANNER_IDENTIFIER.AUTO;
            ScannerPlugin.scanner_input_enabled = true;
            ScannerPlugin.Decoders.decoder_aztec = true;
            ScannerPlugin.Decoders.decoder_ean8 = false;
            ScannerPlugin.Decoders.decoder_ean13 = false;
            ScannerPlugin.Decoders.decoder_code128 = true;
            ScannerPlugin.Decoders.decoder_i2of5 = true;
            ScannerPlugin.Decoders.decoder_datamatrix = true;
            ScannerPlugin.Decoders.decoder_japanese_postal = true;
            ScannerPlugin.DecodersParams.decoder_i2of5_check_digit = SC_E_I2OF5_CHECK_DIGIT.USS_CHECK_DIGIT;
            ScannerPlugin.DecodersParams.decoder_i2of5_redundancy = false;
            ScannerPlugin.UpcEan.upcean_supplemental_mode = SC_E_UPCEAN_SUPPLEMENTAL_MODE.SUPPLEMENTAL_378_379;
        }};

        // Settings classes can be exported to JSON format and initialized from JSON format
        String jsonWithNullTest = DWProfileSetConfigSettings.toJsonWN(mSetConfigSettings);
        DWProfileSetConfigSettings profileFromJSon = DWProfileSetConfigSettings.fromJson(jsonWithNullTest);

        mNormalSettingsForSwitchParams = new DWProfileSwitchBarcodeParamsSettings()
        {{
            mProfileName =mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            mEnableTimeOutMechanism = true;
            ScannerPlugin.ReaderParams.aim_type = SC_E_AIM_TYPE.TRIGGER;
            ScannerPlugin.ReaderParams.beam_timer = 5000;
            ScannerPlugin.ReaderParams.different_barcode_timeout = 500;
            ScannerPlugin.ReaderParams.same_barcode_timeout = 500;
            ScannerPlugin.Decoders.decoder_ean8 = false;
            ScannerPlugin.Decoders.decoder_ean13 = false;
            ScannerPlugin.DecodersParams.decoder_gs1_databar_exp = false;
            //ScannerPlugin.Decoders.decoder_webcode = false;
            ScannerPlugin.UpcEan.upcean_supplemental2 = false;

        }};

        mAggressiveSettingsForSwitchParams = new DWProfileSwitchBarcodeParamsSettings()
        {{
            mProfileName = mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            ScannerPlugin.ReaderParams.aim_type = SC_E_AIM_TYPE.PRESS_AND_SUSTAIN;
            ScannerPlugin.ReaderParams.beam_timer = 0;
            ScannerPlugin.ReaderParams.different_barcode_timeout = 0;
            ScannerPlugin.ReaderParams.same_barcode_timeout = 0;
            ScannerPlugin.Decoders.decoder_ean8 = true;
            ScannerPlugin.Decoders.decoder_ean13 = true;
        }};
    }
}
