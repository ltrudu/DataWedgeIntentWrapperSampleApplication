package com.symbol.datacapturereceiver;

import android.content.Context;

import com.zebra.datawedgeprofileenums.INT_E_DELIVERY;
import com.zebra.datawedgeprofileenums.MB_E_CONFIG_MODE;
import com.zebra.datawedgeprofileenums.SC_E_SCANNER_IDENTIFIER;
import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;
import com.zebra.datawedgeprofileintents.DWProfileSwitchBarcodeParamsSettings;

import java.util.HashMap;

/**
 *
 * Here are the functionalities implemented:
 * -	DWDatawedgeEnable/DWDatawedgeEnableDisable :  as mentioned, enable or disable DW globally (disable all DW for all apps, DANGEROUS)
 * -	DWEnumerateScanners : enumerate the scanners that are available
 * -	DWChecker : checks if a profile exists
 * -	DWProfileCreate : creates a profile
 * -	DWProfileDelete : deletes a profile
 * -	DWProfileSetConfig : set the configuration of an existing profile
 * -	DWProfileSwitchBarcodeParams : fast switch of barcode params for an existing profile
 * -	DWScannerPluginEnable / DWScannerPluginDisable : enable or disable the current DW profile (works only for the current profile)
 * -	DWScannerStartScan : do a software scan
 * -	DWScannerStopScan : stop the current scan
 * -	DWScannerToggleScan : toggle the current scanning status (scan if it was waiting, stop scan if it was scanning)
 *
 * Two objects are added to the library :
 * -	DWStatusScanner : an object that gives the current status of the scanner (idle, waiting, scanning,…)
 * -	DWScanReceiver : an object that receive the data when a scan occurs
 */

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
     * This member will hold the configuration for the restricted mode
     */
    protected static DWProfileSwitchBarcodeParamsSettings mRestrictedSettingsForSwitchParams;

    public static void initSettings(final Context myContext)
    {
        mSetConfigSettings = new DWProfileSetConfigSettings()
        {{
            mProfileName = mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            MainBundle.APP_LIST = new HashMap<>();
            MainBundle.APP_LIST.put(myContext.getPackageName(), null);
            MainBundle.CONFIG_MODE = MB_E_CONFIG_MODE.CREATE_IF_NOT_EXIST;
            IntentPlugin.intent_action = mDemoIntentAction;
            IntentPlugin.intent_category = mDemoIntentCategory;
            IntentPlugin.intent_output_enabled = true;
            IntentPlugin.intent_delivery = INT_E_DELIVERY.BROADCAST;
            KeystrokePlugin.keystroke_output_enabled = false;
            ScannerPlugin.scanner_selection_by_identifier = SC_E_SCANNER_IDENTIFIER.AUTO;
            ScannerPlugin.scanner_input_enabled = true;
            //ScannerPlugin.ScanParams.decode_audio_feedback_uri = "";
            ScannerPlugin.Decoders.decoder_aztec = true;
            ScannerPlugin.Decoders.decoder_micropdf = true;
            //ScannerPlugin.Decoders.decoder_ean8 = false;
            //ScannerPlugin.Decoders.decoder_ean13 = false;
            //ScannerPlugin.Decoders.decoder_code128 = true;
            //ScannerPlugin.Decoders.decoder_i2of5 = true;
            //ScannerPlugin.Decoders.decoder_datamatrix = true;
            //ScannerPlugin.Decoders.decoder_japanese_postal = true;
            //ScannerPlugin.DecodersParams.decoder_i2of5_check_digit = SC_E_I2OF5_CHECK_DIGIT.USS_CHECK_DIGIT;
            //ScannerPlugin.DecodersParams.decoder_i2of5_redundancy = false;
            //ScannerPlugin.UpcEan.upcean_supplemental_mode = SC_E_UPCEAN_SUPPLEMENTAL_MODE.SUPPLEMENTAL_378_379;
        }};

        // Settings classes can be exported to JSON format and initialized from JSON format
        String jsonWithNullTest = DWProfileSetConfigSettings.toJsonWN(mSetConfigSettings);
        DWProfileSetConfigSettings profileFromJSon = DWProfileSetConfigSettings.fromJson(jsonWithNullTest);

        mNormalSettingsForSwitchParams = new DWProfileSwitchBarcodeParamsSettings()
        {{
            mProfileName =mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            mEnableTimeOutMechanism = true;
            ScannerPlugin.Decoders.decoder_ean8 = true;
            ScannerPlugin.Decoders.decoder_ean13 = true;
            ScannerPlugin.Decoders.decoder_code128 = true;
            ScannerPlugin.Decoders.decoder_i2of5 = true;
            ScannerPlugin.Decoders.decoder_qrcode = false;
            ScannerPlugin.Decoders.decoder_pdf417 = false;
            ScannerPlugin.Decoders.decoder_datamatrix = false;
            ScannerPlugin.Decoders.decoder_microqr = false;
        }};

        mRestrictedSettingsForSwitchParams = new DWProfileSwitchBarcodeParamsSettings()
        {{
            mProfileName = mDemoProfileName;
            mTimeOutMS = mDemoTimeOutMS;
            mEnableTimeOutMechanism = true;
            ScannerPlugin.Decoders.decoder_ean8 = false;
            ScannerPlugin.Decoders.decoder_ean13 = false;
            ScannerPlugin.Decoders.decoder_code128 = false;
            ScannerPlugin.Decoders.decoder_i2of5 = false;
            ScannerPlugin.Decoders.decoder_qrcode = true;
            ScannerPlugin.Decoders.decoder_pdf417 = true;
            ScannerPlugin.Decoders.decoder_datamatrix = true;
            ScannerPlugin.Decoders.decoder_microqr = true;
        }};
    }
}
