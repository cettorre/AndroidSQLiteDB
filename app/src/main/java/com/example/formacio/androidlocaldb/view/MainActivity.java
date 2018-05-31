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

        import com.example.formacio.androidlocaldb.R;
        import com.example.formacio.androidlocaldb.persistence.DbHelper;
        import com.example.formacio.androidlocaldb.persistence.DbUtil;

        import java.lang.reflect.Field;

public class MainActivity extends Activity  {


    Button mAddAnimal;

    ListView mList;


    static SQLiteDatabase mDb;
    static Cursor mCursor;
    static SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddAnimal= (Button) findViewById(R.id.addAnimal);
        mList = (ListView) findViewById(R.id.list);



        mAddAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InsertAnimal.class);
                startActivity(i);
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, AnimalInfo.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });




    }

    @Override
    public void onResume() {
        super.onResume();
        //Open connections to the database
        mDb = DbUtil.getDbConnection(this);
        mCursor=DbUtil.getCursor(this, mDb);

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


        mAdapter = DbUtil.getSimpleCursorAdapter(this);

        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 1) {
                    TextView text = (TextView) view;  // get your View
                    text.setText(DbUtil.mCursor.getString(DbUtil.mCursor.getColumnIndexOrThrow(DbHelper.COL_NAME)));
                   // text.setTextColor(Color.rgb(0,255,255));
                    text.setTextSize(24);
                    if(mCursor.getInt(DbUtil.mCursor.getColumnIndexOrThrow(DbHelper.COL_CHIP))==1)
                        text.setTextColor(Color.rgb(0,255,255));
                }
                return false;
            }
        });

        mList.setAdapter(mAdapter);


        //Refresh the list
        DbUtil.mCursor.requery();
        DbUtil.mAdapter.notifyDataSetChanged();





    }

    @Override
    public void onPause() {
        super.onPause();
        //Close all connections
        mDb.close();
        mCursor.close();
    }


}