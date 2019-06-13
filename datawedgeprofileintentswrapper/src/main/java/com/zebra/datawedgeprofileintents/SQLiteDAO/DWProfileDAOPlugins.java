package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugins extends DbManager {
    private static final String TAG = "DWProfileDAOPlugins";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugins.allColumns;


    protected DWProfileDAOPlugins() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugins loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugins.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugins plugins = new Plugins();
        plugins = cursorToPlugins(cursor);

        cursor.close();
        database_close();

        return plugins;
    }

    public static ArrayList<Plugins> loadAllRecords() {
        ArrayList<Plugins> pluginsList = new ArrayList<Plugins>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugins.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugins plugins = cursorToPlugins(cursor);
            pluginsList.add(plugins);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return pluginsList;
    }

    // Please always use the typed column names (Table_Plugins) when passing arguments.
    // Example: Table_Plugins.Column_Name
    public static ArrayList<Plugins> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugins> pluginsList = new ArrayList<Plugins>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugins.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugins plugins = cursorToPlugins(cursor);
            pluginsList.add(plugins);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return pluginsList;
    }

    public static long insertRecord(Plugins plugins) {
        ContentValues values = new ContentValues();
        values = getPluginsValues(plugins);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugins.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugins plugins) { 
        ContentValues values = new ContentValues();
        values = getPluginsValues(plugins);
        database_open();
        String[] where = new String[] { String.valueOf(plugins.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugins.TABLE_NAME , values, DbSchema.Table_Plugins.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugins plugins) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugins.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugins.TABLE_NAME , DbSchema.Table_Plugins.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugins.TABLE_NAME , DbSchema.Table_Plugins.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugins.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPluginsValues(Plugins plugins) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugins.COL__ID, plugins.get_id());
        values.put(DbSchema.Table_Plugins.COL_PLUGIN_ID, plugins.getplugin_id());

        return values;
    }

    protected static Plugins cursorToPlugins(Cursor cursor)  {
        Plugins plugins = new Plugins();

        plugins.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugins.COL__ID)));
        plugins.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugins.COL_PLUGIN_ID)));

        return plugins;
    }

    

}

