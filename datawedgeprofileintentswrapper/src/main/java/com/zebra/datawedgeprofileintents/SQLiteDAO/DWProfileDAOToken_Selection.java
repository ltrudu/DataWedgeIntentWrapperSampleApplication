package com.zebra.datawedgeprofileintents.SQLiteDAO;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DWProfileDAOToken_Selection extends DbManager {
    private static final String TAG = "DWProfileDAOToken_Selection";

    protected static SQLiteDatabase database;
    protected static DbManager mDbManager;
    protected static  String[] allColumns = DbSchema.Table_Token_Selection.allColumns;


    protected DWProfileDAOToken_Selection() {
    }

    protected static void database_open() throws SQLException {
        mDbManager = DbManager.getsInstance();
        database = mDbManager.getDatabase();
    }

    protected static void database_close() {
        mDbManager = DbManager.getsInstance();
        mDbManager.close();
    }

    public static Token_Selection loadRecordById(int m_id)  { 
        database_open();
        Cursor cursor = database.query(DbSchema.Table_Token_Selection.TABLE_NAME,allColumns,  "_id = ?" , new String[] { String.valueOf(m_id) } , null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Token_Selection token_selection = new Token_Selection();
        token_selection = cursorToToken_Selection(cursor);

        cursor.close();
        database_close();

        return token_selection;
    }

    public static ArrayList<Token_Selection> loadAllRecords() {
        ArrayList<Token_Selection> token_selectionList = new ArrayList<Token_Selection>();
        database_open();

        Cursor cursor = database.query(
                DbSchema.Table_Token_Selection.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Token_Selection token_selection = cursorToToken_Selection(cursor);
            token_selectionList.add(token_selection);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return token_selectionList;
    }

    // Please always use the typed column names (Table_Token_Selection) when passing arguments.
    // Example: Table_Token_Selection.Column_Name
    public static ArrayList<Token_Selection> loadAllRecords(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Token_Selection> token_selectionList = new ArrayList<Token_Selection>();
        database_open();

        if(TextUtils.isEmpty(selection)){
            selection = null;
            selectionArgs = null;
        }

        Cursor cursor = database.query(
                DbSchema.Table_Token_Selection.TABLE_NAME,
                allColumns,
                selection==null ? null : selection,
                selectionArgs==null ? null : selectionArgs,
                groupBy==null ? null : groupBy,
                having==null ? null : having,
                orderBy==null ? null : orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Token_Selection token_selection = cursorToToken_Selection(cursor);
            token_selectionList.add(token_selection);
            cursor.moveToNext();
        }
        cursor.close();
        database_close();
        return token_selectionList;
    }

    public static long insertRecord(Token_Selection token_selection) {
        ContentValues values = new ContentValues();
        values = getToken_SelectionValues(token_selection);
        database_open();
        long insertId = database.insert(DbSchema.Table_Token_Selection.TABLE_NAME , null, values);
        database_close();
        return insertId;
    }

    public static int updateRecord(Token_Selection token_selection) { 
        ContentValues values = new ContentValues();
        values = getToken_SelectionValues(token_selection);
        database_open();
        String[] where = new String[] { String.valueOf(token_selection.get_id()) }; 
        int updatedId = database.update(DbSchema.Table_Token_Selection.TABLE_NAME , values, DbSchema.Table_Token_Selection.COL__ID + " = ? ",where );
        database_close();
        return updatedId;
    }

    public static int deleteRecord(Token_Selection token_selection) { 
        database_open();
        String[] where = new String[] { String.valueOf(token_selection.get_id()) }; 
        int deletedCount = database.delete(DbSchema.Table_Token_Selection.TABLE_NAME , DbSchema.Table_Token_Selection.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteRecord(String id) {
        database_open();
        String[] where = new String[] { id }; 
        int deletedCount = database.delete(DbSchema.Table_Token_Selection.TABLE_NAME , DbSchema.Table_Token_Selection.COL__ID + " = ? ",where );
        database_close();
        return deletedCount;
    }

    public static int deleteAllRecords() {
        database_open();
        int deletedCount = database.delete(DbSchema.Table_Token_Selection.TABLE_NAME , null, null );
        database_close();
        return deletedCount;
    }

    protected static ContentValues getToken_SelectionValues(Token_Selection token_selection) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.Table_Token_Selection.COL__ID, token_selection.get_id());
        values.put(DbSchema.Table_Token_Selection.COL_PROFILE_ID, token_selection.getprofile_id());
        values.put(DbSchema.Table_Token_Selection.COL_PLUGIN_ID, token_selection.getplugin_id());
        values.put(DbSchema.Table_Token_Selection.COL_NAME, token_selection.getname());
        values.put(DbSchema.Table_Token_Selection.COL_PRIORITY, token_selection.getpriority());
        values.put(DbSchema.Table_Token_Selection.COL_ENABLED, token_selection.getenabled());
        values.put(DbSchema.Table_Token_Selection.COL_ALLDEVICES, token_selection.getalldevices());

        return values;
    }

    protected static Token_Selection cursorToToken_Selection(Cursor cursor)  {
        Token_Selection token_selection = new Token_Selection();

        token_selection.set_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Token_Selection.COL__ID)));
        token_selection.setprofile_id(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Token_Selection.COL_PROFILE_ID)));
        token_selection.setplugin_id(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Token_Selection.COL_PLUGIN_ID)));
        token_selection.setname(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Token_Selection.COL_NAME)));
        token_selection.setpriority(cursor.getInt(cursor.getColumnIndex(DbSchema.Table_Token_Selection.COL_PRIORITY)));
        token_selection.setenabled(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Token_Selection.COL_ENABLED)));
        token_selection.setalldevices(cursor.getString(cursor.getColumnIndex(DbSchema.Table_Token_Selection.COL_ALLDEVICES)));

        return token_selection;
    }

    

}

