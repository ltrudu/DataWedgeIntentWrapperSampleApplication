package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugin_Output_Ip extends DbManager {
    private static final String TAG = "DWProfileDAOPlugin_Output_Ip";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugin_Output_Ip.allColumns;


    protected DWProfileDAOPlugin_Output_Ip() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugin_Output_Ip loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugin_Output_Ip.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugin_Output_Ip plugin_output_ip = new Plugin_Output_Ip();
        plugin_output_ip = cursorToPlugin_Output_Ip(cursor);

        cursor.close();
        database_close();

        return plugin_output_ip;
    }

    public static ArrayList<Plugin_Output_Ip> loadAllRecords() {
        ArrayList<Plugin_Output_Ip> plugin_output_ipList = new ArrayList<Plugin_Output_Ip>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Output_Ip.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Output_Ip plugin_output_ip = cursorToPlugin_Output_Ip(cursor);
            plugin_output_ipList.add(plugin_output_ip);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_output_ipList;
    }

    // Please always use the typed column names (Table_Plugin_Output_Ip) when passing arguments.
    // Example: Table_Plugin_Output_Ip.Column_Name
    public static ArrayList<Plugin_Output_Ip> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugin_Output_Ip> plugin_output_ipList = new ArrayList<Plugin_Output_Ip>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Output_Ip.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Output_Ip plugin_output_ip = cursorToPlugin_Output_Ip(cursor);
            plugin_output_ipList.add(plugin_output_ip);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_output_ipList;
    }

    public static long insertRecord(Plugin_Output_Ip plugin_output_ip) {
        ContentValues values = new ContentValues();
        values = getPlugin_Output_IpValues(plugin_output_ip);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugin_Output_Ip.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugin_Output_Ip plugin_output_ip) { 
        ContentValues values = new ContentValues();
        values = getPlugin_Output_IpValues(plugin_output_ip);
        database_open();
        String[] where = new String[] { String.valueOf(plugin_output_ip.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugin_Output_Ip.TABLE_NAME , values, DbSchema.Table_Plugin_Output_Ip.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugin_Output_Ip plugin_output_ip) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugin_output_ip.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Output_Ip.TABLE_NAME , DbSchema.Table_Plugin_Output_Ip.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Output_Ip.TABLE_NAME , DbSchema.Table_Plugin_Output_Ip.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugin_Output_Ip.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPlugin_Output_IpValues(Plugin_Output_Ip plugin_output_ip) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugin_Output_Ip.COL__ID, plugin_output_ip.get_id());
        values.put(DbSchema.Table_Plugin_Output_Ip.COL_PROFILE_ID, plugin_output_ip.getprofile_id());
        values.put(DbSchema.Table_Plugin_Output_Ip.COL_PLUGIN_ID, plugin_output_ip.getplugin_id());
        values.put(DbSchema.Table_Plugin_Output_Ip.COL_DEVICE_ID, plugin_output_ip.getdevice_id());
        values.put(DbSchema.Table_Plugin_Output_Ip.COL_PARAM_ID, plugin_output_ip.getparam_id());
        values.put(DbSchema.Table_Plugin_Output_Ip.COL_PARAM_VALUE, plugin_output_ip.getparam_value());

        return values;
    }

    protected static Plugin_Output_Ip cursorToPlugin_Output_Ip(Cursor cursor)  {
        Plugin_Output_Ip plugin_output_ip = new Plugin_Output_Ip();

        plugin_output_ip.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Ip.COL__ID)));
        plugin_output_ip.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Ip.COL_PROFILE_ID)));
        plugin_output_ip.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Ip.COL_PLUGIN_ID)));
        plugin_output_ip.setdevice_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Ip.COL_DEVICE_ID)));
        plugin_output_ip.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Ip.COL_PARAM_ID)));
        plugin_output_ip.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Ip.COL_PARAM_VALUE)));

        return plugin_output_ip;
    }

    

}

