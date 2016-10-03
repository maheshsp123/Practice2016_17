package bvb.android.example.com.practice2016_17;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import bvb.android.example.com.practice2016_17.data.WeatherDbHelper;

public class DbTest1Activity extends AppCompatActivity {
    final String LOG_CAT=DbTest1Activity.class.getSimpleName();
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test1);
        //DELETE EXISTING DATABASE
        getApplicationContext().deleteDatabase(WeatherDbHelper.DATABASE_NAME);

        Log.i(LOG_CAT,"yet to create db ");
        db=new WeatherDbHelper(getApplicationContext()).getWritableDatabase();

        //check db exists or created
        if(db.isOpen()) Log.i(LOG_CAT, "db is open");

        //check table is created
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if(c.moveToFirst()){
            Log.i(LOG_CAT,"tables created= "+c.getString(0));
        }
        do{
            Log.i(LOG_CAT,"table = "+c.getString(0));
        }while (c.moveToNext());


        db.close();
    }
}
