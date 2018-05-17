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
    EditText date;
    EditText photo;

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
        age = (EditText) findViewById(R.id.name);
        chip = (EditText) findViewById(R.id.name);
        type = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.name);
        photo = (EditText) findViewById(R.id.name);
        sendData=(Button) findViewById(R.id.sendData);

        mHelper = new DbHelper(this);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InsertAnimal.this,DatabaseActivity.class);

                String sName=name.getText().toString();
                String sAge=name.getText().toString();
                String sChip=name.getText().toString();
                String sType=name.getText().toString();
                String sDate=name.getText().toString();
                String sPhoto=name.getText().toString();



                //WRITE ON DB

                //Open connections to the database
                mDb = mHelper.getWritableDatabase();
                String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE};
                mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
                //Refresh the list
                String[] headers = new String[]{DbHelper.COL_NAME, DbHelper.COL_DATE};
                mAdapter = new SimpleCursorAdapter(InsertAnimal.this, android.R.layout.two_line_list_item,
                        mCursor, headers, new int[]{android.R.id.text1, android.R.id.text2});


                //Add a new value to the database
                ContentValues cv = new ContentValues(2);

                cv.put(DbHelper.COL_NAME, sName);

                //Create a formatter for SQL date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cv.put(DbHelper.COL_DATE, dateFormat.format(new Date())); //InsertAnimal 'now' as the date
                mDb.insert(DbHelper.TABLE_NAME, null, cv);



                startActivity(i);
            }
        });


    }



}
