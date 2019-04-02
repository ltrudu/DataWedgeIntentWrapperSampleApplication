package com.zebra.datawedgeprofileintents.SettingsPlugins;

import android.os.Bundle;

import com.zebra.datawedgeprofileenums.SC_E_AIM_MODE;
import com.zebra.datawedgeprofileenums.SC_E_AIM_TYPE;
import com.zebra.datawedgeprofileenums.SC_E_CHARSET_NAME;
import com.zebra.datawedgeprofileenums.SC_E_CODE11_VERIFY_CHECK_DIGIT;
import com.zebra.datawedgeprofileenums.SC_E_CODE_ID_TYPE;
import com.zebra.datawedgeprofileenums.SC_E_CONCAT_MODE;
import com.zebra.datawedgeprofileenums.SC_E_I2OF5_CHECK_DIGIT;
import com.zebra.datawedgeprofileenums.SC_E_ILLUMINATION_MODE;
import com.zebra.datawedgeprofileenums.SC_E_INVERSE;
import com.zebra.datawedgeprofileenums.SC_E_INVERSE1DMODE_MODE;
import com.zebra.datawedgeprofileenums.SC_E_LCD_MODE;
import com.zebra.datawedgeprofileenums.SC_E_LINEAR_SECURITY_LEVEL;
import com.zebra.datawedgeprofileenums.SC_E_LINK_MODE;
import com.zebra.datawedgeprofileenums.SC_E_MSI_CHECK_DIGIT;
import com.zebra.datawedgeprofileenums.SC_E_MSI_CHECK_DIGIT_SCHEME;
import com.zebra.datawedgeprofileenums.SC_E_PICKLIST_MODE;
import com.zebra.datawedgeprofileenums.SC_E_POOR_QUALITY_DECODE_LEVEL;
import com.zebra.datawedgeprofileenums.SC_E_PREAMBLE;
import com.zebra.datawedgeprofileenums.SC_E_SCANNER_IDENTIFIER;
import com.zebra.datawedgeprofileenums.SC_E_SCANNINGMODE;
import com.zebra.datawedgeprofileenums.SC_E_SECURITY_LEVEL;
import com.zebra.datawedgeprofileenums.SC_E_UPCEAN_BOOKLAND_FORMAT;
import com.zebra.datawedgeprofileenums.SC_E_UPCEAN_COUPON_REPORT;
import com.zebra.datawedgeprofileenums.SC_E_UPCEAN_SECURITY_LEVEL;
import com.zebra.datawedgeprofileenums.SC_E_UPCEAN_SUPPLEMENTAL_MODE;
import com.zebra.datawedgeprofileenums.SC_E_VOLUME_SLIDER_TYPE;
import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;

/////////////////////////////////////////////////////////////////////////////////////////
// BARCODE Plugin... THE BIG ONE !!!!
/////////////////////////////////////////////////////////////////////////////////////////
public class PluginScanner
{
    // This will ensure that we only pass the necessary parameters
    private PluginScanner mConfigToBeCompared = null;
    
    /*
    Enable or disable current scanner input module.
    Useful to control when scan should be available in an application
    Default is true
     */
    public boolean scanner_input_enabled = true;

    /*
    Use it to force the scanner selection
    You should provide an index from 0-n based on the index from ENUMERATE_SCANNERS API
    http://techdocs.zebra.com/datawedge/6-7/guide/api/enumeratescanners/
     */
    public String scanner_selection = "auto";

    /*
     Set the scanner you want to use for this profile
     Default is internal imager
    */
    public SC_E_SCANNER_IDENTIFIER scanner_selection_by_identifier = SC_E_SCANNER_IDENTIFIER.AUTO;


    public class Decoders {
        /****************************************/
        /*              Decoders                */
        /****************************************/
        public boolean decoder_australian_postal = false;
        public boolean decoder_aztec = true;
        public boolean decoder_canadian_postal = false;
        public boolean decoder_chinese_2of5 = false;
        public boolean decoder_codabar = true;
        public boolean decoder_code11 = false;
        public boolean decoder_code128 = true;
        public boolean decoder_code39 = true;
        public boolean decoder_code93 = false;
        public boolean decoder_composite_ab = false;
        public boolean decoder_composite_c = false;
        public boolean decoder_d2of5 = false;
        public boolean decoder_datamatrix = true;
        public boolean decoder_dutch_postal = false;
        public boolean decoder_ean13 = true;
        public boolean decoder_ean8 = true;
        public boolean decoder_gs1_databar = true;
        public boolean decoder_hanxin = false;
        public boolean decoder_i2of5 = false;
        public boolean decoder_japanese_postal = false;
        public boolean decoder_korean_3of5 = false;
        public boolean decoder_mailmark = true;
        public boolean decoder_matrix_2of5 = false;
        public boolean decoder_maxicode = true;
        public boolean decoder_micropdf = false;
        public boolean decoder_microqr = false;
        public boolean decoder_msi = false;
        public boolean decoder_pdf417 = true;
        public boolean decoder_qrcode = true;
        public boolean decoder_signature = false;
        public boolean decoder_tlc39 = false;
        public boolean decoder_trioptic39 = false;
        public boolean decoder_uk_postal = false;
        public boolean decoder_upca = true;
        public boolean decoder_upce0 = true;
        public boolean decoder_upce1 = false;
        public boolean decoder_us4state = false;
        public boolean decoder_usplanet = false;
        public boolean decoder_uspostnet = false;
        public boolean decoder_webcode = false;
    }
    public Decoders Decoders = new Decoders();

    public class DecodersParams {
        /****************************************/
        /*              Decoders                */
        /****************************************/
        public boolean decoder_codabar_clsi_editing = false;
        public int decoder_codabar_length1 = 6;
        public int decoder_codabar_length2 = 55;
        public boolean decoder_codabar_notis_editing = false;
        public boolean decoder_codabar_redundancy = true;

        public int decoder_code11_length1 = 4;
        public int decoder_code11_length2 = 55;
        public boolean decoder_code11_redundancy = true;
        public boolean decoder_code11_report_check_digit = false;
        public SC_E_CODE11_VERIFY_CHECK_DIGIT decoder_code11_verify_check_digit = SC_E_CODE11_VERIFY_CHECK_DIGIT.ONE_CHECK_DIGIT;

        public boolean decoder_code128_check_isbt_table = false;
        public boolean decoder_code128_enable_ean128 = true;
        public boolean decoder_code128_enable_isbt128 = true;
        public boolean decoder_code128_enable_plain = true;
        public SC_E_CONCAT_MODE decoder_code128_isbt128_concat_mode = SC_E_CONCAT_MODE.CONCAT_MODE_NEVER;
        public int decoder_code128_length1 = 0;
        public int decoder_code128_length2 = 55;
        public boolean decoder_code128_redundancy = false;
        public SC_E_SECURITY_LEVEL decoder_code128_security_level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_1;
        public boolean code128_ignore_fnc4 = false;

        public boolean decoder_code39_convert_to_code32 = false;
        public boolean decoder_code39_full_ascii = false;
        public int decoder_code39_length1 = 0;
        public int decoder_code39_length2 = 55;
        public boolean decoder_code39_redundancy = false;
        public boolean decoder_code39_report_check_digit = false;
        public boolean decoder_code39_report_code32_prefix = false;
        public SC_E_SECURITY_LEVEL decoder_code39_security_level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_1;
        public boolean decoder_code39_verify_check_digit = false;

        public int decoder_code93_length1 = 0;
        public int decoder_code93_length2 = 55;
        public boolean decoder_code93_redundancy = false;

        public SC_E_LINK_MODE decoder_composite_ab_ucc_link_mode = SC_E_LINK_MODE.AUTO_DISCRIMINATE;

        public int decoder_d2of5_length1 = 0;
        public int decoder_d2of5_length2 = 14;
        public boolean decoder_d2of5_redundancy = true;

        public boolean decoder_ean8_convert_to_ean13 = false;

        public boolean decoder_gs1_databar_exp = true;
        public boolean decoder_gs1_databar_lim = false;
        public SC_E_SECURITY_LEVEL decoder_gs1_lim_security_level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_2;

        public SC_E_INVERSE decoder_hanxin_inverse = SC_E_INVERSE.DISABLE;

        public SC_E_I2OF5_CHECK_DIGIT decoder_i2of5_check_digit = SC_E_I2OF5_CHECK_DIGIT.NO_CHECK_DIGIT;
        public int decoder_i2of5_length1 = 6;
        public int decoder_i2of5_length2 = 55;
        public boolean decoder_i2of5_redundancy = true;
        public boolean decoder_i2of5_report_check_digit = false;
        public SC_E_SECURITY_LEVEL decoder_i2of5_security_level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_1;
        public boolean decoder_itf14_convert_to_ean13 = false;

        public int decoder_matrix_2of5_length1 = 10;
        public int decoder_matrix_2of5_length2 = 0;
        public boolean decoder_matrix_2of5_redundancy = false;
        public boolean decoder_matrix_2of5_report_check_digit = true;
        public boolean decoder_matrix_2of5_verify_check_digit = true;

        public SC_E_MSI_CHECK_DIGIT decoder_msi_check_digit = SC_E_MSI_CHECK_DIGIT.ONE_CHECK_DIGIT;
        public SC_E_MSI_CHECK_DIGIT_SCHEME decoder_msi_check_digit_scheme = SC_E_MSI_CHECK_DIGIT_SCHEME.MOD_11_10;
        public int decoder_msi_length1 = 4;
        public int decoder_msi_length2 = 55;
        public boolean decoder_msi_redundancy = true;
        public boolean decoder_msi_report_check_digit = false;

        public boolean decoder_trioptic39_redundancy = false;

        public boolean decoder_uk_postal_report_check_digit = false;

        public SC_E_PREAMBLE decoder_upca_preamble = SC_E_PREAMBLE.PREAMBLE_SYS_CHAR;
        public boolean decoder_upca_report_check_digit = true;

        public boolean decoder_upce0_convert_to_upca = false;
        public SC_E_PREAMBLE decoder_upce0_preamble = SC_E_PREAMBLE.PREAMBLE_NONE;
        public boolean decoder_upce0_report_check_digit = false;

        public boolean decoder_upce1_convert_to_upca = false;
        public SC_E_PREAMBLE decoder_upce1_preamble = SC_E_PREAMBLE.PREAMBLE_NONE;
        public boolean decoder_upce1_report_check_digit = false;

        public boolean decoder_us4state_fics = false;

        public boolean decoder_usplanet_report_check_digit = false;
    }
    public DecodersParams DecodersParams = new DecodersParams();

    public class UpcEan
    {
        /****************************************/
        /*              UPCEAN Specific         */
        /****************************************/
        public boolean databar_to_upc_ean = false;
        public boolean upcean_bookland = false;
        public SC_E_UPCEAN_BOOKLAND_FORMAT upcean_bookland_format = SC_E_UPCEAN_BOOKLAND_FORMAT.FORMAT_ISBN_10;
        public boolean upcean_coupon = false;
        public SC_E_UPCEAN_COUPON_REPORT upcean_coupon_report = SC_E_UPCEAN_COUPON_REPORT.BOTH_COUPON_REPORT_MODES;
        public boolean upcean_ean_zero_extend = false;
        public int upcean_retry_count = 10;
        public SC_E_UPCEAN_SECURITY_LEVEL upcean_security_level = SC_E_UPCEAN_SECURITY_LEVEL.SECURITY_LEVEL_1;
        public boolean upcean_supplemental2 = true;
        public boolean upcean_supplemental5 = true;
        public SC_E_UPCEAN_SUPPLEMENTAL_MODE upcean_supplemental_mode = SC_E_UPCEAN_SUPPLEMENTAL_MODE.NO_SUPPLEMENTALS;
        public boolean upcean_linear_decode = false;
        public boolean upcean_random_weight_check_digit = false;
    }
    public UpcEan UpcEan = new UpcEan();

    public class ReaderParams
    {
        /****************************************/
        /*              Reader parameter        */
        /****************************************/
        public SC_E_AIM_MODE aim_mode = SC_E_AIM_MODE.ON;
        public SC_E_CHARSET_NAME charset_name = SC_E_CHARSET_NAME.UTF_8;
        public SC_E_ILLUMINATION_MODE illumination_mode = SC_E_ILLUMINATION_MODE.ON;
        public SC_E_INVERSE1DMODE_MODE inverse_1d_mode = SC_E_INVERSE1DMODE_MODE.DISABLE;
        public SC_E_LCD_MODE lcd_mode = SC_E_LCD_MODE.DISABLED;
        public SC_E_LINEAR_SECURITY_LEVEL linear_security_level = SC_E_LINEAR_SECURITY_LEVEL.SECURITY_SHORT_OR_CODABAR;
        public int low_power_timeout = 250; //0-1000
        public SC_E_PICKLIST_MODE picklist = SC_E_PICKLIST_MODE.DISABLED;
        public SC_E_POOR_QUALITY_DECODE_LEVEL poor_quality_bcdecode_effort_level = SC_E_POOR_QUALITY_DECODE_LEVEL.SECURITY_LEVEL_0;
        public int aim_timer = 500; //0-60000
        public SC_E_AIM_TYPE aim_type = SC_E_AIM_TYPE.TRIGGER;
        public int beam_timer = 5000; //0-60000
        public int different_barcode_timeout = 500; //0-5000 must be a multiple value of 500 (0 included)
        public int same_barcode_timeout = 500; //0-5000 must be a multiple value of 500 (0 included)
        public SC_E_SCANNINGMODE scanning_mode = SC_E_SCANNINGMODE.SINGLE;
    }
    public ReaderParams ReaderParams = new ReaderParams();

    public class ScanParams
    {
        public SC_E_CODE_ID_TYPE code_id_type = SC_E_CODE_ID_TYPE.CODE_ID_TYPE_NONE;
        public boolean decode_haptic_feedback = false;
        public String decode_audio_feedback_uri = "content://media/external/audio/media/null";
        public boolean decoding_led_feedback = false;
        public int good_decode_led_timer = 75; //0-1000
        public SC_E_VOLUME_SLIDER_TYPE volume_slider_type = SC_E_VOLUME_SLIDER_TYPE.NOTIFICATION;
    }
    public ScanParams ScanParams = new ScanParams();

    public class MultiBarcode
    {
        /*
         Number of multibarcode to read
         Default is 5
        */
        public int multi_barcode_count = 5;
    }
    public MultiBarcode MultiBarcode = new MultiBarcode();

    /*
    Undocumented and not available from DataWedge Configuration GUI
    Comes from GetConfig Bundle analysis
     */
    public class MarginLess
    {
        /****************************************/
        /*     Marginless Decode parameters     */
        /****************************************/

        public boolean code128_enable_marginless_decode = false;
        public boolean code39_enable_marginless_decode = false;
        public boolean upc_enable_marginless_decode = false;
        public boolean i20f5_enable_marginless_decode = false;
    }
    public MarginLess MarginLess = new MarginLess();

    /*
    Undocumented and not available from DataWedge Configuration GUI
    */
    public boolean trigger_wakeup = false;


    public Bundle getBarcodePluginBundleForSetConfig(boolean resetConfig, PluginScanner sourceConfiguration)
    {
        // Set it to null to force update of all the parameters
        mConfigToBeCompared = sourceConfiguration;
        
        // Barcode plugin configuration
        Bundle barcodePluginConfig = new Bundle();
        barcodePluginConfig.putString("PLUGIN_NAME", "BARCODE");
        barcodePluginConfig.putString("RESET_CONFIG", resetConfig ? "true" : "false");

        Bundle barcodeProps = new Bundle();

        setupScannerPlugin(barcodeProps);

        barcodePluginConfig.putBundle("PARAM_LIST", barcodeProps);
        return barcodePluginConfig;
    }
    
    private void setupScannerPlugin(Bundle barcodeProps)
    {
        barcodeProps.putString("scanner_input_enabled", scanner_input_enabled ? "true" : "false");

        // Use this for Datawedge < 6.7
        barcodeProps.putString("scanner_selection", scanner_selection);

        // Use this for Datawedge < 6.7
        //barcodeProps.putString("scanner_selection", "AUTO");
        // Use this for Datawedge >= 6.7
        barcodeProps.putString("scanner_selection_by_identifier",scanner_selection_by_identifier.toString());

        // Setup decoders
        setupDecoders(barcodeProps);

        // Setup parameters associated with decoders
        setupDecodersParams(barcodeProps);

        // Setup UPC/EAN Params
        setupUPC_EANParams(barcodeProps);

        // Setup Reader Params
        setupReaderParams(barcodeProps, false);

        // Setup Scan Params
        setupScanParams(barcodeProps);

        // Multibarcode
        setupMultiBarcode(barcodeProps);

        // Setup undocumented parameters... found after reading
        // the content of the bundle returned by the GET_CONFIG Datawedge intent
        // Uncomment at your own risks
        // setupOtherParameters(barcodeProps);
    }

    private  void setupDecoders(Bundle barcodeProps) {
        if(mConfigToBeCompared == null || Decoders.decoder_australian_postal                       != mConfigToBeCompared.Decoders.decoder_australian_postal                 ) barcodeProps.putString(   "decoder_australian_postal"                , Decoders.decoder_australian_postal               ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_aztec                                   != mConfigToBeCompared.Decoders.decoder_aztec                             ) barcodeProps.putString(   "decoder_aztec"                            , Decoders.decoder_aztec                           ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_canadian_postal                         != mConfigToBeCompared.Decoders.decoder_canadian_postal                   ) barcodeProps.putString(   "decoder_canadian_postal"                  , Decoders.decoder_canadian_postal                 ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_chinese_2of5                            != mConfigToBeCompared.Decoders.decoder_chinese_2of5                      ) barcodeProps.putString(   "decoder_chinese_2of5"                     , Decoders.decoder_chinese_2of5                    ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_codabar                                 != mConfigToBeCompared.Decoders.decoder_codabar                           ) barcodeProps.putString(   "decoder_codabar"                          , Decoders.decoder_codabar                         ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_code11                                  != mConfigToBeCompared.Decoders.decoder_code11                            ) barcodeProps.putString(   "decoder_code11"                           , Decoders.decoder_code11                          ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_code128                                 != mConfigToBeCompared.Decoders.decoder_code128                           ) barcodeProps.putString(   "decoder_code128"                          , Decoders.decoder_code128                         ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_code39                                  != mConfigToBeCompared.Decoders.decoder_code39                            ) barcodeProps.putString(   "decoder_code39"                           , Decoders.decoder_code39                          ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_code93                                  != mConfigToBeCompared.Decoders.decoder_code93                            ) barcodeProps.putString(   "decoder_code93"                           , Decoders.decoder_code93                          ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_composite_ab                            != mConfigToBeCompared.Decoders.decoder_composite_ab                      ) barcodeProps.putString(   "decoder_composite_ab"                     , Decoders.decoder_composite_ab                    ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_composite_c                             != mConfigToBeCompared.Decoders.decoder_composite_c                       ) barcodeProps.putString(   "decoder_composite_c"                      , Decoders.decoder_composite_c                     ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_d2of5                                   != mConfigToBeCompared.Decoders.decoder_d2of5                             ) barcodeProps.putString(   "decoder_d2of5"                            , Decoders.decoder_d2of5                           ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_datamatrix                              != mConfigToBeCompared.Decoders.decoder_datamatrix                        ) barcodeProps.putString(   "decoder_datamatrix"                       , Decoders.decoder_datamatrix                      ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_dutch_postal                            != mConfigToBeCompared.Decoders.decoder_dutch_postal                      ) barcodeProps.putString(   "decoder_dutch_postal"                     , Decoders.decoder_dutch_postal                    ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_ean13                                   != mConfigToBeCompared.Decoders.decoder_ean13                             ) barcodeProps.putString(   "decoder_ean13"                            , Decoders.decoder_ean13                           ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_ean8                                    != mConfigToBeCompared.Decoders.decoder_ean8                              ) barcodeProps.putString(   "decoder_ean8"                             , Decoders.decoder_ean8                            ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_gs1_databar                             != mConfigToBeCompared.Decoders.decoder_gs1_databar                       ) barcodeProps.putString(   "decoder_gs1_databar"                      , Decoders.decoder_gs1_databar                     ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_hanxin                                  != mConfigToBeCompared.Decoders.decoder_hanxin                            ) barcodeProps.putString(   "decoder_hanxin"                           , Decoders.decoder_hanxin                          ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_i2of5                                   != mConfigToBeCompared.Decoders.decoder_i2of5                             ) barcodeProps.putString(   "decoder_i2of5"                            , Decoders.decoder_i2of5                           ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_japanese_postal                         != mConfigToBeCompared.Decoders.decoder_japanese_postal                   ) barcodeProps.putString(   "decoder_japanese_postal"                  , Decoders.decoder_japanese_postal                 ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_korean_3of5                             != mConfigToBeCompared.Decoders.decoder_korean_3of5                       ) barcodeProps.putString(   "decoder_korean_3of5"                      , Decoders.decoder_korean_3of5                     ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_mailmark                                != mConfigToBeCompared.Decoders.decoder_mailmark                          ) barcodeProps.putString(   "decoder_mailmark"                         , Decoders.decoder_mailmark                        ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_matrix_2of5                             != mConfigToBeCompared.Decoders.decoder_matrix_2of5                       ) barcodeProps.putString(   "decoder_matrix_2of5"                      , Decoders.decoder_matrix_2of5                     ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_maxicode                                != mConfigToBeCompared.Decoders.decoder_maxicode                          ) barcodeProps.putString(   "decoder_maxicode"                         , Decoders.decoder_maxicode                        ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_micropdf                                != mConfigToBeCompared.Decoders.decoder_micropdf                          ) barcodeProps.putString(   "decoder_micropdf"                         , Decoders.decoder_micropdf                        ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_microqr                                 != mConfigToBeCompared.Decoders.decoder_microqr                           ) barcodeProps.putString(   "decoder_microqr"                          , Decoders.decoder_microqr                         ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_msi                                     != mConfigToBeCompared.Decoders.decoder_msi                               ) barcodeProps.putString(   "decoder_msi"                              , Decoders.decoder_msi                             ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_pdf417                                  != mConfigToBeCompared.Decoders.decoder_pdf417                            ) barcodeProps.putString(   "decoder_pdf417"                           , Decoders.decoder_pdf417                          ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_qrcode                                  != mConfigToBeCompared.Decoders.decoder_qrcode                            ) barcodeProps.putString(   "decoder_qrcode"                           , Decoders.decoder_qrcode                          ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_signature                               != mConfigToBeCompared.Decoders.decoder_signature                         ) barcodeProps.putString(   "decoder_signature"                        , Decoders.decoder_signature                       ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_tlc39                                   != mConfigToBeCompared.Decoders.decoder_tlc39                             ) barcodeProps.putString(   "decoder_tlc39"                            , Decoders.decoder_tlc39                           ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_trioptic39                              != mConfigToBeCompared.Decoders.decoder_trioptic39                        ) barcodeProps.putString(   "decoder_trioptic39"                       , Decoders.decoder_trioptic39                      ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_uk_postal                               != mConfigToBeCompared.Decoders.decoder_uk_postal                         ) barcodeProps.putString(   "decoder_uk_postal"                        , Decoders.decoder_uk_postal                       ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_upca                                    != mConfigToBeCompared.Decoders.decoder_upca                              ) barcodeProps.putString(   "decoder_upca"                             , Decoders.decoder_upca                            ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_upce0                                   != mConfigToBeCompared.Decoders.decoder_upce0                             ) barcodeProps.putString(   "decoder_upce0"                            , Decoders.decoder_upce0                           ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_upce1                                   != mConfigToBeCompared.Decoders.decoder_upce1                             ) barcodeProps.putString(   "decoder_upce1"                            , Decoders.decoder_upce1                           ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_us4state                                != mConfigToBeCompared.Decoders.decoder_us4state                          ) barcodeProps.putString(   "decoder_us4state"                         , Decoders.decoder_us4state                        ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_usplanet                                != mConfigToBeCompared.Decoders.decoder_usplanet                          ) barcodeProps.putString(   "decoder_usplanet"                         , Decoders.decoder_usplanet                        ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_uspostnet                               != mConfigToBeCompared.Decoders.decoder_uspostnet                         ) barcodeProps.putString(   "decoder_uspostnet"                        , Decoders.decoder_uspostnet                       ? "true":"false");
        if(mConfigToBeCompared == null || Decoders.decoder_webcode                                 != mConfigToBeCompared.Decoders.decoder_webcode                           ) barcodeProps.putString(   "decoder_webcode"                          , Decoders.decoder_webcode                         ? "true":"false");
    }

    private  void setupDecodersParams(Bundle barcodeProps)
    {
        if(mConfigToBeCompared == null || DecodersParams.decoder_codabar_clsi_editing                    != mConfigToBeCompared.DecodersParams.decoder_codabar_clsi_editing              ) barcodeProps.putBoolean(   "decoder_codabar_clsi_editing"             , DecodersParams.decoder_codabar_clsi_editing             );
        if(mConfigToBeCompared == null || DecodersParams.decoder_codabar_length1                         != mConfigToBeCompared.DecodersParams.decoder_codabar_length1                   ) barcodeProps.putInt(       "decoder_codabar_length1"                  , DecodersParams.decoder_codabar_length1                  );
        if(mConfigToBeCompared == null || DecodersParams.decoder_codabar_length2                         != mConfigToBeCompared.DecodersParams.decoder_codabar_length2                   ) barcodeProps.putInt(       "decoder_codabar_length2"                  , DecodersParams.decoder_codabar_length2                  );
        if(mConfigToBeCompared == null || DecodersParams.decoder_codabar_notis_editing                   != mConfigToBeCompared.DecodersParams.decoder_codabar_notis_editing             ) barcodeProps.putBoolean(   "decoder_codabar_notis_editing"            , DecodersParams.decoder_codabar_notis_editing            );
        if(mConfigToBeCompared == null || DecodersParams.decoder_codabar_redundancy                      != mConfigToBeCompared.DecodersParams.decoder_codabar_redundancy                ) barcodeProps.putBoolean(   "decoder_codabar_redundancy"               , DecodersParams.decoder_codabar_redundancy               );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code11_length1                          != mConfigToBeCompared.DecodersParams.decoder_code11_length1                    ) barcodeProps.putInt(       "decoder_code11_length1"                   , DecodersParams.decoder_code11_length1                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code11_length2                          != mConfigToBeCompared.DecodersParams.decoder_code11_length2                    ) barcodeProps.putInt(       "decoder_code11_length2"                   , DecodersParams.decoder_code11_length2                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code11_redundancy                       != mConfigToBeCompared.DecodersParams.decoder_code11_redundancy                 ) barcodeProps.putBoolean(   "decoder_code11_redundancy"                , DecodersParams.decoder_code11_redundancy                );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code11_report_check_digit               != mConfigToBeCompared.DecodersParams.decoder_code11_report_check_digit         ) barcodeProps.putBoolean(   "decoder_code11_report_check_digit"        , DecodersParams.decoder_code11_report_check_digit        );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code11_verify_check_digit               != mConfigToBeCompared.DecodersParams.decoder_code11_verify_check_digit         ) barcodeProps.putString(    "decoder_code11_verify_check_digit"        , DecodersParams.decoder_code11_verify_check_digit.toString()        );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_check_isbt_table                != mConfigToBeCompared.DecodersParams.decoder_code128_check_isbt_table          ) barcodeProps.putBoolean(   "decoder_code128_check_isbt_table"         , DecodersParams.decoder_code128_check_isbt_table         );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_enable_ean128                   != mConfigToBeCompared.DecodersParams.decoder_code128_enable_ean128             ) barcodeProps.putBoolean(   "decoder_code128_enable_ean128"            , DecodersParams.decoder_code128_enable_ean128            );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_enable_isbt128                  != mConfigToBeCompared.DecodersParams.decoder_code128_enable_isbt128            ) barcodeProps.putBoolean(   "decoder_code128_enable_isbt128"           , DecodersParams.decoder_code128_enable_isbt128           );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_enable_plain                    != mConfigToBeCompared.DecodersParams.decoder_code128_enable_plain              ) barcodeProps.putBoolean(   "decoder_code128_enable_plain"             , DecodersParams.decoder_code128_enable_plain             );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_isbt128_concat_mode             != mConfigToBeCompared.DecodersParams.decoder_code128_isbt128_concat_mode       ) barcodeProps.putString(    "decoder_code128_isbt128_concat_mode"      , DecodersParams.decoder_code128_isbt128_concat_mode.toString()      );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_length1                         != mConfigToBeCompared.DecodersParams.decoder_code128_length1                   ) barcodeProps.putInt(       "decoder_code128_length1"                  , DecodersParams.decoder_code128_length1                  );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_length2                         != mConfigToBeCompared.DecodersParams.decoder_code128_length2                   ) barcodeProps.putInt(       "decoder_code128_length2"                  , DecodersParams.decoder_code128_length2                  );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_redundancy                      != mConfigToBeCompared.DecodersParams.decoder_code128_redundancy                ) barcodeProps.putBoolean(   "decoder_code128_redundancy"               , DecodersParams.decoder_code128_redundancy               );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code128_security_level                  != mConfigToBeCompared.DecodersParams.decoder_code128_security_level            ) barcodeProps.putString(    "decoder_code128_security_level"           , DecodersParams.decoder_code128_security_level.toString()           );
        if(mConfigToBeCompared == null || DecodersParams.code128_ignore_fnc4                             != mConfigToBeCompared.DecodersParams.code128_ignore_fnc4                       ) barcodeProps.putBoolean(   "code128_ignore_fnc4"                      , DecodersParams.code128_ignore_fnc4                      );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_convert_to_code32                != mConfigToBeCompared.DecodersParams.decoder_code39_convert_to_code32          ) barcodeProps.putBoolean(   "decoder_code39_convert_to_code32"         , DecodersParams.decoder_code39_convert_to_code32         );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_full_ascii                       != mConfigToBeCompared.DecodersParams.decoder_code39_full_ascii                 ) barcodeProps.putBoolean(   "decoder_code39_full_ascii"                , DecodersParams.decoder_code39_full_ascii                );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_length1                          != mConfigToBeCompared.DecodersParams.decoder_code39_length1                    ) barcodeProps.putInt(       "decoder_code39_length1"                   , DecodersParams.decoder_code39_length1                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_length2                          != mConfigToBeCompared.DecodersParams.decoder_code39_length2                    ) barcodeProps.putInt(       "decoder_code39_length2"                   , DecodersParams.decoder_code39_length2                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_redundancy                       != mConfigToBeCompared.DecodersParams.decoder_code39_redundancy                 ) barcodeProps.putBoolean(   "decoder_code39_redundancy"                , DecodersParams.decoder_code39_redundancy                );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_report_check_digit               != mConfigToBeCompared.DecodersParams.decoder_code39_report_check_digit         ) barcodeProps.putBoolean(   "decoder_code39_report_check_digit"        , DecodersParams.decoder_code39_report_check_digit        );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_report_code32_prefix             != mConfigToBeCompared.DecodersParams.decoder_code39_report_code32_prefix       ) barcodeProps.putBoolean(   "decoder_code39_report_code32_prefix"      , DecodersParams.decoder_code39_report_code32_prefix      );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_security_level                   != mConfigToBeCompared.DecodersParams.decoder_code39_security_level             ) barcodeProps.putString(    "decoder_code39_security_level"            , DecodersParams.decoder_code39_security_level.toString()            );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code39_verify_check_digit               != mConfigToBeCompared.DecodersParams.decoder_code39_verify_check_digit         ) barcodeProps.putBoolean(   "decoder_code39_verify_check_digit"        , DecodersParams.decoder_code39_verify_check_digit        );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code93_length1                          != mConfigToBeCompared.DecodersParams.decoder_code93_length1                    ) barcodeProps.putInt(       "decoder_code93_length1"                   , DecodersParams.decoder_code93_length1                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code93_length2                          != mConfigToBeCompared.DecodersParams.decoder_code93_length2                    ) barcodeProps.putInt(       "decoder_code93_length2"                   , DecodersParams.decoder_code93_length2                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_code93_redundancy                       != mConfigToBeCompared.DecodersParams.decoder_code93_redundancy                 ) barcodeProps.putBoolean(   "decoder_code93_redundancy"                , DecodersParams.decoder_code93_redundancy                );
        if(mConfigToBeCompared == null || DecodersParams.decoder_composite_ab_ucc_link_mode              != mConfigToBeCompared.DecodersParams.decoder_composite_ab_ucc_link_mode        ) barcodeProps.putString(    "decoder_composite_ab_ucc_link_mode"       , DecodersParams.decoder_composite_ab_ucc_link_mode.toString()       );
        if(mConfigToBeCompared == null || DecodersParams.decoder_d2of5_length1                           != mConfigToBeCompared.DecodersParams.decoder_d2of5_length1                     ) barcodeProps.putInt(       "decoder_d2of5_length1"                    , DecodersParams.decoder_d2of5_length1                    );
        if(mConfigToBeCompared == null || DecodersParams.decoder_d2of5_length2                           != mConfigToBeCompared.DecodersParams.decoder_d2of5_length2                     ) barcodeProps.putInt(       "decoder_d2of5_length2"                    , DecodersParams.decoder_d2of5_length2                    );
        if(mConfigToBeCompared == null || DecodersParams.decoder_d2of5_redundancy                        != mConfigToBeCompared.DecodersParams.decoder_d2of5_redundancy                  ) barcodeProps.putBoolean(   "decoder_d2of5_redundancy"                 , DecodersParams.decoder_d2of5_redundancy                 );
        if(mConfigToBeCompared == null || DecodersParams.decoder_ean8_convert_to_ean13                   != mConfigToBeCompared.DecodersParams.decoder_ean8_convert_to_ean13             ) barcodeProps.putBoolean(   "decoder_ean8_convert_to_ean13"            , DecodersParams.decoder_ean8_convert_to_ean13            );
        if(mConfigToBeCompared == null || DecodersParams.decoder_gs1_databar_exp                         != mConfigToBeCompared.DecodersParams.decoder_gs1_databar_exp                   ) barcodeProps.putBoolean(   "decoder_gs1_databar_exp"                  , DecodersParams.decoder_gs1_databar_exp                  );
        if(mConfigToBeCompared == null || DecodersParams.decoder_gs1_databar_lim                         != mConfigToBeCompared.DecodersParams.decoder_gs1_databar_lim                   ) barcodeProps.putBoolean(   "decoder_gs1_databar_lim"                  , DecodersParams.decoder_gs1_databar_lim                  );
        if(mConfigToBeCompared == null || DecodersParams.decoder_gs1_lim_security_level                  != mConfigToBeCompared.DecodersParams.decoder_gs1_lim_security_level            ) barcodeProps.putString(    "decoder_gs1_lim_security_level"           , DecodersParams.decoder_gs1_lim_security_level.toString()           );
        if(mConfigToBeCompared == null || DecodersParams.decoder_hanxin_inverse                          != mConfigToBeCompared.DecodersParams.decoder_hanxin_inverse                    ) barcodeProps.putString(    "decoder_hanxin_inverse"                   , DecodersParams.decoder_hanxin_inverse.toString()                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_i2of5_check_digit                       != mConfigToBeCompared.DecodersParams.decoder_i2of5_check_digit                 ) barcodeProps.putString(    "decoder_i2of5_check_digit"                , DecodersParams.decoder_i2of5_check_digit.toString()                );
        if(mConfigToBeCompared == null || DecodersParams.decoder_i2of5_length1                           != mConfigToBeCompared.DecodersParams.decoder_i2of5_length1                     ) barcodeProps.putInt(       "decoder_i2of5_length1"                    , DecodersParams.decoder_i2of5_length1                    );
        if(mConfigToBeCompared == null || DecodersParams.decoder_i2of5_length2                           != mConfigToBeCompared.DecodersParams.decoder_i2of5_length2                     ) barcodeProps.putInt(       "decoder_i2of5_length2"                    , DecodersParams.decoder_i2of5_length2                    );
        if(mConfigToBeCompared == null || DecodersParams.decoder_i2of5_redundancy                        != mConfigToBeCompared.DecodersParams.decoder_i2of5_redundancy                  ) barcodeProps.putBoolean(   "decoder_i2of5_redundancy"                 , DecodersParams.decoder_i2of5_redundancy                 );
        if(mConfigToBeCompared == null || DecodersParams.decoder_i2of5_report_check_digit                != mConfigToBeCompared.DecodersParams.decoder_i2of5_report_check_digit          ) barcodeProps.putBoolean(   "decoder_i2of5_report_check_digit"         , DecodersParams.decoder_i2of5_report_check_digit         );
        if(mConfigToBeCompared == null || DecodersParams.decoder_i2of5_security_level                    != mConfigToBeCompared.DecodersParams.decoder_i2of5_security_level              ) barcodeProps.putString(    "decoder_i2of5_security_level"             , DecodersParams.decoder_i2of5_security_level.toString()             );
        if(mConfigToBeCompared == null || DecodersParams.decoder_itf14_convert_to_ean13                  != mConfigToBeCompared.DecodersParams.decoder_itf14_convert_to_ean13            ) barcodeProps.putBoolean(   "decoder_itf14_convert_to_ean13"           , DecodersParams.decoder_itf14_convert_to_ean13           );
        if(mConfigToBeCompared == null || DecodersParams.decoder_matrix_2of5_length1                     != mConfigToBeCompared.DecodersParams.decoder_matrix_2of5_length1               ) barcodeProps.putInt(       "decoder_matrix_2of5_length1"              , DecodersParams.decoder_matrix_2of5_length1              );
        if(mConfigToBeCompared == null || DecodersParams.decoder_matrix_2of5_length2                     != mConfigToBeCompared.DecodersParams.decoder_matrix_2of5_length2               ) barcodeProps.putInt(       "decoder_matrix_2of5_length2"              , DecodersParams.decoder_matrix_2of5_length2              );
        if(mConfigToBeCompared == null || DecodersParams.decoder_matrix_2of5_redundancy                  != mConfigToBeCompared.DecodersParams.decoder_matrix_2of5_redundancy            ) barcodeProps.putBoolean(   "decoder_matrix_2of5_redundancy"           , DecodersParams.decoder_matrix_2of5_redundancy           );
        if(mConfigToBeCompared == null || DecodersParams.decoder_matrix_2of5_report_check_digit          != mConfigToBeCompared.DecodersParams.decoder_matrix_2of5_report_check_digit    ) barcodeProps.putBoolean(   "decoder_matrix_2of5_report_check_digit"   , DecodersParams.decoder_matrix_2of5_report_check_digit   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_matrix_2of5_verify_check_digit          != mConfigToBeCompared.DecodersParams.decoder_matrix_2of5_verify_check_digit    ) barcodeProps.putBoolean(   "decoder_matrix_2of5_verify_check_digit"   , DecodersParams.decoder_matrix_2of5_verify_check_digit   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_msi_check_digit                         != mConfigToBeCompared.DecodersParams.decoder_msi_check_digit                   ) barcodeProps.putString(    "decoder_msi_check_digit"                  , DecodersParams.decoder_msi_check_digit.toString()                  );
        if(mConfigToBeCompared == null || DecodersParams.decoder_msi_check_digit_scheme                  != mConfigToBeCompared.DecodersParams.decoder_msi_check_digit_scheme            ) barcodeProps.putString(    "decoder_msi_check_digit_scheme"           , DecodersParams.decoder_msi_check_digit_scheme.toString()           );
        if(mConfigToBeCompared == null || DecodersParams.decoder_msi_length1                             != mConfigToBeCompared.DecodersParams.decoder_msi_length1                       ) barcodeProps.putInt(       "decoder_msi_length1"                      , DecodersParams.decoder_msi_length1                      );
        if(mConfigToBeCompared == null || DecodersParams.decoder_msi_length2                             != mConfigToBeCompared.DecodersParams.decoder_msi_length2                       ) barcodeProps.putInt(       "decoder_msi_length2"                      , DecodersParams.decoder_msi_length2                      );
        if(mConfigToBeCompared == null || DecodersParams.decoder_msi_redundancy                          != mConfigToBeCompared.DecodersParams.decoder_msi_redundancy                    ) barcodeProps.putBoolean(   "decoder_msi_redundancy"                   , DecodersParams.decoder_msi_redundancy                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_msi_report_check_digit                  != mConfigToBeCompared.DecodersParams.decoder_msi_report_check_digit            ) barcodeProps.putBoolean(   "decoder_msi_report_check_digit"           , DecodersParams.decoder_msi_report_check_digit           );
        if(mConfigToBeCompared == null || DecodersParams.decoder_trioptic39_redundancy                   != mConfigToBeCompared.DecodersParams.decoder_trioptic39_redundancy             ) barcodeProps.putBoolean(   "decoder_trioptic39_redundancy"            , DecodersParams.decoder_trioptic39_redundancy            );
        if(mConfigToBeCompared == null || DecodersParams.decoder_uk_postal_report_check_digit            != mConfigToBeCompared.DecodersParams.decoder_uk_postal_report_check_digit      ) barcodeProps.putBoolean(   "decoder_uk_postal_report_check_digit"     , DecodersParams.decoder_uk_postal_report_check_digit     );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upca_preamble                           != mConfigToBeCompared.DecodersParams.decoder_upca_preamble                     ) barcodeProps.putString(    "decoder_upca_preamble"                    , DecodersParams.decoder_upca_preamble.toString()                    );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upca_report_check_digit                 != mConfigToBeCompared.DecodersParams.decoder_upca_report_check_digit           ) barcodeProps.putBoolean(   "decoder_upca_report_check_digit"          , DecodersParams.decoder_upca_report_check_digit          );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upce0_convert_to_upca                   != mConfigToBeCompared.DecodersParams.decoder_upce0_convert_to_upca             ) barcodeProps.putBoolean(   "decoder_upce0_convert_to_upca"            , DecodersParams.decoder_upce0_convert_to_upca            );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upce0_preamble                          != mConfigToBeCompared.DecodersParams.decoder_upce0_preamble                    ) barcodeProps.putString(    "decoder_upce0_preamble"                   , DecodersParams.decoder_upce0_preamble.toString()                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upce0_report_check_digit                != mConfigToBeCompared.DecodersParams.decoder_upce0_report_check_digit          ) barcodeProps.putBoolean(   "decoder_upce0_report_check_digit"         , DecodersParams.decoder_upce0_report_check_digit         );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upce1_convert_to_upca                   != mConfigToBeCompared.DecodersParams.decoder_upce1_convert_to_upca             ) barcodeProps.putBoolean(   "decoder_upce1_convert_to_upca"            , DecodersParams.decoder_upce1_convert_to_upca            );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upce1_preamble                          != mConfigToBeCompared.DecodersParams.decoder_upce1_preamble                    ) barcodeProps.putString(    "decoder_upce1_preamble"                   , DecodersParams.decoder_upce1_preamble.toString()                   );
        if(mConfigToBeCompared == null || DecodersParams.decoder_upce1_report_check_digit                != mConfigToBeCompared.DecodersParams.decoder_upce1_report_check_digit          ) barcodeProps.putBoolean(   "decoder_upce1_report_check_digit"         , DecodersParams.decoder_upce1_report_check_digit         );
        if(mConfigToBeCompared == null || DecodersParams.decoder_us4state_fics                           != mConfigToBeCompared.DecodersParams.decoder_us4state_fics                     ) barcodeProps.putBoolean(   "decoder_us4state_fics"                    , DecodersParams.decoder_us4state_fics                    );
        if(mConfigToBeCompared == null || DecodersParams.decoder_usplanet_report_check_digit             != mConfigToBeCompared.DecodersParams.decoder_usplanet_report_check_digit       ) barcodeProps.putBoolean(   "decoder_usplanet_report_check_digit"      , DecodersParams.decoder_usplanet_report_check_digit      );
    }

    private void setupUPC_EANParams(Bundle barcodeProps)
    {
        if(mConfigToBeCompared == null || UpcEan.databar_to_upc_ean != mConfigToBeCompared.UpcEan.databar_to_upc_ean)
            barcodeProps.putBoolean("databar_to_upc_ean", UpcEan.databar_to_upc_ean);
        if(mConfigToBeCompared == null || UpcEan.upcean_bookland != mConfigToBeCompared.UpcEan.upcean_bookland)
            barcodeProps.putBoolean("upcean_bookland", UpcEan.upcean_bookland);
        if(mConfigToBeCompared == null || UpcEan.upcean_bookland_format != mConfigToBeCompared.UpcEan.upcean_bookland_format)
            barcodeProps.putString("upcean_bookland_format", UpcEan.upcean_bookland_format.toString());
        if(mConfigToBeCompared == null || UpcEan.upcean_coupon != mConfigToBeCompared.UpcEan.upcean_coupon)
            barcodeProps.putBoolean("upcean_coupon", UpcEan.upcean_coupon);
        if(mConfigToBeCompared == null || UpcEan.upcean_coupon_report != mConfigToBeCompared.UpcEan.upcean_coupon_report)
            barcodeProps.putString("upcean_coupon_report", UpcEan.upcean_coupon_report.toString());
        if(mConfigToBeCompared == null || UpcEan.upcean_ean_zero_extend != mConfigToBeCompared.UpcEan.upcean_ean_zero_extend)
            barcodeProps.putBoolean("upcean_ean_zero_extend", UpcEan.upcean_ean_zero_extend);
        if(mConfigToBeCompared == null || UpcEan.upcean_retry_count != mConfigToBeCompared.UpcEan.upcean_retry_count)
            barcodeProps.putInt("upcean_retry_count", UpcEan.upcean_retry_count);
        if(mConfigToBeCompared == null || UpcEan.upcean_security_level != mConfigToBeCompared.UpcEan.upcean_security_level)
            barcodeProps.putString("upcean_security_level", UpcEan.upcean_security_level.toString());
        if(mConfigToBeCompared == null || UpcEan.upcean_supplemental2 != mConfigToBeCompared.UpcEan.upcean_supplemental2)
            barcodeProps.putBoolean("upcean_supplemental2", UpcEan.upcean_supplemental2);
        if(mConfigToBeCompared == null || UpcEan.upcean_supplemental5 != mConfigToBeCompared.UpcEan.upcean_supplemental5)
            barcodeProps.putBoolean("upcean_supplemental5", UpcEan.upcean_supplemental5);
        if(mConfigToBeCompared == null || UpcEan.upcean_supplemental_mode != mConfigToBeCompared.UpcEan.upcean_supplemental_mode)
            barcodeProps.putString("upcean_supplemental_mode", UpcEan.upcean_supplemental_mode.toString());
        if(mConfigToBeCompared == null || UpcEan.upcean_linear_decode != mConfigToBeCompared.UpcEan.upcean_linear_decode)
            barcodeProps.putBoolean("upcean_linear_decode", UpcEan.upcean_linear_decode);
        if(mConfigToBeCompared == null || UpcEan.upcean_random_weight_check_digit != mConfigToBeCompared.UpcEan.upcean_random_weight_check_digit)
            barcodeProps.putBoolean("upcean_random_weight_check_digit", UpcEan.upcean_random_weight_check_digit);
    }

    private void setupReaderParams(Bundle barcodeProps, boolean switchParams) {

        // This parameter is not supported when switching parameters
        if(switchParams == false && (mConfigToBeCompared == null || ReaderParams.aim_mode != mConfigToBeCompared.ReaderParams.aim_mode))
            barcodeProps.putString("aim_mode", ReaderParams.aim_mode.toString());

        if(mConfigToBeCompared == null || ReaderParams.charset_name != mConfigToBeCompared.ReaderParams.charset_name)
            barcodeProps.putString("charset_name", ReaderParams.charset_name.toString());

        if(mConfigToBeCompared == null || ReaderParams.illumination_mode != mConfigToBeCompared.ReaderParams.illumination_mode)
            barcodeProps.putString("illumination_mode", ReaderParams.illumination_mode.toString());

        if(mConfigToBeCompared == null || ReaderParams.inverse_1d_mode != mConfigToBeCompared.ReaderParams.inverse_1d_mode)
            barcodeProps.putString("inverse_1d_mode", ReaderParams.inverse_1d_mode.toString());

        if(mConfigToBeCompared == null || ReaderParams.lcd_mode != mConfigToBeCompared.ReaderParams.lcd_mode)
            barcodeProps.putString("lcd_mode", ReaderParams.lcd_mode.toString());

        if(mConfigToBeCompared == null || ReaderParams.linear_security_level != mConfigToBeCompared.ReaderParams.linear_security_level)
            barcodeProps.putString("linear_security_level", ReaderParams.linear_security_level.toString());

        if(mConfigToBeCompared == null || ReaderParams.low_power_timeout != mConfigToBeCompared.ReaderParams.low_power_timeout)
            barcodeProps.putString("low_power_timeout", Integer.toString(ReaderParams.low_power_timeout));

        if(mConfigToBeCompared == null || ReaderParams.picklist != mConfigToBeCompared.ReaderParams.picklist)
            barcodeProps.putString("picklist", ReaderParams.picklist.toString());

        if(mConfigToBeCompared == null || ReaderParams.poor_quality_bcdecode_effort_level != mConfigToBeCompared.ReaderParams.poor_quality_bcdecode_effort_level)
            barcodeProps.putString("poor_quality_bcdecode_effort_level", ReaderParams.poor_quality_bcdecode_effort_level.toString());

        if(mConfigToBeCompared == null || ReaderParams.aim_timer != mConfigToBeCompared.ReaderParams.aim_timer)
            barcodeProps.putString("aim_timer", Integer.toString(ReaderParams.aim_timer));

        if(mConfigToBeCompared == null || ReaderParams.aim_type != mConfigToBeCompared.ReaderParams.aim_type)
            barcodeProps.putString("aim_type", ReaderParams.aim_type.toString());

        if(mConfigToBeCompared == null || ReaderParams.beam_timer != mConfigToBeCompared.ReaderParams.beam_timer)
            barcodeProps.putString("beam_timer", Integer.toString(ReaderParams.beam_timer));

        // This value must be a multiple of 500
        if(mConfigToBeCompared == null || ReaderParams.different_barcode_timeout != mConfigToBeCompared.ReaderParams.different_barcode_timeout)
        {
            // Let's brute force the multiple of 500
            int remainder = ReaderParams.different_barcode_timeout % 500;
            int different_barcode_timeout = (ReaderParams.different_barcode_timeout / 500) * 500 + (remainder > 250 ? 500 : 0);
            barcodeProps.putString("different_barcode_timeout", Integer.toString(different_barcode_timeout));
        }

        // This value must be a multiple of 500
        if(mConfigToBeCompared == null || ReaderParams.same_barcode_timeout != mConfigToBeCompared.ReaderParams.same_barcode_timeout)
        {
            // Let's brute force the multiple of 500
            int remainder = ReaderParams.same_barcode_timeout % 500;
            int same_barcode_timeout = (ReaderParams.same_barcode_timeout / 500) * 500 + (remainder > 250 ? 500 : 0);
            barcodeProps.putString("same_barcode_timeout", Integer.toString(same_barcode_timeout));
        }

        if(mConfigToBeCompared == null || ReaderParams.scanning_mode != mConfigToBeCompared.ReaderParams.scanning_mode)
            barcodeProps.putString("scanning_mode", ReaderParams.scanning_mode.toString());

    }

    private void setupScanParams(Bundle barcodeProps)
    {
        if(mConfigToBeCompared == null || ScanParams.code_id_type != mConfigToBeCompared.ScanParams.code_id_type)
            barcodeProps.putString("code_id_type", ScanParams.code_id_type.toString());

        if(mConfigToBeCompared == null || ScanParams.decode_haptic_feedback != mConfigToBeCompared.ScanParams.decode_haptic_feedback)
            barcodeProps.putBoolean("decode_haptic_feedback", ScanParams.decode_haptic_feedback);

        if(mConfigToBeCompared == null || ScanParams.decode_audio_feedback_uri != mConfigToBeCompared.ScanParams.decode_audio_feedback_uri)
            barcodeProps.putString("decode_audio_feedback_uri", ScanParams.decode_audio_feedback_uri.toString());

        if(mConfigToBeCompared == null || ScanParams.decoding_led_feedback != mConfigToBeCompared.ScanParams.decoding_led_feedback)
            barcodeProps.putBoolean("decoding_led_feedback", ScanParams.decoding_led_feedback);

        if(mConfigToBeCompared == null || ScanParams.good_decode_led_timer != mConfigToBeCompared.ScanParams.good_decode_led_timer)
            barcodeProps.putInt("good_decode_led_timer", ScanParams.good_decode_led_timer);

        if(mConfigToBeCompared == null || ScanParams.volume_slider_type != mConfigToBeCompared.ScanParams.volume_slider_type)
            barcodeProps.putString("volume_slider_type", ScanParams.volume_slider_type.toString());
    }

    private void setupMultiBarcode(Bundle barcodeProps)
    {
        if(mConfigToBeCompared == null || MultiBarcode.multi_barcode_count != mConfigToBeCompared.MultiBarcode.multi_barcode_count)
            barcodeProps.putInt("multi_barcode_count", MultiBarcode.multi_barcode_count);
    }

    private void setupOtherParameters(Bundle barcodeProps)
    {
        if(mConfigToBeCompared == null || trigger_wakeup != mConfigToBeCompared.trigger_wakeup)
            barcodeProps.putString("trigger-wakeup", trigger_wakeup ? "true" : "false");

        if(mConfigToBeCompared == null || MarginLess.code128_enable_marginless_decode != mConfigToBeCompared.MarginLess.code128_enable_marginless_decode)
            barcodeProps.putBoolean("decoding_led_feedback", MarginLess.code128_enable_marginless_decode);

        if(mConfigToBeCompared == null || MarginLess.code39_enable_marginless_decode != mConfigToBeCompared.MarginLess.code39_enable_marginless_decode)
            barcodeProps.putBoolean("code39_enable_marginless_decode", MarginLess.code39_enable_marginless_decode);

        if(mConfigToBeCompared == null || MarginLess.upc_enable_marginless_decode != mConfigToBeCompared.MarginLess.upc_enable_marginless_decode)
            barcodeProps.putBoolean("upc_enable_marginless_decode", MarginLess.upc_enable_marginless_decode);

        if(mConfigToBeCompared == null || MarginLess.i20f5_enable_marginless_decode != mConfigToBeCompared.MarginLess.i20f5_enable_marginless_decode)
            barcodeProps.putBoolean("i20f5_enable_marginless_decode", MarginLess.i20f5_enable_marginless_decode);
    }

    /**
     * Use this method if you want to switch between two knowns parameters
     * @param configToBeCompared
     * @return
     */
    public Bundle getBarcodePluginBundleForSwitchParams(PluginScanner configToBeCompared)
    {
        // Pass everything to the bundle
        mConfigToBeCompared = configToBeCompared;

        Bundle barcodeProps = new Bundle();

        // Setup Reader Params
        setupReaderParams(barcodeProps, true);

        return barcodeProps;
    }

    /**
     * Use this method if you want to force all parameters to be switched
     * @return
     */
    public Bundle getBarcodePluginBundleForSwitchParams()
    {
        // Pass everything to the bundle
        mConfigToBeCompared = null;

        Bundle barcodeProps = new Bundle();

        // Setup Reader Params
        setupReaderParams(barcodeProps, true);

        return barcodeProps;
    }
}

