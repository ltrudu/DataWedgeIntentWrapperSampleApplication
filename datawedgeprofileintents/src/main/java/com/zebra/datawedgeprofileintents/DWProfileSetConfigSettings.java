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
    public MB_ConfigMode mMB_ConfigMode = MB_ConfigMode.CREATE_IF_NOT_EXIST;

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
    public SC_ScannerIdentifier mSC_ScannerIdentifier = SC_ScannerIdentifier.INTERNAL_IMAGER;

    /*
    Set the scanning mode
    Default is Single
     */
    public SC_ScanningMode mSC_ScanningMode = SC_ScanningMode.SINGLE;

    /*
    Number of multibarcode to read
    Default is 5
     */
    public int mSC_Multi_Barcode_Count = 5;

    /*
    Unknown parameter that is not enabled until we know what it does
     */
    //public boolean mSC_Trigger_WakeUp = false;

    /*
    Enable or disable current scanner input module.
    Useful to control when scan should be available in an application
    Default is true
     */
    public boolean mSC_Scanner_Input_Enabled = true;

    /****************************************/
    /*              Decoders                */
    /****************************************/
    public boolean Decoder_Australian_Postal = false;
    public boolean Decoder_Aztec = false;
    public boolean Decoder_Canadian_Postal = false;
    public boolean Decoder_Chinese_2of5 = false;
    public boolean Decoder_Codabar = false;
    public boolean Decoder_Codabar_Clsi_Editing = false;
    public int Decoder_Codabar_Length1 = 6;
    public int Decoder_Codabar_Length2 = 55;
    public boolean Decoder_Codabar_Notis_Editing = false;
    public boolean Decoder_Codabar_Redundancy = false;
    public boolean Decoder_Code11 = false;
    public int Decoder_Code11_Length1 = 4;
    public int Decoder_Code11_Length2 = 55;
    public boolean Decoder_Code11_Redundancy = false;
    public boolean Decoder_Code11_Report_Check_Digit = false;
    public int Decoder_Code11_Verify_Check_Digit = 1;
    public boolean Decoder_Code128 = false;
    public boolean Decoder_Code128_Check_Isbt_Table = false;
    public boolean Decoder_Code128_Enable_Ean128 = false;
    public boolean Decoder_Code128_Enable_Isbt128 = false;
    public boolean Decoder_Code128_Enable_Plain = false;
    public int Decoder_Code128_Isbt128_Concat_Mode = 0;
    public int Decoder_Code128_Length1 = 0;
    public int Decoder_Code128_Length2 = 55;
    public boolean Decoder_Code128_Redundancy = false;
    public int Decoder_Code128_Security_Level = 1;
    public boolean Decoder_Code39 = false;
    public boolean Decoder_Code39_Convert_To_Code32 = false;
    public boolean Decoder_Code39_Full_Ascii = false;
    public int Decoder_Code39_Length1 = 0;
    public int Decoder_Code39_Length2 = 55;
    public boolean Decoder_Code39_Redundancy = false;
    public boolean Decoder_Code39_Report_Check_Digit = false;
    public boolean Decoder_Code39_Report_Code32_Prefix = false;
    public int Decoder_Code39_Security_Level = 1;
    public boolean Decoder_Code39_Verify_Check_Digit = false;
    public boolean Decoder_Code93 = false;
    public int Decoder_Code93_Length1 = 0;
    public int Decoder_Code93_Length2 = 55;
    public boolean Decoder_Code93_Redundancy = false;
    public boolean Decoder_Composite_Ab = false;
    public int Decoder_Composite_Ab_Ucc_Link_Mode = 2;
    public boolean Decoder_Composite_C = false;
    public boolean Decoder_D2of5 = false;
    public int Decoder_D2of5_Length1 = 0;
    public int Decoder_D2of5_Length2 = 14;
    public boolean Decoder_D2of5_Redundancy = false;
    public boolean Decoder_Datamatrix = false;
    public boolean Decoder_Dutch_Postal = false;
    public boolean Decoder_Ean13 = false;
    public boolean Decoder_Ean8 = false;
    public boolean Decoder_Gs1_Databar = false;
    public boolean Decoder_Gs1_Databar_Exp = false;
    public boolean Decoder_Gs1_Databar_Lim = false;
    public int Decoder_Gs1_Lim_Security_Level = 2;
    public boolean Decoder_Hanxin = false;
    public int Decoder_Hanxin_Inverse = 0;
    public boolean Decoder_I2of5 = false;
    public int Decoder_I2of5_Check_Digit = 0;
    public int Decoder_I2of5_Length1 = 6;
    public int Decoder_I2of5_Length2 = 55;
    public boolean Decoder_I2of5_Redundancy = false;
    public boolean Decoder_I2of5_Report_Check_Digit = false;
    public int Decoder_I2of5_Security_Level = 1;
    public boolean Decoder_Itf14_Convert_To_Ean13 = false;
    public boolean Decoder_Japanese_Postal = false;
    public boolean Decoder_Korean_3of5 = false;
    public boolean Decoder_Mailmark = false;
    public boolean Decoder_Matrix_2of5 = false;
    public int Decoder_Matrix_2of5_Length1 = 10;
    public int Decoder_Matrix_2of5_Length2 = 0;
    public boolean Decoder_Matrix_2of5_Redundancy = false;
    public boolean Decoder_Matrix_2of5_Report_Check_Digit = false;
    public boolean Decoder_Matrix_2of5_Verify_Check_Digit = false;
    public boolean Decoder_Maxicode = false;
    public boolean Decoder_Micropdf = false;
    public boolean Decoder_Microqr = false;
    public boolean Decoder_Msi = false;
    public int Decoder_Msi_Check_Digit = 0;
    public int Decoder_Msi_Check_Digit_Scheme = 0;
    public int Decoder_Msi_Length1 = 4;
    public int Decoder_Msi_Length2 = 55;
    public boolean Decoder_Msi_Redundancy = false;
    public boolean Decoder_Msi_Report_Check_Digit = false;
    public boolean Decoder_Pdf417 = false;
    public boolean Decoder_Qrcode = false;
    public boolean Decoder_Signature = false;
    public boolean Decoder_Tlc39 = false;
    public boolean Decoder_Trioptic39 = false;
    public boolean Decoder_Uk_Postal = false;
    public boolean Decoder_Uk_Postal_Report_Check_Digit = false;
    public boolean Decoder_Upca = false;
    public int Decoder_Upca_Preamble = 1;
    public boolean Decoder_Upca_Report_Check_Digit = false;
    public boolean Decoder_Upce0 = false;
    public boolean Decoder_Upce0_Convert_To_Upca = false;
    public int Decoder_Upce0_Preamble = 0;
    public boolean Decoder_Upce0_Report_Check_Digit = false;
    public boolean Decoder_Upce1 = false;
    public boolean Decoder_Upce1_Convert_To_Upca = false;
    public int Decoder_Upce1_Preamble = 0;
    public boolean Decoder_Upce1_Report_Check_Digit = false;
    public boolean Decoder_Us4state = false;
    public boolean Decoder_Us4state_Fics = false;
    public boolean Decoder_Usplanet = false;
    public boolean Decoder_Usplanet_Report_Check_Digit = false;
    public boolean Decoder_Uspostnet = false;


}
