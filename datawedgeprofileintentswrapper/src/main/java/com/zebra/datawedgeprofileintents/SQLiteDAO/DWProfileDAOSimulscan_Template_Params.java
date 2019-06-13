package com.zebra.datawedgeprofileintents.sqlitedao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOSimulscan_Template_Params extends DbManager {
    private static final String TAG = "DWProfileDAOSimulscan_Template_Params";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Simulscan_Template_Params.allColumns;


    protected DWProfileDAOSimulscan_Template_Params() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Simulscan_Template_Params loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Simulscan_Template_Params.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Simulscan_Template_Params simulscan_template_params = new Simulscan_Template_Params();
        simulscan_template_params = cursorToSimulscan_Template_Params(cursor);

        cursor.close();
        database_close();

        return simulscan_template_params;
    }

    public static ArrayList<Simulscan_Template_Params> loadAllRecords() {
        ArrayList<Simulscan_Template_Params> simulscan_template_paramsList = new ArrayList<Simulscan_Template_Params>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Simulscan_Template_Params.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Simulscan_Template_Params simulscan_template_params = cursorToSimulscan_Template_Params(cursor);
            simulscan_template_paramsList.add(simulscan_template_params);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return simulscan_template_paramsList;
    }

    // Please always use the typed column names (Table_Simulscan_Template_Params) when passing arguments.
    // Example: Table_Simulscan_Template_Params.Column_Name
    public static ArrayList<Simulscan_Template_Params> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Simulscan_Template_Params> simulscan_template_paramsList = new ArrayList<Simulscan_Template_Params>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Simulscan_Template_Params.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Simulscan_Template_Params simulscan_template_params = cursorToSimulscan_Template_Params(cursor);
            simulscan_template_paramsList.add(simulscan_template_params);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return simulscan_template_paramsList;
    }

    public static long insertRecord(Simulscan_Template_Params simulscan_template_params) {
        ContentValues values = new ContentValues();
        values = getSimulscan_Template_ParamsValues(simulscan_template_params);
        database_open();
        long insertId = database.insert(DbSchema.Table_Simulscan_Template_Params.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Simulscan_Template_Params simulscan_template_params) { 
        ContentValues values = new ContentValues();
        values = getSimulscan_Template_ParamsValues(simulscan_template_params);
        database_open();
        String[] where = new String[] { String.valueOf(simulscan_template_params.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Simulscan_Template_Params.TABLE_NAME , values, DbSchema.Table_Simulscan_Template_Params.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Simulscan_Template_Params simulscan_template_params) { 
        database_open();
        String[] where = new String[] { String.valueOf(simulscan_template_params.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Simulscan_Template_Params.TABLE_NAME , DbSchema.Table_Simulscan_Template_Params.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Simulscan_Template_Params.TABLE_NAME , DbSchema.Table_Simulscan_Template_Params.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Simulscan_Template_Params.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getSimulscan_Template_ParamsValues(Simulscan_Template_Params simulscan_template_params) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Simulscan_Template_Params.COL__ID, simulscan_template_params.get_id());
        values.put(DbSchema.Table_Simulscan_Template_Params.COL_PROFILE_ID, simulscan_template_params.getprofile_id());
        values.put(DbSchema.Table_Simulscan_Template_Params.COL_TEMPLATE, simulscan_template_params.gettemplate());
        values.put(DbSchema.Table_Simulscan_Template_Params.COL_PARAM_ID, simulscan_template_params.getparam_id());
        values.put(DbSchema.Table_Simulscan_Template_Params.COL_PARAM_VALUE, simulscan_template_params.getparam_value());

        return values;
    }

    protected static Simulscan_Template_Params cursorToSimulscan_Template_Params(Cursor cursor)  {
        Simulscan_Template_Params simulscan_template_params = new Simulscan_Template_Params();

        simulscan_template_params.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Simulscan_Template_Params.COL__ID)));
        simulscan_template_params.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Simulscan_Template_Params.COL_PROFILE_ID)));
        simulscan_template_params.settemplate(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Simulscan_Template_Params.COL_TEMPLATE)));
        simulscan_template_params.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Simulscan_Template_Params.COL_PARAM_ID)));
        simulscan_template_params.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Simulscan_Template_Params.COL_PARAM_VALUE)));

        return simulscan_template_params;
    }

    

}

