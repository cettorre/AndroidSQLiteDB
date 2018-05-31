package com.example.formacio.androidlocaldb.persistence;

/**
 * Created by formacio on 16/05/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by busko on 14/12/17.
 */

public class DbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "animals";
    public static final String COL_NAME = "name";
    public static final String COL_DATE = "date";
    public static final String COL_AGE = "age";
    public static final String COL_CHIP = "chip";
    public static final String COL_TYPE = "type";
    public static final String COL_PHOTO = "photo";
    private static final String STRING_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME + " TEXT, " + COL_DATE + " DATE," + COL_AGE + " INTEGER,"+
                     COL_CHIP + " INTEGER, " + COL_TYPE + " TEXT,"+ COL_PHOTO + " TEXT" +
                    ");";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the database table
        db.execSQL(STRING_CREATE);

        //You may also load initial values into the database here
        ContentValues cv = new ContentValues(10);
        cv.put(COL_NAME, "scooby dog");
        //Create a formatter for sql date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // cv.put(COL_DATE, dateFormat.format(new Date())); //InsertAnimal 'now' as the date
       // cv.put(COL_AGE, 8);
       // cv.put(COL_CHIP, "yes");
       // cv.put(COL_TYPE, "dog");


     //   db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //For now, clear the database and re-create
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}