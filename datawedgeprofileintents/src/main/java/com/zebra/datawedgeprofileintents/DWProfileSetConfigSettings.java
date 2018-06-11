package com.zebra.datawedgeprofileintents;

import com.zebra.datawedgeprofileenums.*;

/*
Add more initialisation parameters here
 */
public class DWProfileSetConfigSettings extends DWProfileBaseSettings
{
    /////////////////////////////////////////////////////////////////////////////////////////
    // MAIN Bundle
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
    Set if the profile should be enabled or not
     */
    public boolean mMB_ProfileEnabled = true;

    /*
    Set how the profile will be processed
     */
    public MB_E_CONFIG_MODE mMB_ConfigMode = MB_E_CONFIG_MODE.CREATE_IF_NOT_EXIST;

    /////////////////////////////////////////////////////////////////////////////////////////
    // APP LIST Bundle
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
    Set a specific package name if you want to create a profile for a different package than the current one
     */
    public String mAPPL_PackageName = "";

    /*
    Define the activity that will receive the intents from DataWedge
     */
    public String[] mAPPL_ActivityList = null;

    /////////////////////////////////////////////////////////////////////////////////////////
    // INTENT Plugin
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
    The action associated with the broadcasted intent
     */
    public String mINT_IntentAction = "";

    /*
    The category associated with the broadcast intent
     */
    public String mINT_IntentCategory = "";

    /////////////////////////////////////////////////////////////////////////////////////////
    // BDF Plugin
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
    Enable or disable BDF
     */
    public boolean mBDF_Enabled = false;

    /*
    Prefix to acquired data
     */
    public String mBDF_Prefix = "";

    /*
    Suffix to acquired data
     */
    public String mBDF_Suffix = "";

    /*
    Send Data ? set to false if you want only hex for exemple
    */
    public boolean mBDF_Send_Data = true;

    /*
    Send as Hexadecimal data
    */
    public boolean mBDF_Send_Hex = false;

    /*
    Send a TAB after the data
    */
    public boolean mBDF_Send_Tab = false;

    /*
    Send a Enter after the data
    */
    public boolean mBDF_Send_Enter = false;

    /////////////////////////////////////////////////////////////////////////////////////////
    // BARCODE Plugin... THE BIG ONE !!!!
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
    Set the scanner you want to use for this profile
    Default is internal imager
     */
    public SC_E_SCANNER_IDENTIFIER mSC_ScannerIdentifier = SC_E_SCANNER_IDENTIFIER.INTERNAL_IMAGER;

    /*
    Set the scanning mode
    Default is Single
     */
    public SC_E_SCANNINGMODE mSC_ScanningMode = SC_E_SCANNINGMODE.SINGLE;

    /*
    Number of multibarcode to read
    Default is 5
     */
    public int mSC_Multi_Barcode_Count = 5;

    /*
    Unknown parameter, if someone ca explain what it does ???
     */
    public boolean mSC_Trigger_WakeUp = false;

    /*
    Enable or disable current scanner input module.
    Useful to control when scan should be available in an application
    Default is true
     */
    public boolean mSC_Scanner_Input_Enabled = true;

    /****************************************/
    /*              Decoders                */
    /****************************************/
    public boolean mSC_Decoder_Australian_Postal = false;
    public boolean mSC_Decoder_Aztec = false;
    public boolean mSC_Decoder_Canadian_Postal = false;
    public boolean mSC_Decoder_Chinese_2of5 = false;
    public boolean mSC_Decoder_Codabar = false;
    public boolean mSC_Decoder_Codabar_Clsi_Editing = false;
    public int mSC_Decoder_Codabar_Length1 = 6;
    public int mSC_Decoder_Codabar_Length2 = 55;
    public boolean mSC_Decoder_Codabar_Notis_Editing = false;
    public boolean mSC_Decoder_Codabar_Redundancy = false;
    public boolean mSC_Decoder_Code11 = false;
    public int mSC_Decoder_Code11_Length1 = 4;
    public int mSC_Decoder_Code11_Length2 = 55;
    public boolean mSC_Decoder_Code11_Redundancy = false;
    public boolean mSC_Decoder_Code11_Report_Check_Digit = false;
    public SC_E_CODE11_VERIFY_CHECK_DIGIT mSC_Decoder_Code11_Verify_Check_Digit = SC_E_CODE11_VERIFY_CHECK_DIGIT.ONE_CHECK_DIGIT;
    public boolean mSC_Decoder_Code128 = false;
    public boolean mSC_Decoder_Code128_Check_Isbt_Table = false;
    public boolean mSC_Decoder_Code128_Enable_Ean128 = false;
    public boolean mSC_Decoder_Code128_Enable_Isbt128 = false;
    public boolean mSC_Decoder_Code128_Enable_Plain = false;
    public SC_E_CONCAT_MODE mSC_Decoder_Code128_Isbt128_Concat_Mode = SC_E_CONCAT_MODE.CONCAT_MODE_NEVER;
    public int mSC_Decoder_Code128_Length1 = 0;
    public int mSC_Decoder_Code128_Length2 = 55;
    public boolean mSC_Decoder_Code128_Redundancy = false;
    public SC_E_SECURITY_LEVEL mSC_Decoder_Code128_Security_Level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_1;
    public boolean mSC_Decoder_Code39 = false;
    public boolean mSC_Decoder_Code39_Convert_To_Code32 = false;
    public boolean mSC_Decoder_Code39_Full_Ascii = false;
    public int mSC_Decoder_Code39_Length1 = 0;
    public int mSC_Decoder_Code39_Length2 = 55;
    public boolean mSC_Decoder_Code39_Redundancy = false;
    public boolean mSC_Decoder_Code39_Report_Check_Digit = false;
    public boolean mSC_Decoder_Code39_Report_Code32_Prefix = false;
    public SC_E_SECURITY_LEVEL mSC_Decoder_Code39_Security_Level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_1;
    public boolean mSC_Decoder_Code39_Verify_Check_Digit = false;
    public boolean mSC_Decoder_Code93 = false;
    public int mSC_Decoder_Code93_Length1 = 0;
    public int mSC_Decoder_Code93_Length2 = 55;
    public boolean mSC_Decoder_Code93_Redundancy = false;
    public boolean mSC_Decoder_Composite_Ab = false;
    public SC_E_LINK_MODE mSC_Decoder_Composite_Ab_Ucc_Link_Mode = SC_E_LINK_MODE.AUTO_DISCRIMINATE;
    public boolean mSC_Decoder_Composite_C = false;
    public boolean mSC_Decoder_D2of5 = false;
    public int mSC_Decoder_D2of5_Length1 = 0;
    public int mSC_Decoder_D2of5_Length2 = 14;
    public boolean mSC_Decoder_D2of5_Redundancy = false;
    public boolean mSC_Decoder_Datamatrix = false;
    public boolean mSC_Decoder_Dutch_Postal = false;
    public boolean mSC_Decoder_Ean13 = false;
    public boolean mSC_Decoder_Ean8 = false;
    public boolean mSC_Decoder_Gs1_Databar = false;
    public boolean mSC_Decoder_Gs1_Databar_Exp = false;
    public boolean mSC_Decoder_Gs1_Databar_Lim = false;
    public SC_E_SECURITY_LEVEL mSC_Decoder_Gs1_Lim_Security_Level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_2;
    public boolean mSC_Decoder_Hanxin = false;
    public SC_E_INVERSE mSC_Decoder_Hanxin_Inverse = SC_E_INVERSE.DISABLE;
    public boolean mSC_Decoder_I2of5 = false;
    public SC_E_I2OF5_CHECK_DIGIT mSC_Decoder_I2of5_Check_Digit = SC_E_I2OF5_CHECK_DIGIT.NO_CHECK_DIGIT;
    public int mSC_Decoder_I2of5_Length1 = 6;
    public int mSC_Decoder_I2of5_Length2 = 55;
    public boolean mSC_Decoder_I2of5_Redundancy = false;
    public boolean mSC_Decoder_I2of5_Report_Check_Digit = false;
    public SC_E_SECURITY_LEVEL mSC_Decoder_I2of5_Security_Level = SC_E_SECURITY_LEVEL.SECURITY_LEVEL_1;
    public boolean mSC_Decoder_Itf14_Convert_To_Ean13 = false;
    public boolean mSC_Decoder_Japanese_Postal = false;
    public boolean mSC_Decoder_Korean_3of5 = false;
    public boolean mSC_Decoder_Mailmark = false;
    public boolean mSC_Decoder_Matrix_2of5 = false;
    public int mSC_Decoder_Matrix_2of5_Length1 = 10;
    public int mSC_Decoder_Matrix_2of5_Length2 = 0;
    public boolean mSC_Decoder_Matrix_2of5_Redundancy = false;
    public boolean mSC_Decoder_Matrix_2of5_Report_Check_Digit = false;
    public boolean mSC_Decoder_Matrix_2of5_Verify_Check_Digit = false;
    public boolean mSC_Decoder_Maxicode = false;
    public boolean mSC_Decoder_Micropdf = false;
    public boolean mSC_Decoder_Microqr = false;
    public boolean mSC_Decoder_Msi = false;
    public SC_E_MSI_CHECK_DIGIT mSC_Decoder_Msi_Check_Digit = SC_E_MSI_CHECK_DIGIT.ONE_CHECK_DIGIT;
    public SC_E_MSI_CHECK_DIGIT_SCHEME mSC_Decoder_Msi_Check_Digit_Scheme = SC_E_MSI_CHECK_DIGIT_SCHEME.MOD_11_10;
    public int mSC_Decoder_Msi_Length1 = 4;
    public int mSC_Decoder_Msi_Length2 = 55;
    public boolean mSC_Decoder_Msi_Redundancy = false;
    public boolean mSC_Decoder_Msi_Report_Check_Digit = false;
    public boolean mSC_Decoder_Pdf417 = false;
    public boolean mSC_Decoder_Qrcode = false;
    public boolean mSC_Decoder_Signature = false;
    public boolean mSC_Decoder_Tlc39 = false;
    public boolean mSC_Decoder_Trioptic39 = false;
    public boolean mSC_Decoder_Uk_Postal = false;
    public boolean mSC_Decoder_Uk_Postal_Report_Check_Digit = false;
    public boolean mSC_Decoder_Upca = false;
    public SC_E_PREAMBLE mSC_Decoder_Upca_Preamble = SC_E_PREAMBLE.PREAMBLE_SYS_CHAR;
    public boolean mSC_Decoder_Upca_Report_Check_Digit = false;
    public boolean mSC_Decoder_Upce0 = false;
    public boolean mSC_Decoder_Upce0_Convert_To_Upca = false;
    public SC_E_PREAMBLE mSC_Decoder_Upce0_Preamble = SC_E_PREAMBLE.PREAMBLE_NONE;
    public boolean mSC_Decoder_Upce0_Report_Check_Digit = false;
    public boolean mSC_Decoder_Upce1 = false;
    public boolean mSC_Decoder_Upce1_Convert_To_Upca = false;
    public SC_E_PREAMBLE mSC_Decoder_Upce1_Preamble = SC_E_PREAMBLE.PREAMBLE_NONE;
    public boolean mSC_Decoder_Upce1_Report_Check_Digit = false;
    public boolean mSC_Decoder_Us4state = false;
    public boolean mSC_Decoder_Us4state_Fics = false;
    public boolean mSC_Decoder_Usplanet = false;
    public boolean mSC_Decoder_Usplanet_Report_Check_Digit = false;
    public boolean mSC_Decoder_Uspostnet = false;

    /****************************************/
    /*              UPCEAN                  */
    /****************************************/


}
