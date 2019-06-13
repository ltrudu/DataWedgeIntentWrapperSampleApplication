package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOPlugin_Output_Keystroke extends DbManager {
    private static final String TAG = "DWProfileDAOPlugin_Output_Keystroke";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Plugin_Output_Keystroke.allColumns;


    protected DWProfileDAOPlugin_Output_Keystroke() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Plugin_Output_Keystroke loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Plugin_Output_Keystroke plugin_output_keystroke = new Plugin_Output_Keystroke();
        plugin_output_keystroke = cursorToPlugin_Output_Keystroke(cursor);

        cursor.close();
        database_close();

        return plugin_output_keystroke;
    }

    public static ArrayList<Plugin_Output_Keystroke> loadAllRecords() {
        ArrayList<Plugin_Output_Keystroke> plugin_output_keystrokeList = new ArrayList<Plugin_Output_Keystroke>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Output_Keystroke plugin_output_keystroke = cursorToPlugin_Output_Keystroke(cursor);
            plugin_output_keystrokeList.add(plugin_output_keystroke);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_output_keystrokeList;
    }

    // Please always use the typed column names (Table_Plugin_Output_Keystroke) when passing arguments.
    // Example: Table_Plugin_Output_Keystroke.Column_Name
    public static ArrayList<Plugin_Output_Keystroke> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Plugin_Output_Keystroke> plugin_output_keystrokeList = new ArrayList<Plugin_Output_Keystroke>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Plugin_Output_Keystroke plugin_output_keystroke = cursorToPlugin_Output_Keystroke(cursor);
            plugin_output_keystrokeList.add(plugin_output_keystroke);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return plugin_output_keystrokeList;
    }

    public static long insertRecord(Plugin_Output_Keystroke plugin_output_keystroke) {
        ContentValues values = new ContentValues();
        values = getPlugin_Output_KeystrokeValues(plugin_output_keystroke);
        database_open();
        long insertId = database.insert(DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Plugin_Output_Keystroke plugin_output_keystroke) { 
        ContentValues values = new ContentValues();
        values = getPlugin_Output_KeystrokeValues(plugin_output_keystroke);
        database_open();
        String[] where = new String[] { String.valueOf(plugin_output_keystroke.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME , values, DbSchema.Table_Plugin_Output_Keystroke.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Plugin_Output_Keystroke plugin_output_keystroke) { 
        database_open();
        String[] where = new String[] { String.valueOf(plugin_output_keystroke.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME , DbSchema.Table_Plugin_Output_Keystroke.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME , DbSchema.Table_Plugin_Output_Keystroke.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Plugin_Output_Keystroke.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getPlugin_Output_KeystrokeValues(Plugin_Output_Keystroke plugin_output_keystroke) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Plugin_Output_Keystroke.COL__ID, plugin_output_keystroke.get_id());
        values.put(DbSchema.Table_Plugin_Output_Keystroke.COL_PROFILE_ID, plugin_output_keystroke.getprofile_id());
        values.put(DbSchema.Table_Plugin_Output_Keystroke.COL_PLUGIN_ID, plugin_output_keystroke.getplugin_id());
        values.put(DbSchema.Table_Plugin_Output_Keystroke.COL_PARAM_ID, plugin_output_keystroke.getparam_id());
        values.put(DbSchema.Table_Plugin_Output_Keystroke.COL_PARAM_VALUE, plugin_output_keystroke.getparam_value());

        return values;
    }

    protected static Plugin_Output_Keystroke cursorToPlugin_Output_Keystroke(Cursor cursor)  {
        Plugin_Output_Keystroke plugin_output_keystroke = new Plugin_Output_Keystroke();

        plugin_output_keystroke.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Keystroke.COL__ID)));
        plugin_output_keystroke.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Keystroke.COL_PROFILE_ID)));
        plugin_output_keystroke.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Keystroke.COL_PLUGIN_ID)));
        plugin_output_keystroke.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Keystroke.COL_PARAM_ID)));
        plugin_output_keystroke.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Plugin_Output_Keystroke.COL_PARAM_VALUE)));

        return plugin_output_keystroke;
    }

    

}

