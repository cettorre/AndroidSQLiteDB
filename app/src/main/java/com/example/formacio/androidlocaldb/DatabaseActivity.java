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

public class DatabaseActivity extends Activity implements AdapterView.OnItemClickListener {


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
        mList.setOnItemClickListener(this);

        mHelper = new DbHelper(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Open connections to the database
        mDb = mHelper.getWritableDatabase();
        String[] columns = new String[]{"_id", DbHelper.COL_NAME, DbHelper.COL_DATE};
        mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //Refresh the list
        String[] headers = new String[]{DbHelper.COL_NAME, DbHelper.COL_DATE};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                mCursor, headers, new int[]{android.R.id.text1, android.R.id.text2});
        mList.setAdapter(mAdapter);



        //Add a new value to the database
        ContentValues cv = new ContentValues(2);

        Intent i2 = getIntent();
        String nameA = i2.getStringExtra("name");

        cv.put(DbHelper.COL_NAME, nameA);

        //Create a formatter for SQL date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv.put(DbHelper.COL_DATE, dateFormat.format(new Date())); //InsertAnimal 'now' as the date
        mDb.insert(DbHelper.TABLE_NAME, null, cv);
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



    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //Delete the item from the database
        mCursor.moveToPosition(position);
        //Get the id value of this row
        String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
        mDb.delete(DbHelper.TABLE_NAME, "_id = ?", new String[]{rowId});
        //Refresh the list
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
    }
}