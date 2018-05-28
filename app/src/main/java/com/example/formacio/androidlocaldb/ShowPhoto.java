package com.example.formacio.androidlocaldb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ShowPhoto extends AppCompatActivity {

    ImageView fullPhoto;
    private DbHelper mHelper;
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        fullPhoto=findViewById(R.id.fullPhoto);

        Intent i = getIntent();
        int pos = i.getIntExtra("position",2);
        Log.e("position",String.valueOf(pos));

        mHelper = new DbHelper(this);
        //Open connections to the database
        mDb = mHelper.getWritableDatabase();
        // TODO add coloumn
        String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE,DbHelper.COL_AGE, DbHelper.COL_CHIP, DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
        mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        mCursor.moveToPosition(pos);
        //Get the id value of this row

        String photo = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_PHOTO));

        bmp=AnimalInfo.decodeFromBase64ToBitmap(photo);

        fullPhoto.setImageBitmap(bmp);


        Log.e("column_photo",photo);


    }
}
