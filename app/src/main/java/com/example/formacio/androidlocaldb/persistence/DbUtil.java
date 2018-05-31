package com.example.formacio.androidlocaldb.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

public class DbUtil {



    static DbHelper mHelper;
    static SQLiteDatabase mDb;
    static Cursor mCursor;
    static SimpleCursorAdapter mAdapter;
    static ContentValues cv;






    public static SQLiteDatabase getDbConnection(Context context){
        mHelper = new DbHelper(context);
        mDb = mHelper.getWritableDatabase();
        return mDb;
    }



    public static Cursor getCursor(Context context, SQLiteDatabase sqLiteDatabase){

        String[] columns = new String[]{
                "_id", DbHelper.COL_NAME,   DbHelper.COL_DATE,DbHelper.COL_AGE,
                DbHelper.COL_CHIP,          DbHelper.COL_TYPE,DbHelper.COL_PHOTO};

        mCursor = sqLiteDatabase
                .query(DbHelper.TABLE_NAME, columns,
                        null, null, null, null, null, null);


        return mCursor;
    }


    public static ContentValues getContentValues(){
        cv = new ContentValues(2);
        return cv;
    }






}
