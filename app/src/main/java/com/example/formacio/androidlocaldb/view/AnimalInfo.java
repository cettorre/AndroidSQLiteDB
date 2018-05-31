package com.example.formacio.androidlocaldb.view;

import android.content.Intent;
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

import com.example.formacio.androidlocaldb.R;
import com.example.formacio.androidlocaldb.persistence.DbHelper;
import com.example.formacio.androidlocaldb.persistence.DbUtil;

import static com.example.formacio.androidlocaldb.view.MainActivity.mCursor;
import static com.example.formacio.androidlocaldb.view.MainActivity.mDb;

public class AnimalInfo extends AppCompatActivity {

    Button delBtn;
    TextView iName;
    TextView iDate;
    TextView iAge;
    TextView iChip;
    TextView iType;
    ImageButton aPhoto;
    static int positionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);

        iName= findViewById(R.id.iName);
        iDate= findViewById(R.id.iDate);
        iAge= findViewById(R.id.iAge);
        iChip= findViewById(R.id.iChip);
        iType= findViewById(R.id.iType);
        aPhoto=findViewById(R.id.aPhoto);
        aPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimalInfo.this,ShowPhoto.class);
                startActivity(i);


            }
        });

        delBtn=(Button) findViewById(R.id.iDelete);




       // read selected record from List DB**************
        Intent i = getIntent();
        int pos = i.getIntExtra("position",1);
        positionList=pos;
        Log.e("position",String.valueOf(pos));



        mDb = DbUtil.getDbConnection(this);
        mCursor= DbUtil.getCursor(this,mDb);
        mCursor.moveToPosition(pos);

        //Get the id value of this row
        String name = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_NAME));
        Log.e("column",name);
        String date = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_DATE));
        Log.e("column",date);
        int age = mCursor.getInt(mCursor.getColumnIndexOrThrow(DbHelper.COL_AGE));
        Log.e("column2",String.valueOf(age));
        String chip = (mCursor.getInt(mCursor.getColumnIndexOrThrow(DbHelper.COL_CHIP))==1)?"yes":"no";
        Log.e("chip",chip);
        String type = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_TYPE));
        Log.e("column",type);
        String photo = mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_PHOTO));

        if(photo!=null) {
   //         Log.e("column_photo", photo);
            aPhoto.setImageBitmap(decodeFromBase64ToBitmap(photo));
        }else {
            aPhoto.setBackgroundResource(R.drawable.no_pic);
        }



        //Refresh the list
        mCursor.requery();

        iName.setText("name: "+name);
        iDate.setText("date: "+date);
        iAge.setText("age: "+String.valueOf(age));
        iChip.setText("chip: "+chip);
        iType.setText("type: "+type);



        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                int pos = i.getIntExtra("position",2);
                Log.e("position",String.valueOf(pos));

                mCursor.moveToPosition(pos);
                //Get the id value of this row
                String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
                Log.e("rowID",rowId);

                mDb.delete(DbHelper.TABLE_NAME, "_id = ?", new String[]{rowId});
                //Refresh the list
                mCursor.requery();

                Intent i2 = new Intent(AnimalInfo.this, MainActivity.class);
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
