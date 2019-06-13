package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugin_Process_Bdf extends DbManager {
    private static final String TAG = "DWProfileDAOPlugin_Process_Bdf";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugin_Process_Bdf.allColumns;


    protected DWProfileDAOPlugin_Process_Bdf() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugin_Process_Bdf loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugin_Process_Bdf plugin_process_bdf = new Plugin_Process_Bdf();
        plugin_process_bdf = cursorToPlugin_Process_Bdf(cursor);

        cursor.close();
        database_close();

        return plugin_process_bdf;
    }

    public static ArrayList<Plugin_Process_Bdf> loadAllRecords() {
        ArrayList<Plugin_Process_Bdf> plugin_process_bdfList = new ArrayList<Plugin_Process_Bdf>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Process_Bdf plugin_process_bdf = cursorToPlugin_Process_Bdf(cursor);
            plugin_process_bdfList.add(plugin_process_bdf);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_process_bdfList;
    }

    // Please always use the typed column names (Table_Plugin_Process_Bdf) when passing arguments.
    // Example: Table_Plugin_Process_Bdf.Column_Name
    public static ArrayList<Plugin_Process_Bdf> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugin_Process_Bdf> plugin_process_bdfList = new ArrayList<Plugin_Process_Bdf>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Process_Bdf plugin_process_bdf = cursorToPlugin_Process_Bdf(cursor);
            plugin_process_bdfList.add(plugin_process_bdf);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_process_bdfList;
    }

    public static long insertRecord(Plugin_Process_Bdf plugin_process_bdf) {
        ContentValues values = new ContentValues();
        values = getPlugin_Process_BdfValues(plugin_process_bdf);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugin_Process_Bdf plugin_process_bdf) { 
        ContentValues values = new ContentValues();
        values = getPlugin_Process_BdfValues(plugin_process_bdf);
        database_open();
        String[] where = new String[] { String.valueOf(plugin_process_bdf.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME , values, DbSchema.Table_Plugin_Process_Bdf.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugin_Process_Bdf plugin_process_bdf) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugin_process_bdf.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME , DbSchema.Table_Plugin_Process_Bdf.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME , DbSchema.Table_Plugin_Process_Bdf.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugin_Process_Bdf.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPlugin_Process_BdfValues(Plugin_Process_Bdf plugin_process_bdf) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugin_Process_Bdf.COL__ID, plugin_process_bdf.get_id());
        values.put(DbSchema.Table_Plugin_Process_Bdf.COL_PROFILE_ID, plugin_process_bdf.getprofile_id());
        values.put(DbSchema.Table_Plugin_Process_Bdf.COL_PLUGIN_ID, plugin_process_bdf.getplugin_id());
        values.put(DbSchema.Table_Plugin_Process_Bdf.COL_PARAM_ID, plugin_process_bdf.getparam_id());
        values.put(DbSchema.Table_Plugin_Process_Bdf.COL_PARAM_VALUE, plugin_process_bdf.getparam_value());

        return values;
    }

    protected static Plugin_Process_Bdf cursorToPlugin_Process_Bdf(Cursor cursor)  {
        Plugin_Process_Bdf plugin_process_bdf = new Plugin_Process_Bdf();

        plugin_process_bdf.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Bdf.COL__ID)));
        plugin_process_bdf.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Bdf.COL_PROFILE_ID)));
        plugin_process_bdf.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Bdf.COL_PLUGIN_ID)));
        plugin_process_bdf.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Bdf.COL_PARAM_ID)));
        plugin_process_bdf.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Bdf.COL_PARAM_VALUE)));

        return plugin_process_bdf;
    }

    

}

