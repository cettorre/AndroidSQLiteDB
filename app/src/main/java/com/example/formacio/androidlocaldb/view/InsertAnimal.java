package com.example.formacio.androidlocaldb.view;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.formacio.androidlocaldb.R;
import com.example.formacio.androidlocaldb.persistence.DbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.formacio.androidlocaldb.view.DatabaseActivity.mCursor;
import static com.example.formacio.androidlocaldb.view.DatabaseActivity.mHelper;
import static com.example.formacio.androidlocaldb.view.DatabaseActivity.mDb;

public class InsertAnimal extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText type;
    CheckBox hasChip;
    int iChip;


    Button sendData;
    Button takePhoto;

    ImageView mImageView;
    Uri photoUri = null;

    static ContentValues cv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        type = (EditText) findViewById(R.id.type);
        hasChip=findViewById(R.id.hasChip);

        sendData=(Button) findViewById(R.id.sendData);
        takePhoto=findViewById(R.id.takePhoto);

        mImageView=findViewById(R.id.photo);

        mHelper = new DbHelper(this);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();


            }
        });



        //WRITE ON DB

        //Open connections to the database
        mDb = mHelper.getWritableDatabase();
        String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE,DbHelper.COL_AGE, DbHelper.COL_CHIP, DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
        mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //Refresh the list


        //Add a new value to the database
        cv = new ContentValues(2);


        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InsertAnimal.this,DatabaseActivity.class);

                String sName=name.getText().toString();
                String sAge="";

                try {
                    int intAge=Integer.parseInt(age.getText().toString());
                     sAge=age.getText().toString();
                    Log.i("stringAge",sAge+" is a number");


                String sType=type.getText().toString();



                //todo add value to DB
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cv.put(DbHelper.COL_NAME, sName);
                cv.put(DbHelper.COL_DATE, dateFormat.format(new Date())); //InsertAnimal 'now' as the date
                cv.put(DbHelper.COL_AGE, sAge);
                cv.put(DbHelper.COL_TYPE, sType);
                if(hasChip.isChecked()){
                    cv.put(DbHelper.COL_CHIP,1);
                }else {
                    cv.put(DbHelper.COL_CHIP,0);
                }

                mDb.insert(DbHelper.TABLE_NAME, null, cv);

                startActivity(i);
                } catch (NumberFormatException e) {
                    Log.i("stringAge",sAge+" is not a number");
                    Toast t=Toast.makeText(InsertAnimal.this,sAge+"is not a valid age",Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });




    }


    static final int REQUEST_IMAGE_CAPTURE = 1;


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this,
                        "com.example.formacio.androidlocaldb.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    String mCurrentPhotoPath;

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("photo_path", mCurrentPhotoPath);


        return image;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            mImageView.setImageURI(photoUri);
            String encodedImage=  InsertAnimal.convertToBase64(mCurrentPhotoPath);
            Log.e("encoded_image1",encodedImage);
            cv.put(DbHelper.COL_PHOTO, encodedImage);
       }
    }



    public static String convertToBase64(String imagePath)    {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }




}
