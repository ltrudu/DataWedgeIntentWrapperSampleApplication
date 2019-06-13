package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOApplist extends DbManager {
    private static final String TAG = "DWProfileDAOApplist";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Applist.allColumns;


    protected DWProfileDAOApplist() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Applist loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Applist.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Applist applist = new Applist();
        applist = cursorToApplist(cursor);

        cursor.close();
        database_close();

        return applist;
    }

    public static ArrayList<Applist> loadAllRecords() {
        ArrayList<Applist> applistList = new ArrayList<Applist>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Applist.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Applist applist = cursorToApplist(cursor);
            applistList.add(applist);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return applistList;
    }

    // Please always use the typed column names (Table_Applist) when passing arguments.
    // Example: Table_Applist.Column_Name
    public static ArrayList<Applist> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Applist> applistList = new ArrayList<Applist>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Applist.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Applist applist = cursorToApplist(cursor);
            applistList.add(applist);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return applistList;
    }

    public static long insertRecord(Applist applist) {
        ContentValues values = new ContentValues();
        values = getApplistValues(applist);
        database_open();
        long insertId = database.insert(DbSchema.Table_Applist.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Applist applist) { 
        ContentValues values = new ContentValues();
        values = getApplistValues(applist);
        database_open();
        String[] where = new String[] { String.valueOf(applist.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Applist.TABLE_NAME , values, DbSchema.Table_Applist.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Applist applist) { 
        database_open();
        String[] where = new String[] { String.valueOf(applist.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Applist.TABLE_NAME , DbSchema.Table_Applist.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Applist.TABLE_NAME , DbSchema.Table_Applist.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Applist.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getApplistValues(Applist applist) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Applist.COL__ID, applist.get_id());
        values.put(DbSchema.Table_Applist.COL_PROFILE_ID, applist.getprofile_id());
        values.put(DbSchema.Table_Applist.COL_ACTIVITY, applist.getactivity());
        values.put(DbSchema.Table_Applist.COL_PACKAGENAME, applist.getpackagename());

        return values;
    }

    protected static Applist cursorToApplist(Cursor cursor)  {
        Applist applist = new Applist();

        applist.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Applist.COL__ID)));
        applist.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Applist.COL_PROFILE_ID)));
        applist.setactivity(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Applist.COL_ACTIVITY)));
        applist.setpackagename(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Applist.COL_PACKAGENAME)));

        return applist;
    }

    

}

