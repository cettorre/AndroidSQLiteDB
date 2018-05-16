package com.example.formacio.androidlocaldb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertAnimal extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText chip;
    EditText type;
    EditText date;
    EditText photo;

    Button sendData;

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

                i.putExtra("name",sName);
                i.putExtra("age",sAge);
                i.putExtra("chip",sChip);
                i.putExtra("type",sType);
                i.putExtra("date",sDate);
                i.putExtra("photo",sPhoto);




                startActivity(i);
            }
        });
    }



}
