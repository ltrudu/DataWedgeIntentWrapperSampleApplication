package com.zebra.datawedgeprofileintents.sqlitedao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOScanner_Params extends DbManager {
    private static final String TAG = "DWProfileDAOScanner_Params";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Scanner_Params.allColumns;


    protected DWProfileDAOScanner_Params() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Scanner_Params loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Scanner_Params.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Scanner_Params scanner_params = new Scanner_Params();
        scanner_params = cursorToScanner_Params(cursor);

        cursor.close();
        database_close();

        return scanner_params;
    }

    public static ArrayList<Scanner_Params> loadAllRecords() {
        ArrayList<Scanner_Params> scanner_paramsList = new ArrayList<Scanner_Params>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Scanner_Params.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Scanner_Params scanner_params = cursorToScanner_Params(cursor);
            scanner_paramsList.add(scanner_params);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return scanner_paramsList;
    }

    // Please always use the typed column names (Table_Scanner_Params) when passing arguments.
    // Example: Table_Scanner_Params.Column_Name
    public static ArrayList<Scanner_Params> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Scanner_Params> scanner_paramsList = new ArrayList<Scanner_Params>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Scanner_Params.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Scanner_Params scanner_params = cursorToScanner_Params(cursor);
            scanner_paramsList.add(scanner_params);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return scanner_paramsList;
    }

    public static long insertRecord(Scanner_Params scanner_params) {
        ContentValues values = new ContentValues();
        values = getScanner_ParamsValues(scanner_params);
        database_open();
        long insertId = database.insert(DbSchema.Table_Scanner_Params.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Scanner_Params scanner_params) { 
        ContentValues values = new ContentValues();
        values = getScanner_ParamsValues(scanner_params);
        database_open();
        String[] where = new String[] { String.valueOf(scanner_params.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Scanner_Params.TABLE_NAME , values, DbSchema.Table_Scanner_Params.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Scanner_Params scanner_params) { 
        database_open();
        String[] where = new String[] { String.valueOf(scanner_params.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Scanner_Params.TABLE_NAME , DbSchema.Table_Scanner_Params.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Scanner_Params.TABLE_NAME , DbSchema.Table_Scanner_Params.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Scanner_Params.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getScanner_ParamsValues(Scanner_Params scanner_params) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Scanner_Params.COL__ID, scanner_params.get_id());
        values.put(DbSchema.Table_Scanner_Params.COL_SCANNER_TYPE, scanner_params.getscanner_type());
        values.put(DbSchema.Table_Scanner_Params.COL_PARAM_ID, scanner_params.getparam_id());
        values.put(DbSchema.Table_Scanner_Params.COL_PARAM_CATEGORY, scanner_params.getparam_category());
        values.put(DbSchema.Table_Scanner_Params.COL_DISPLAY_NAME, scanner_params.getdisplay_name());
        values.put(DbSchema.Table_Scanner_Params.COL_DEFAULT_VALUE, scanner_params.getdefault_value());
        values.put(DbSchema.Table_Scanner_Params.COL_VALUE_NAME, scanner_params.getvalue_name());
        values.put(DbSchema.Table_Scanner_Params.COL_VALUE_TYPE, scanner_params.getvalue_type());
        values.put(DbSchema.Table_Scanner_Params.COL_VALUE_MIN, scanner_params.getvalue_min());
        values.put(DbSchema.Table_Scanner_Params.COL_VALUE_MAX, scanner_params.getvalue_max());
        values.put(DbSchema.Table_Scanner_Params.COL_VALUES_DISCRETE_COUNT, scanner_params.getvalues_discrete_count());
        values.put(DbSchema.Table_Scanner_Params.COL_VALUES_DISCRETE, scanner_params.getvalues_discrete());
        values.put(DbSchema.Table_Scanner_Params.COL_VALUES_DISCRETE_NAMES, scanner_params.getvalues_discrete_names());

        return values;
    }

    protected static Scanner_Params cursorToScanner_Params(Cursor cursor)  {
        Scanner_Params scanner_params = new Scanner_Params();

        scanner_params.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL__ID)));
        scanner_params.setscanner_type(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_SCANNER_TYPE)));
        scanner_params.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_PARAM_ID)));
        scanner_params.setparam_category(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_PARAM_CATEGORY)));
        scanner_params.setdisplay_name(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_DISPLAY_NAME)));
        scanner_params.setdefault_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_DEFAULT_VALUE)));
        scanner_params.setvalue_name(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_VALUE_NAME)));
        scanner_params.setvalue_type(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_VALUE_TYPE)));
        scanner_params.setvalue_min(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_VALUE_MIN)));
        scanner_params.setvalue_max(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_VALUE_MAX)));
        scanner_params.setvalues_discrete_count(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_VALUES_DISCRETE_COUNT)));
        scanner_params.setvalues_discrete(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_VALUES_DISCRETE)));
        scanner_params.setvalues_discrete_names(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Scanner_Params.COL_VALUES_DISCRETE_NAMES)));

        return scanner_params;
    }

    

}

