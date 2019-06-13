package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOProfiles extends DbManager {
    private static final String TAG = "DWProfileDAOProfiles";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Profiles.allColumns;


    protected DWProfileDAOProfiles() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Profiles loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Profiles.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Profiles profiles = new Profiles();
        profiles = cursorToProfiles(cursor);

        cursor.close();
        database_close();

        return profiles;
    }

    public static ArrayList<Profiles> loadAllRecords() {
        ArrayList<Profiles> profilesList = new ArrayList<Profiles>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Profiles.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Profiles profiles = cursorToProfiles(cursor);
            profilesList.add(profiles);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return profilesList;
    }

    // Please always use the typed column names (Table_Profiles) when passing arguments.
    // Example: Table_Profiles.Column_Name
    public static ArrayList<Profiles> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Profiles> profilesList = new ArrayList<Profiles>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Profiles.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Profiles profiles = cursorToProfiles(cursor);
            profilesList.add(profiles);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return profilesList;
    }

    public static long insertRecord(Profiles profiles) {
        ContentValues values = new ContentValues();
        values = getProfilesValues(profiles);
        database_open();
        long insertId = database.insert(DbSchema.Table_Profiles.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Profiles profiles) { 
        ContentValues values = new ContentValues();
        values = getProfilesValues(profiles);
        database_open();
        String[] where = new String[] { String.valueOf(profiles.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Profiles.TABLE_NAME , values, DbSchema.Table_Profiles.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Profiles profiles) { 
        database_open();
        String[] where = new String[] { String.valueOf(profiles.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Profiles.TABLE_NAME , DbSchema.Table_Profiles.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Profiles.TABLE_NAME , DbSchema.Table_Profiles.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Profiles.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getProfilesValues(Profiles profiles) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Profiles.COL__ID, profiles.get_id());
        values.put(DbSchema.Table_Profiles.COL_NAME, profiles.getname());
        values.put(DbSchema.Table_Profiles.COL_DW_ENABLED, profiles.getdw_enabled());
        values.put(DbSchema.Table_Profiles.COL_ENABLED, profiles.getenabled());
        values.put(DbSchema.Table_Profiles.COL_DELETABLE, profiles.getdeletable());
        values.put(DbSchema.Table_Profiles.COL_EDITABLE, profiles.geteditable());

        return values;
    }

    protected static Profiles cursorToProfiles(Cursor cursor)  {
        Profiles profiles = new Profiles();

        profiles.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Profiles.COL__ID)));
        profiles.setname(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Profiles.COL_NAME)));
        profiles.setdw_enabled(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Profiles.COL_DW_ENABLED)));
        profiles.setenabled(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Profiles.COL_ENABLED)));
        profiles.setdeletable(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Profiles.COL_DELETABLE)));
        profiles.seteditable(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Profiles.COL_EDITABLE)));

        return profiles;
    }

    

}

