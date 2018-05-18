package com.example.formacio.androidlocaldb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnimalInfo extends AppCompatActivity {

    Button delBtn;
    DbHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    TextView iName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);

        iName= findViewById(R.id.iName);

        delBtn=(Button) findViewById(R.id.iDelete);

        mHelper = new DbHelper(this);


       //TODO read selected record from DB**************

        Intent i = getIntent();
        int pos = i.getIntExtra("position",2);
        Log.e("position",String.valueOf(pos));

        //Open connections to the database
        mDb = mHelper.getWritableDatabase();
        String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE};
        mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        mCursor.moveToPosition(pos);
        //Get the id value of this row


        String name = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_NAME));
        Log.e("column",name);
        

        //Refresh the list
        mCursor.requery();



        iName.setText(name);


      //end   *******************************************




        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int pos = i.getIntExtra("position",2);
                Log.e("position",String.valueOf(pos));

                //Open connections to the database
                mDb = mHelper.getWritableDatabase();
                String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE};
                mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);


                mCursor.moveToPosition(pos);
                //Get the id value of this row
                String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
                Log.e("rowID",rowId);




                mDb.delete(DbHelper.TABLE_NAME, "_id = ?", new String[]{rowId});
                //Refresh the list
                mCursor.requery();
                //mAdapter.notifyDataSetChanged();


                Intent i2 = new Intent(AnimalInfo.this, DatabaseActivity.class);
                startActivity(i2);



            }
        });

    }
}
