package com.zebra.datawedgeprofileenums;

public class DWScannerConfigEnumsHelper {

    /////////////////////////////////////////////////////////////////////////////////////////
    // MAIN Bundle
    /////////////////////////////////////////////////////////////////////////////////////////
    /*
    Config mode, describe how the profile will be processed
     */
    public static String MB_GetConfigMode(MB_ConfigMode configMode)
    {
        String selectedConfigMode = "";
        switch(configMode)
        {
            case CREATE_IF_NOT_EXIST: //Creates the Profile if string in PROFILE_NAME is not present on device
                selectedConfigMode = "CREATE_IF_NOT_EXIST";
                break;
            case OVERWRITE: // If Profile exists, resets all options to default, then configures specified settings
                selectedConfigMode = "OVERWRITE";
                break;
            case UPDATE: //Updates only specified settings
                selectedConfigMode = "UPDATE";
                break;
        }
        return selectedConfigMode;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // BARCODE Plugin... THE BIG ONE !!!!
    /////////////////////////////////////////////////////////////////////////////////////////
    public static String SC_GetScannerIdentifier(SC_ScannerIdentifier scanneridentifier)
    {
        String selectedScanner = "";
        switch(scanneridentifier)
        {
            case AUTO: //Automatic scanner selection
                selectedScanner = "AUTO";
                break;
            case INTERNAL_IMAGER: //Built-in imager scanner
                selectedScanner = "INTERNAL_IMAGER";
                break;
            case INTERNAL_LASER: //Built-in laser scanner
                selectedScanner = "INTERNAL_LASER";
                break;
            case INTERNAL_CAMERA: //Built-in camera scanner
                selectedScanner = "INTERNAL_CAMERA";
                break;
            case SERIAL_SSI: //Pluggable Z-back scanner for ET50/ET55
                selectedScanner = "SERIAL_SSI";
                break;
            case BLUETOOTH_SSI: //RS507 Bluetooth scanner
                selectedScanner = "BLUETOOTH_SSI";
                break;
            case BLUETOOTH_RS6000: //RS6000 Bluetooth scanner
                selectedScanner = "BLUETOOTH_RS6000";
                break;
            case BLUETOOTH_DS3678: //DS3678 Bluetooth scanner
                selectedScanner = "BLUETOOTH_DS3678";
                break;
            case PLUGABLE_SSI: //Serial SSI scanner RS429 (for use with WT6000)
                selectedScanner = "PLUGABLE_SSI";
                break;
            case PLUGABLE_SSI_RS5000: //Serial SSI scanner RS5000 (for use with WT6000)
                selectedScanner = "PLUGABLE_SSI_RS5000";
                break;
            case USB_SSI_DS3608: //DS3608 pluggable USB scanner
                selectedScanner = "USB_SSI_DS3608";
                break;
        }
        return selectedScanner;
    }

    public static String SC_GetScanningMode(SC_ScanningMode value)
    {
        String returnString = "";
        switch(value)
        {
            case SINGLE:
                returnString = "Single";
                break;
            case UDI:
                returnString = "UDI";
                break;
            case MULTIBARCODE:
                returnString = "MultiBarcode";
                break;
        }
        return returnString;
    }






    public static String SC_Get(SC_ScanningMode value)
    {
        String returnString = "";
        switch(value)
        {
            case SINGLE:
                returnString = "";
                break;
        }
        return returnString;
    }

}
