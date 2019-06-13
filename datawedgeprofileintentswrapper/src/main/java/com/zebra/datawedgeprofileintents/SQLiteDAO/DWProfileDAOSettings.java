package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOSettings extends DbManager {
    private static final String TAG = "DWProfileDAOSettings";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Settings.allColumns;


    protected DWProfileDAOSettings() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Settings loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Settings.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Settings settings = new Settings();
        settings = cursorToSettings(cursor);

        cursor.close();
        database_close();

        return settings;
    }

    public static ArrayList<Settings> loadAllRecords() {
        ArrayList<Settings> settingsList = new ArrayList<Settings>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Settings.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Settings settings = cursorToSettings(cursor);
            settingsList.add(settings);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return settingsList;
    }

    // Please always use the typed column names (Table_Settings) when passing arguments.
    // Example: Table_Settings.Column_Name
    public static ArrayList<Settings> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Settings> settingsList = new ArrayList<Settings>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Settings.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Settings settings = cursorToSettings(cursor);
            settingsList.add(settings);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return settingsList;
    }

    public static long insertRecord(Settings settings) {
        ContentValues values = new ContentValues();
        values = getSettingsValues(settings);
        database_open();
        long insertId = database.insert(DbSchema.Table_Settings.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Settings settings) { 
        ContentValues values = new ContentValues();
        values = getSettingsValues(settings);
        database_open();
        String[] where = new String[] { String.valueOf(settings.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Settings.TABLE_NAME , values, DbSchema.Table_Settings.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Settings settings) { 
        database_open();
        String[] where = new String[] { String.valueOf(settings.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Settings.TABLE_NAME , DbSchema.Table_Settings.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Settings.TABLE_NAME , DbSchema.Table_Settings.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Settings.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getSettingsValues(Settings settings) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Settings.COL__ID, settings.get_id());
        values.put(DbSchema.Table_Settings.COL_PARAM_ID, settings.getparam_id());
        values.put(DbSchema.Table_Settings.COL_PARAM_VALUE, settings.getparam_value());

        return values;
    }

    protected static Settings cursorToSettings(Cursor cursor)  {
        Settings settings = new Settings();

        settings.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Settings.COL__ID)));
        settings.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Settings.COL_PARAM_ID)));
        settings.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Settings.COL_PARAM_VALUE)));

        return settings;
    }

    

}

