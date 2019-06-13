package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugin_Process_Token extends DbManager {
    private static final String TAG = "DWProfileDAOPlugin_Process_Token";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugin_Process_Token.allColumns;


    protected DWProfileDAOPlugin_Process_Token() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugin_Process_Token loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugin_Process_Token.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugin_Process_Token plugin_process_token = new Plugin_Process_Token();
        plugin_process_token = cursorToPlugin_Process_Token(cursor);

        cursor.close();
        database_close();

        return plugin_process_token;
    }

    public static ArrayList<Plugin_Process_Token> loadAllRecords() {
        ArrayList<Plugin_Process_Token> plugin_process_tokenList = new ArrayList<Plugin_Process_Token>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Process_Token.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Process_Token plugin_process_token = cursorToPlugin_Process_Token(cursor);
            plugin_process_tokenList.add(plugin_process_token);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_process_tokenList;
    }

    // Please always use the typed column names (Table_Plugin_Process_Token) when passing arguments.
    // Example: Table_Plugin_Process_Token.Column_Name
    public static ArrayList<Plugin_Process_Token> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugin_Process_Token> plugin_process_tokenList = new ArrayList<Plugin_Process_Token>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Process_Token.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Process_Token plugin_process_token = cursorToPlugin_Process_Token(cursor);
            plugin_process_tokenList.add(plugin_process_token);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_process_tokenList;
    }

    public static long insertRecord(Plugin_Process_Token plugin_process_token) {
        ContentValues values = new ContentValues();
        values = getPlugin_Process_TokenValues(plugin_process_token);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugin_Process_Token.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugin_Process_Token plugin_process_token) { 
        ContentValues values = new ContentValues();
        values = getPlugin_Process_TokenValues(plugin_process_token);
        database_open();
        String[] where = new String[] { String.valueOf(plugin_process_token.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugin_Process_Token.TABLE_NAME , values, DbSchema.Table_Plugin_Process_Token.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugin_Process_Token plugin_process_token) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugin_process_token.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Process_Token.TABLE_NAME , DbSchema.Table_Plugin_Process_Token.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Process_Token.TABLE_NAME , DbSchema.Table_Plugin_Process_Token.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugin_Process_Token.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPlugin_Process_TokenValues(Plugin_Process_Token plugin_process_token) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugin_Process_Token.COL__ID, plugin_process_token.get_id());
        values.put(DbSchema.Table_Plugin_Process_Token.COL_PROFILE_ID, plugin_process_token.getprofile_id());
        values.put(DbSchema.Table_Plugin_Process_Token.COL_PLUGIN_ID, plugin_process_token.getplugin_id());
        values.put(DbSchema.Table_Plugin_Process_Token.COL_DEVICE_ID, plugin_process_token.getdevice_id());
        values.put(DbSchema.Table_Plugin_Process_Token.COL_PARAM_ID, plugin_process_token.getparam_id());
        values.put(DbSchema.Table_Plugin_Process_Token.COL_PARAM_VALUE, plugin_process_token.getparam_value());

        return values;
    }

    protected static Plugin_Process_Token cursorToPlugin_Process_Token(Cursor cursor)  {
        Plugin_Process_Token plugin_process_token = new Plugin_Process_Token();

        plugin_process_token.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Token.COL__ID)));
        plugin_process_token.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Token.COL_PROFILE_ID)));
        plugin_process_token.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Token.COL_PLUGIN_ID)));
        plugin_process_token.setdevice_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Token.COL_DEVICE_ID)));
        plugin_process_token.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Token.COL_PARAM_ID)));
        plugin_process_token.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Process_Token.COL_PARAM_VALUE)));

        return plugin_process_token;
    }

    

}

