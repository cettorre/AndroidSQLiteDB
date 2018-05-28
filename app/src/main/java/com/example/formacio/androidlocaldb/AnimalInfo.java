package com.example.formacio.androidlocaldb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AnimalInfo extends AppCompatActivity {

    Button delBtn;
    DbHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    TextView iName;
    TextView iDate;
    TextView iAge;
    TextView iChip;
    TextView iType;
    //TextView iPhoto;
    ImageButton aPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);

        iName= findViewById(R.id.iName);
        iDate= findViewById(R.id.iDate);
        iAge= findViewById(R.id.iAge);
        iChip= findViewById(R.id.iChip);
        iType= findViewById(R.id.iType);
    //    iPhoto= findViewById(R.id.iPhoto);
        aPhoto=findViewById(R.id.aPhoto);
        aPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimalInfo.this,ShowPhoto.class);
                startActivity(i);


            }
        });

        delBtn=(Button) findViewById(R.id.iDelete);




       //TODO read selected record from DB**************

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


        String name = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_NAME));
        Log.e("column",name);

        String date = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_DATE));
        Log.e("column",date);

       // TODO add coloumn

        int age = mCursor.getInt(mCursor.getColumnIndexOrThrow(DbHelper.COL_AGE));
        Log.e("column2",String.valueOf(age));

        String chip = (mCursor.getInt(mCursor.getColumnIndexOrThrow(DbHelper.COL_CHIP))==1)?"yes":"no";
        Log.e("chip",chip);

        String type = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_TYPE));
        Log.e("column",type);

        String photo = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_PHOTO));

        Log.e("column_photo",photo);

        aPhoto.setImageBitmap(decodeFromBase64ToBitmap(photo));




        //Refresh the list
        mCursor.requery();


        //todo set string
        iName.setText("name: "+name);
        iDate.setText("date: "+date);
        iAge.setText("age: "+String.valueOf(age));
        iChip.setText("chip: "+chip);
        iType.setText("type: "+type);
     //   iPhoto.setText(photo);
        //iPhoto.setText(photo);


      //end   *******************************************




        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int pos = i.getIntExtra("position",2);
                Log.e("position",String.valueOf(pos));

                //Open connections to the database
                mDb = mHelper.getWritableDatabase();
                //todo add column
                String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE, DbHelper.COL_AGE};
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

 static public Bitmap decodeFromBase64ToBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
