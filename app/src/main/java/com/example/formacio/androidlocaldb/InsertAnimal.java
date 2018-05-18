package com.example.formacio.androidlocaldb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertAnimal extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText chip;
    EditText type;


    Button sendData;

    DbHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        chip = (EditText) findViewById(R.id.chip);
        type = (EditText) findViewById(R.id.type);

        sendData=(Button) findViewById(R.id.sendData);

        mHelper = new DbHelper(this);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InsertAnimal.this,DatabaseActivity.class);

                String sName=name.getText().toString();
                String sAge=age.getText().toString();
                String sChip=chip.getText().toString();
                String sType=type.getText().toString();
        //        String sDate=date.getText().toString();
        //        String sPhoto=photo.getText().toString();



                //WRITE ON DB

                //Open connections to the database
                mDb = mHelper.getWritableDatabase();
                String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE,DbHelper.COL_AGE, DbHelper.COL_CHIP, DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
                    //    ,DbHelper.COL_CHIP,DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
                mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
                //Refresh the list
                String[] headers = new String[]{DbHelper.COL_NAME, DbHelper.COL_DATE,DbHelper.COL_AGE, DbHelper.COL_CHIP, DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
                     //   ,DbHelper.COL_CHIP,DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
                mAdapter = new SimpleCursorAdapter(InsertAnimal.this, android.R.layout.two_line_list_item,
                        mCursor, headers, new int[]{android.R.id.text1, android.R.id.text2});


                //Add a new value to the database
                ContentValues cv = new ContentValues(2);

                //todo add value to DB

                cv.put(DbHelper.COL_NAME, sName);

                //Create a formatter for SQL date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cv.put(DbHelper.COL_DATE, dateFormat.format(new Date())); //InsertAnimal 'now' as the date

                cv.put(DbHelper.COL_AGE, sAge);
                cv.put(DbHelper.COL_CHIP, sChip);
                cv.put(DbHelper.COL_TYPE, sType);
         //       cv.put(DbHelper.COL_PHOTO, sPhoto);

                mDb.insert(DbHelper.TABLE_NAME, null, cv);



                startActivity(i);
            }
        });


    }



}
