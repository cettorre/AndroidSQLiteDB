package com.example.formacio.androidlocaldb.view;

        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.CursorWindow;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;
        import android.widget.TextView;

<<<<<<< Updated upstream:app/src/main/java/com/example/formacio/androidlocaldb/DatabaseActivity.java
        import java.lang.reflect.Field;
        import java.text.SimpleDateFormat;
        import java.util.Date;
=======
        import com.example.formacio.androidlocaldb.persistence.DbHelper;
        import com.example.formacio.androidlocaldb.R;
        import com.example.formacio.androidlocaldb.persistence.DbUtil;
>>>>>>> Stashed changes:app/src/main/java/com/example/formacio/androidlocaldb/view/DatabaseActivity.java

public class DatabaseActivity extends Activity  {


    Button mAddAnimal;

    ListView mList;

   static DbHelper mHelper;
    static SQLiteDatabase mDb;
    static Cursor mCursor;
    static SimpleCursorAdapter mAdapter;

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




    }

    @Override
    public void onResume() {
        super.onResume();
<<<<<<< Updated upstream:app/src/main/java/com/example/formacio/androidlocaldb/DatabaseActivity.java
        //Open connections to the database
        mDb = mHelper.getWritableDatabase();
        // TODO add coloumn
        String[] columns = new String[]{
                "_id", DbHelper.COL_NAME,   DbHelper.COL_DATE,DbHelper.COL_AGE,
                DbHelper.COL_CHIP,          DbHelper.COL_TYPE,DbHelper.COL_PHOTO};
        mCursor = mDb.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //Refresh the list

        //*****************************************

        /*this fix is necessary for android 5.1. Android 6 does not need it.
        In Android 5.1 stored photo exceeds the maximum size allowed
        https://github.com/wallabag/android-app/issues/413
        */
        Field field = null;
        try {
            field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            field.set(null, 10240 * 1024);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //****************************************

        // TODO add coloumn
=======

        //***************************************DB call
        mCursor=DbUtil.getCursor(this,DbUtil.getDbConnection(this));


>>>>>>> Stashed changes:app/src/main/java/com/example/formacio/androidlocaldb/view/DatabaseActivity.java
        String[] headers = new String[]{DbHelper.COL_NAME, DbHelper.COL_DATE,DbHelper.COL_AGE,
                DbHelper.COL_CHIP,DbHelper.COL_TYPE,DbHelper.COL_PHOTO};

        mAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                mCursor, headers, new int[]{android.R.id.text1, android.R.id.text2});

        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 1) {
                    TextView text = (TextView) view;  // get your View
                    text.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(DbHelper.COL_NAME)));
                   // text.setTextColor(Color.rgb(0,255,255));
                    text.setTextSize(24);
                    if(mCursor.getInt(mCursor.getColumnIndexOrThrow(DbHelper.COL_CHIP))==1)
                        text.setTextColor(Color.rgb(0,255,255));
                }
                return false;
            }
        });



        mList.setAdapter(mAdapter);


        //Refresh the list
        mCursor.requery();
        mAdapter.notifyDataSetChanged();





    }

    @Override
    public void onPause() {
        super.onPause();
        //Close all connections


        //todo
        DbUtil.getDbConnection(this).close();
        mCursor.close();
    }


}