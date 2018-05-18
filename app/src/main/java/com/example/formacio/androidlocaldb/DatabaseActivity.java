package com.example.formacio.androidlocaldb;

        import android.app.Activity;
        import android.content.ContentValues;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;
        import java.text.SimpleDateFormat;
        import java.util.Date;

public class DatabaseActivity extends Activity  {


    Button mAddAnimal;

    ListView mList;

    DbHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mAddAnimal= (Button) findViewById(R.id.addAnimal);



        mAddAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatabaseActivity.this, InsertAnimal.class);
                startActivity(i);
            }
        });

        mList = (ListView) findViewById(R.id.list);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(DatabaseActivity.this, AnimalInfo.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });




        mHelper = new DbHelper(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Open connections to the database
        mDb = mHelper.getWritableDatabase();
        // TODO add coloumn
        String[] columns = new String[]{
                "_id", DbHelper.COL_NAME,   DbHelper.COL_DATE,DbHelper.COL_AGE};
       //         DbHelper.COL_CHIP,          DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
        mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //Refresh the list
        // TODO add coloumn
        String[] headers = new String[]{DbHelper.COL_NAME, DbHelper.COL_DATE,DbHelper.COL_AGE};
//                DbHelper.COL_CHIP,DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                mCursor, headers, new int[]{android.R.id.text1, android.R.id.text2});
        mList.setAdapter(mAdapter);


        //Refresh the list
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
        //Clear the edit field
       // mText.setText(null);




    }

    @Override
    public void onPause() {
        super.onPause();
        //Close all connections
        mDb.close();
        mCursor.close();
    }


}