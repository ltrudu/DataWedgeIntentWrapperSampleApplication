package com.zebra.datawedgeprofileintents.SQLiteDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager {
    private static final String TAG = "DBMANAGER";

    private static DbManager sInstance;

    private static long errorId = 0;

    private Context mCtx;
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;


    public static DbManager setConfig(Context context) {
        if (sInstance==null){
            sInstance = new DbManager(context);}
        return sInstance;
    }

    public static DbManager getsInstance() {
        if (sInstance==null) throw new NullPointerException("DbManager is null, Please set DbManger.setConfig(this) in your activity before using the DAO objects.");
        return sInstance;
    }


    private DbManager(Context ctx) {
        this.mCtx = ctx;
    }

    protected DbManager() {}

    public DbManager open() {
        if(mDbHelper == null)
            mDbHelper = new DbHelper(this);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public DbManager open(String filename, int version) {
        if(mDbHelper == null)
            mDbHelper = new DbHelper(this, filename, version);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public SQLiteDatabase getDatabase() {
        if(mDbHelper == null)
            mDbHelper = new DbHelper(this);
        mDb = mDbHelper.getWritableDatabase();
        return mDb;
    }

    private static class DbHelper extends SQLiteOpenHelper {
        private static DbHelper sInstance;

        private static final String LOG_TAG = "DbHelper";
        private DbManager mDbManager;

        public static DbHelper getInstance(DbManager manager) {
            if (sInstance == null) {
                sInstance = new DbHelper(manager);
            }
            return sInstance;
        }

        public static DbHelper initWithDatabase(DbManager manager, String fileName)
        {
            if (sInstance == null) {
                sInstance = new DbHelper(manager);
            }
            return sInstance;
        }


        private DbHelper(DbManager dbmanger) {
            super(dbmanger.mCtx, DbSchema.DATABASE_NAME, null, DbSchema.DATABASE_VERSION);
            mDbManager = dbmanger;
        }

        private DbHelper(DbManager dbmanger, String fileName, int version) {
            super(dbmanger.mCtx, fileName, null, version);
            DbSchema.DATABASE_NAME = fileName;
            DbSchema.DATABASE_VERSION = 0;
            mDbManager = dbmanger;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DbSchema.Table_Profiles.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Applist.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Settings.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugins.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Info.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Scanner_Params.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Input_Scanner.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Output_Keystroke.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Output_Intent.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Process_Bdf.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Input_Msr.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Process_Adf.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Output_Ip.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Input_Ssb.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Process_Token.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Token_Selection.CREATE_TABLE);
            db.execSQL(DbSchema.Table_Simulscan_Template_Params.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DbSchema.Table_Profiles.DROP_TABLE);
            db.execSQL(DbSchema.Table_Applist.DROP_TABLE);
            db.execSQL(DbSchema.Table_Settings.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugins.DROP_TABLE);
            db.execSQL(DbSchema.Table_Info.DROP_TABLE);
            db.execSQL(DbSchema.Table_Scanner_Params.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Input_Scanner.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Output_Keystroke.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Output_Intent.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Process_Bdf.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Input_Msr.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Process_Adf.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Output_Ip.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Input_Ssb.DROP_TABLE);
            db.execSQL(DbSchema.Table_Plugin_Process_Token.DROP_TABLE);
            db.execSQL(DbSchema.Table_Token_Selection.DROP_TABLE);
            db.execSQL(DbSchema.Table_Simulscan_Template_Params.DROP_TABLE);
            this.onCreate(db);
        }

    }
}
