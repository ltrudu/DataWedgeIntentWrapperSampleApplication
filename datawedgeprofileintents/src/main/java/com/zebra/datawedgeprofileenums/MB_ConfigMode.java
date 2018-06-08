package com.zebra.datawedgeprofileenums;

/////////////////////////////////////////////////////////////////////////////////////////
// MAIN Bundle
/////////////////////////////////////////////////////////////////////////////////////////
    /*
    Set how the profile will be processed
    */
public enum MB_ConfigMode {
    CREATE_IF_NOT_EXIST, // Create profil if it does not exists
    OVERWRITE, // If Profile exists, resets all options to default, then configures specified settings
    UPDATE // Updates only specified settings
}
