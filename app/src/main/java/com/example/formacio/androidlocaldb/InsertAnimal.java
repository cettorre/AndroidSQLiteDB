package com.example.formacio.androidlocaldb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertAnimal extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText chip;
    EditText type;


    Button sendData;
    Button takePhoto;

    ImageView mImageView;
    Uri photoUri = null;
    File output;

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
        takePhoto=findViewById(R.id.takePhoto);

        mImageView=findViewById(R.id.photo);

        mHelper = new DbHelper(this);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto("");
            }
        });

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



//TODO  ***************************
        if (savedInstanceState == null){
            File dir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
            dir.mkdirs();
            output = new File(dir,"com.example.formacio.androidlocaldb.foto1.jpg");
        } else {
            output = (File)savedInstanceState.getSerializable("foto1");
        }
        //*************************

    }

    public void capturePhoto(String targetFilename) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  intent.putExtra(MediaStore.EXTRA_OUTPUT,                 Uri.withAppendedPath(mLocationForPhotos, targetFilename));
        //Attempt to invoke virtual method 'android.net.Uri$Builder android.net.Uri.buildUpon()' on a null object reference

//*********TODO
        photoUri = Uri.fromFile(output);
        //The name of the Intent-extra used to indicate a content resolver Uri to be used to store the requested image or video.
     //   intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
    //    startActivityForResult(intent,10);//different req code
//*********
        if (intent.resolveActivity(getPackageManager()) != null) {
                 startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private String convertToBase64(String imagePath)    {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final Uri mLocationForPhotos= null;//>>asignar para guardar imagen todo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //capture thumbnail from extra
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);

            //        Bitmap thumbnail = data.getParcelable("data"); //cannot resolve method
            // Do other work with full size photo saved in mLocationForPhotos


            //get selected image and assign to mImageView after selectImage method
            Uri fullPhotoUri = data.getData();
   //         mImageView.setImageURI(fullPhotoUri);


        }
    }



}
