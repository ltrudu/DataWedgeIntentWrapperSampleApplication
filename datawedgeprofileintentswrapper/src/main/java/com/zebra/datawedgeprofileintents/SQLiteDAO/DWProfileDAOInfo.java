package com.zebra.datawedgeprofileintents.sqlitedao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOInfo extends DbManager {
    private static final String TAG = "DWProfileDAOInfo";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Info.allColumns;


    protected DWProfileDAOInfo() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Info loadRecordById(int mparam_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Info.TABLE_NAME,allColumns,  "param_id = ?" , new String[] { String.valueOf(mparam_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Info info = new Info();
        info = cursorToInfo(cursor);

        cursor.close();
        database_close();

        return info;
    }

    public static ArrayList<Info> loadAllRecords() {
        ArrayList<Info> infoList = new ArrayList<Info>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Info.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Info info = cursorToInfo(cursor);
            infoList.add(info);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return infoList;
    }

    // Please always use the typed column names (Table_Info) when passing arguments.
    // Example: Table_Info.Column_Name
    public static ArrayList<Info> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Info> infoList = new ArrayList<Info>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Info.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Info info = cursorToInfo(cursor);
            infoList.add(info);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return infoList;
    }

    public static long insertRecord(Info info) {
        ContentValues values = new ContentValues();
        values = getInfoValues(info);
        database_open();
        long insertId = database.insert(DbSchema.Table_Info.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Info info) { 
        ContentValues values = new ContentValues();
        values = getInfoValues(info);
        database_open();
        String[] where = new String[] { String.valueOf(info.getparam_id()) }; 
        int updatedId = database.update(DbSchema.Table_Info.TABLE_NAME , values, DbSchema.Table_Info.COL_PARAM_ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Info info) { 
        database_open();
        String[] where = new String[] { String.valueOf(info.getparam_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Info.TABLE_NAME , DbSchema.Table_Info.COL_PARAM_ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Info.TABLE_NAME , DbSchema.Table_Info.COL_PARAM_ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Info.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getInfoValues(Info info) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Info.COL_PARAM_ID, info.getparam_id());
        values.put(DbSchema.Table_Info.COL_PARAM_VALUE, info.getparam_value());

        return values;
    }

    protected static Info cursorToInfo(Cursor cursor)  {
        Info info = new Info();

        info.setparam_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Info.COL_PARAM_ID)));
        info.setparam_value(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Info.COL_PARAM_VALUE)));

        return info;
    }

    

}

