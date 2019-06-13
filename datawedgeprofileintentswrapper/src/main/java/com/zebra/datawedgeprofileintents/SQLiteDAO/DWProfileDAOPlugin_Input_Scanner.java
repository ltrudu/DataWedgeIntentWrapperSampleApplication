package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugin_Input_Scanner extends DbManager {
    private static final String TAG = "DWProfileDAOPlugin_Input_Scanner";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugin_Input_Scanner.allColumns;


    protected DWProfileDAOPlugin_Input_Scanner() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugin_Input_Scanner loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugin_Input_Scanner plugin_input_scanner = new Plugin_Input_Scanner();
        plugin_input_scanner = cursorToPlugin_Input_Scanner(cursor);

        cursor.close();
        database_close();

        return plugin_input_scanner;
    }

    public static ArrayList<Plugin_Input_Scanner> loadAllRecords() {
        ArrayList<Plugin_Input_Scanner> plugin_input_scannerList = new ArrayList<Plugin_Input_Scanner>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Input_Scanner plugin_input_scanner = cursorToPlugin_Input_Scanner(cursor);
            plugin_input_scannerList.add(plugin_input_scanner);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_input_scannerList;
    }

    // Please always use the typed column names (Table_Plugin_Input_Scanner) when passing arguments.
    // Example: Table_Plugin_Input_Scanner.Column_Name
    public static ArrayList<Plugin_Input_Scanner> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugin_Input_Scanner> plugin_input_scannerList = new ArrayList<Plugin_Input_Scanner>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Input_Scanner plugin_input_scanner = cursorToPlugin_Input_Scanner(cursor);
            plugin_input_scannerList.add(plugin_input_scanner);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_input_scannerList;
    }

    public static long insertRecord(Plugin_Input_Scanner plugin_input_scanner) {
        ContentValues values = new ContentValues();
        values = getPlugin_Input_ScannerValues(plugin_input_scanner);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugin_Input_Scanner plugin_input_scanner) { 
        ContentValues values = new ContentValues();
        values = getPlugin_Input_ScannerValues(plugin_input_scanner);
        database_open();
        String[] where = new String[] { String.valueOf(plugin_input_scanner.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME , values, DbSchema.Table_Plugin_Input_Scanner.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugin_Input_Scanner plugin_input_scanner) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugin_input_scanner.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME , DbSchema.Table_Plugin_Input_Scanner.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME , DbSchema.Table_Plugin_Input_Scanner.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Scanner.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPlugin_Input_ScannerValues(Plugin_Input_Scanner plugin_input_scanner) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugin_Input_Scanner.COL__ID, plugin_input_scanner.get_id());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_PROFILE_ID, plugin_input_scanner.getprofile_id());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_PLUGIN_ID, plugin_input_scanner.getplugin_id());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_DEVICE_ID, plugin_input_scanner.getdevice_id());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_PARAM_ID, plugin_input_scanner.getparam_id());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_PARAM_VALUE, plugin_input_scanner.getparam_value());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_SCANNER_TYPE, plugin_input_scanner.getscanner_type());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_DISPLAY_NAME, plugin_input_scanner.getdisplay_name());
        values.put(DbSchema.Table_Plugin_Input_Scanner.COL_VALUE_NAME, plugin_input_scanner.getvalue_name());

        return values;
    }

    protected static Plugin_Input_Scanner cursorToPlugin_Input_Scanner(Cursor cursor)  {
        Plugin_Input_Scanner plugin_input_scanner = new Plugin_Input_Scanner();

        plugin_input_scanner.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL__ID)));
        plugin_input_scanner.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_PROFILE_ID)));
        plugin_input_scanner.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_PLUGIN_ID)));
        plugin_input_scanner.setdevice_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_DEVICE_ID)));
        plugin_input_scanner.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_PARAM_ID)));
        plugin_input_scanner.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_PARAM_VALUE)));
        plugin_input_scanner.setscanner_type(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_SCANNER_TYPE)));
        plugin_input_scanner.setdisplay_name(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_DISPLAY_NAME)));
        plugin_input_scanner.setvalue_name(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Scanner.COL_VALUE_NAME)));

        return plugin_input_scanner;
    }

    

}

