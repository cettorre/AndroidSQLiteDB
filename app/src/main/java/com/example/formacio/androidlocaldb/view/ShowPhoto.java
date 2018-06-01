package com.example.formacio.androidlocaldb.view;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.formacio.androidlocaldb.R;
import com.example.formacio.androidlocaldb.persistence.DbHelper;
import com.example.formacio.androidlocaldb.persistence.DbUtil;

import static com.example.formacio.androidlocaldb.view.AnimalInfo.positionList;

public class ShowPhoto extends AppCompatActivity {

    ImageView fullPhoto;

    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        fullPhoto=findViewById(R.id.fullPhoto);

        DbUtil.getCursor(this).moveToPosition(positionList);
        //Get the id value of this row

        String photo = DbUtil.getCursor(this)
                .getString(DbUtil.getCursor(this)
                        .getColumnIndexOrThrow(DbHelper.COL_PHOTO));

        if(photo!=null) {
            bmp = AnimalInfo.decodeFromBase64ToBitmap(photo);
            fullPhoto.setImageBitmap(bmp);

        }else {
            fullPhoto.setBackgroundResource(R.drawable.no_pic);
        }


    }
}
