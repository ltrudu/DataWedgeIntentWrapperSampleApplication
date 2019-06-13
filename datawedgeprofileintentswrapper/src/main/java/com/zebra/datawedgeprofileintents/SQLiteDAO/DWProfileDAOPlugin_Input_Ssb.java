package com.zebra.datawedgeprofileintents.sqlitedao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugin_Input_Ssb extends DbManager {
    private static final String TAG = "DWProfileDAOPlugin_Input_Ssb";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugin_Input_Ssb.allColumns;


    protected DWProfileDAOPlugin_Input_Ssb() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugin_Input_Ssb loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugin_Input_Ssb plugin_input_ssb = new Plugin_Input_Ssb();
        plugin_input_ssb = cursorToPlugin_Input_Ssb(cursor);

        cursor.close();
        database_close();

        return plugin_input_ssb;
    }

    public static ArrayList<Plugin_Input_Ssb> loadAllRecords() {
        ArrayList<Plugin_Input_Ssb> plugin_input_ssbList = new ArrayList<Plugin_Input_Ssb>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Input_Ssb plugin_input_ssb = cursorToPlugin_Input_Ssb(cursor);
            plugin_input_ssbList.add(plugin_input_ssb);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_input_ssbList;
    }

    // Please always use the typed column names (Table_Plugin_Input_Ssb) when passing arguments.
    // Example: Table_Plugin_Input_Ssb.Column_Name
    public static ArrayList<Plugin_Input_Ssb> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugin_Input_Ssb> plugin_input_ssbList = new ArrayList<Plugin_Input_Ssb>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Input_Ssb plugin_input_ssb = cursorToPlugin_Input_Ssb(cursor);
            plugin_input_ssbList.add(plugin_input_ssb);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_input_ssbList;
    }

    public static long insertRecord(Plugin_Input_Ssb plugin_input_ssb) {
        ContentValues values = new ContentValues();
        values = getPlugin_Input_SsbValues(plugin_input_ssb);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugin_Input_Ssb plugin_input_ssb) { 
        ContentValues values = new ContentValues();
        values = getPlugin_Input_SsbValues(plugin_input_ssb);
        database_open();
        String[] where = new String[] { String.valueOf(plugin_input_ssb.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME , values, DbSchema.Table_Plugin_Input_Ssb.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugin_Input_Ssb plugin_input_ssb) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugin_input_ssb.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME , DbSchema.Table_Plugin_Input_Ssb.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME , DbSchema.Table_Plugin_Input_Ssb.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugin_Input_Ssb.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPlugin_Input_SsbValues(Plugin_Input_Ssb plugin_input_ssb) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugin_Input_Ssb.COL__ID, plugin_input_ssb.get_id());
        values.put(DbSchema.Table_Plugin_Input_Ssb.COL_PROFILE_ID, plugin_input_ssb.getprofile_id());
        values.put(DbSchema.Table_Plugin_Input_Ssb.COL_PLUGIN_ID, plugin_input_ssb.getplugin_id());
        values.put(DbSchema.Table_Plugin_Input_Ssb.COL_DEVICE_ID, plugin_input_ssb.getdevice_id());
        values.put(DbSchema.Table_Plugin_Input_Ssb.COL_PARAM_ID, plugin_input_ssb.getparam_id());
        values.put(DbSchema.Table_Plugin_Input_Ssb.COL_PARAM_VALUE, plugin_input_ssb.getparam_value());

        return values;
    }

    protected static Plugin_Input_Ssb cursorToPlugin_Input_Ssb(Cursor cursor)  {
        Plugin_Input_Ssb plugin_input_ssb = new Plugin_Input_Ssb();

        plugin_input_ssb.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Ssb.COL__ID)));
        plugin_input_ssb.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Ssb.COL_PROFILE_ID)));
        plugin_input_ssb.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Ssb.COL_PLUGIN_ID)));
        plugin_input_ssb.setdevice_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Ssb.COL_DEVICE_ID)));
        plugin_input_ssb.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Ssb.COL_PARAM_ID)));
        plugin_input_ssb.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Input_Ssb.COL_PARAM_VALUE)));

        return plugin_input_ssb;
    }

    

}

