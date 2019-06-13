package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugin_Input_Msr extends DbManager {
    private static final String TAG = "DWProfileDAOPlugin_Input_Msr";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugin_Input_Msr.allColumns;


    protected DWProfileDAOPlugin_Input_Msr() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugin_Input_Msr loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugin_Input_Msr.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugin_Input_Msr plugin_input_msr = new Plugin_Input_Msr();
        plugin_input_msr = cursorToPlugin_Input_Msr(cursor);

        cursor.close();
        database_close();

        return plugin_input_msr;
    }

    public static ArrayList<Plugin_Input_Msr> loadAllRecords() {
        ArrayList<Plugin_Input_Msr> plugin_input_msrList = new ArrayList<Plugin_Input_Msr>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Input_Msr.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Input_Msr plugin_input_msr = cursorToPlugin_Input_Msr(cursor);
            plugin_input_msrList.add(plugin_input_msr);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_input_msrList;
    }

    // Please always use the typed column names (Table_Plugin_Input_Msr) when passing arguments.
    // Example: Table_Plugin_Input_Msr.Column_Name
    public static ArrayList<Plugin_Input_Msr> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugin_Input_Msr> plugin_input_msrList = new ArrayList<Plugin_Input_Msr>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Input_Msr.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Input_Msr plugin_input_msr = cursorToPlugin_Input_Msr(cursor);
            plugin_input_msrList.add(plugin_input_msr);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_input_msrList;
    }

    public static long insertRecord(Plugin_Input_Msr plugin_input_msr) {
        ContentValues values = new ContentValues();
        values = getPlugin_Input_MsrValues(plugin_input_msr);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugin_Input_Msr.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugin_Input_Msr plugin_input_msr) { 
        ContentValues values = new ContentValues();
        values = getPlugin_Input_MsrValues(plugin_input_msr);
        database_open();
        String[] where = new String[] { String.valueOf(plugin_input_msr.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugin_Input_Msr.TABLE_NAME , values, DbSchema.Table_Plugin_Input_Msr.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugin_Input_Msr plugin_input_msr) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugin_input_msr.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Msr.TABLE_NAME , DbSchema.Table_Plugin_Input_Msr.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Msr.TABLE_NAME , DbSchema.Table_Plugin_Input_Msr.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Msr.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPlugin_Input_MsrValues(Plugin_Input_Msr plugin_input_msr) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugin_Input_Msr.COL__ID, plugin_input_msr.get_id());
        values.put(DbSchema.Table_Plugin_Input_Msr.COL_PROFILE_ID, plugin_input_msr.getprofile_id());
        values.put(DbSchema.Table_Plugin_Input_Msr.COL_PLUGIN_ID, plugin_input_msr.getplugin_id());
        values.put(DbSchema.Table_Plugin_Input_Msr.COL_DEVICE_ID, plugin_input_msr.getdevice_id());
        values.put(DbSchema.Table_Plugin_Input_Msr.COL_PARAM_ID, plugin_input_msr.getparam_id());
        values.put(DbSchema.Table_Plugin_Input_Msr.COL_PARAM_VALUE, plugin_input_msr.getparam_value());

        return values;
    }

    protected static Plugin_Input_Msr cursorToPlugin_Input_Msr(Cursor cursor)  {
        Plugin_Input_Msr plugin_input_msr = new Plugin_Input_Msr();

        plugin_input_msr.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Msr.COL__ID)));
        plugin_input_msr.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Msr.COL_PROFILE_ID)));
        plugin_input_msr.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Msr.COL_PLUGIN_ID)));
        plugin_input_msr.setdevice_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Msr.COL_DEVICE_ID)));
        plugin_input_msr.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Msr.COL_PARAM_ID)));
        plugin_input_msr.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Msr.COL_PARAM_VALUE)));

        return plugin_input_msr;
    }

    

}

