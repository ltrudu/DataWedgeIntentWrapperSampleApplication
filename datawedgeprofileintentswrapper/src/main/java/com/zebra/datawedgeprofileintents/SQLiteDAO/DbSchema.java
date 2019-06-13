package com.zebra.datawedgeprofileintents.sqlitedao;

import android.provider.BaseColumns;

public class DbSchema {
    private static final String TAG = "DbSchema";

    public static final String DATABASE_NAME = "dwprofile_test.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SORT_ASC = " ASC";
    public static final String SORT_DESC = " DESC";
    public static final String[] ORDERS = {SORT_ASC,SORT_DESC};
    public static final int OFF = 0;
    public static final int ON = 1;

    public static final class Table_Profiles implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "profiles";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_NAME = "name";
        public static final String COL_DW_ENABLED = "dw_enabled";
        public static final String COL_ENABLED = "enabled";
        public static final String COL_DELETABLE = "deletable";
        public static final String COL_EDITABLE = "editable";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS profiles ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_NAME + " TEXT NOT NULL," + 
            COL_DW_ENABLED + " BOOLEAN NOT NULL," + 
            COL_ENABLED + " BOOLEAN NOT NULL," + 
            COL_DELETABLE + " BOOLEAN NOT NULL," + 
            COL_EDITABLE + " BOOLEAN NOT NULL );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS profiles;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_NAME,
            COL_DW_ENABLED,
            COL_ENABLED,
            COL_DELETABLE,
            COL_EDITABLE };
    }

    public static final class Table_Applist implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "applist";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_ACTIVITY = "activity";
        public static final String COL_PACKAGENAME = "packagename";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS applist ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_ACTIVITY + " TEXT NOT NULL," + 
            COL_PACKAGENAME + " TEXT NOT NULL );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS applist;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_ACTIVITY,
            COL_PACKAGENAME };
    }

    public static final class Table_Settings implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "settings";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS settings ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS settings;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugins implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugins";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PLUGIN_ID = "plugin_id";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugins ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PLUGIN_ID + " TEXT NOT NULL );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugins;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PLUGIN_ID };
    }

    public static final class Table_Info implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "info";

        // Table Columns
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS info ( " + 
            COL_PARAM_ID + " TEXT NOT NULL PRIMARY KEY ," + 
            COL_PARAM_VALUE + " TEXT NOT NULL );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS info;";

        // Columns list array
        public static final String[] allColumns = {
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Scanner_Params implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "scanner_params";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_SCANNER_TYPE = "scanner_type";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_CATEGORY = "param_category";
        public static final String COL_DISPLAY_NAME = "display_name";
        public static final String COL_DEFAULT_VALUE = "default_value";
        public static final String COL_VALUE_NAME = "value_name";
        public static final String COL_VALUE_TYPE = "value_type";
        public static final String COL_VALUE_MIN = "value_min";
        public static final String COL_VALUE_MAX = "value_max";
        public static final String COL_VALUES_DISCRETE_COUNT = "values_discrete_count";
        public static final String COL_VALUES_DISCRETE = "values_discrete";
        public static final String COL_VALUES_DISCRETE_NAMES = "values_discrete_names";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS scanner_params ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_SCANNER_TYPE + " TEXT NOT NULL," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_CATEGORY + " TEXT NOT NULL," + 
            COL_DISPLAY_NAME + " TEXT NOT NULL," + 
            COL_DEFAULT_VALUE + " TEXT NOT NULL," + 
            COL_VALUE_NAME + " TEXT NOT NULL," + 
            COL_VALUE_TYPE + " TEXT NOT NULL," + 
            COL_VALUE_MIN + " INTEGER," + 
            COL_VALUE_MAX + " INTEGER," + 
            COL_VALUES_DISCRETE_COUNT + " INTEGER," + 
            COL_VALUES_DISCRETE + " TEXT," + 
            COL_VALUES_DISCRETE_NAMES + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS scanner_params;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_SCANNER_TYPE,
            COL_PARAM_ID,
            COL_PARAM_CATEGORY,
            COL_DISPLAY_NAME,
            COL_DEFAULT_VALUE,
            COL_VALUE_NAME,
            COL_VALUE_TYPE,
            COL_VALUE_MIN,
            COL_VALUE_MAX,
            COL_VALUES_DISCRETE_COUNT,
            COL_VALUES_DISCRETE,
            COL_VALUES_DISCRETE_NAMES };
    }

    public static final class Table_Plugin_Input_Scanner implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_input_scanner";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_DEVICE_ID = "device_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";
        public static final String COL_SCANNER_TYPE = "scanner_type";
        public static final String COL_DISPLAY_NAME = "display_name";
        public static final String COL_VALUE_NAME = "value_name";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_input_scanner ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_DEVICE_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT," + 
            COL_SCANNER_TYPE + " TEXT," + 
            COL_DISPLAY_NAME + " TEXT," + 
            COL_VALUE_NAME + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_input_scanner;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_DEVICE_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE,
            COL_SCANNER_TYPE,
            COL_DISPLAY_NAME,
            COL_VALUE_NAME };
    }

    public static final class Table_Plugin_Output_Keystroke implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_output_keystroke";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_output_keystroke ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_output_keystroke;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugin_Output_Intent implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_output_intent";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_output_intent ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_output_intent;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugin_Process_Bdf implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_process_bdf";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_process_bdf ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_process_bdf;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugin_Input_Msr implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_input_msr";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_DEVICE_ID = "device_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_input_msr ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_DEVICE_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_input_msr;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_DEVICE_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugin_Process_Adf implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_process_adf";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_DEVICE_ID = "device_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_process_adf ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_DEVICE_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_process_adf;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_DEVICE_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugin_Output_Ip implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_output_ip";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_DEVICE_ID = "device_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_output_ip ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_DEVICE_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_output_ip;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_DEVICE_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugin_Input_Ssb implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_input_ssb";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_DEVICE_ID = "device_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_input_ssb ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_DEVICE_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_input_ssb;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_DEVICE_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Plugin_Process_Token implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "plugin_process_token";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_DEVICE_ID = "device_id";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plugin_process_token ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT," + 
            COL_DEVICE_ID + " TEXT," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS plugin_process_token;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_DEVICE_ID,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

    public static final class Table_Token_Selection implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "token_selection";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_PLUGIN_ID = "plugin_id";
        public static final String COL_NAME = "name";
        public static final String COL_PRIORITY = "priority";
        public static final String COL_ENABLED = "enabled";
        public static final String COL_ALLDEVICES = "alldevices";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS token_selection ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_PLUGIN_ID + " TEXT NOT NULL," + 
            COL_NAME + " TEXT NOT NULL," + 
            COL_PRIORITY + " INTEGER NOT NULL," + 
            COL_ENABLED + " BOOLEAN NOT NULL," + 
            COL_ALLDEVICES + " BOOLEAN NOT NULL );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS token_selection;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_PLUGIN_ID,
            COL_NAME,
            COL_PRIORITY,
            COL_ENABLED,
            COL_ALLDEVICES };
    }

    public static final class Table_Simulscan_Template_Params implements BaseColumns  { 
        // Table Name
        public static final String TABLE_NAME = "simulscan_template_params";

        // Table Columns
        public static final String COL__ID = "_id";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_TEMPLATE = "template";
        public static final String COL_PARAM_ID = "param_id";
        public static final String COL_PARAM_VALUE = "param_value";

        // Create Table Statement
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS simulscan_template_params ( " + 
            COL__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " + 
            COL_PROFILE_ID + " INTEGER NOT NULL," + 
            COL_TEMPLATE + " TEXT NOT NULL," + 
            COL_PARAM_ID + " TEXT NOT NULL," + 
            COL_PARAM_VALUE + " TEXT );";

        // Drop table statement
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS simulscan_template_params;";

        // Columns list array
        public static final String[] allColumns = {
            COL__ID,
            COL_PROFILE_ID,
            COL_TEMPLATE,
            COL_PARAM_ID,
            COL_PARAM_VALUE };
    }

}
