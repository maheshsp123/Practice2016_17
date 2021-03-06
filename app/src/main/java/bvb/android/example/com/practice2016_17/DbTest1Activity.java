package bvb.android.example.com.practice2016_17;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import bvb.android.example.com.practice2016_17.data.WeatherDbHelper;
import bvb.android.example.com.practice2016_17.data.WeatherContract;

public class DbTest1Activity extends AppCompatActivity {
    final String LOG_CAT=DbTest1Activity.class.getSimpleName();
    SQLiteDatabase db;
    static final String TEST_LOCATION = "99705";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test1);
        try {
            //DELETE EXISTING DATABASE
            getApplicationContext().deleteDatabase(WeatherDbHelper.DATABASE_NAME);

            Log.i(LOG_CAT, "yet to create db ");
            db = new WeatherDbHelper(getApplicationContext()).getWritableDatabase();

            //check db exists or created
            if (db.isOpen()) Log.i(LOG_CAT, "db is open");

            //check table is created
            Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            if (c.moveToFirst()) {
                Log.i(LOG_CAT, "tables created= " + c.getString(0));
            }
            do {
                Log.i(LOG_CAT, "table = " + c.getString(0));
            } while (c.moveToNext());

            //now adding a row through user defined method
            addRow();

            //query location table through user defined method
            Cursor c2=getAllRowsOfLocationTable();
            c2.moveToFirst();
            Log.i(LOG_CAT, "table = Location rows as follows");
            do {
                Log.i(LOG_CAT, "id="+c2.getInt(0)+"setting= "+c2.getString(1)+"  city name="+c2.getString(2)
                        +"  latitude="+c2.getDouble(3)+"  longitude="+c2.getDouble(4));
            } while (c.moveToNext());


        }catch (Exception e){
            Log.i(LOG_CAT, e.getMessage());

        }

        db.close();
    }

    void addRow(){
        ContentValues testValues = new ContentValues();
        testValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, TEST_LOCATION);
        testValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, "North Pole");
        testValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, 64.7488);
        testValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, -147.353);

        //Insert ContentValues into database and get a row ID back
        long locationRowId;
        locationRowId = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, testValues);
        // Verify we got a row back.
        if(locationRowId != -1) Log.i(LOG_CAT,"location table row added");
            else Log.i(LOG_CAT,"adding row failed location table");
    }

    Cursor getAllRowsOfLocationTable(){
        // Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                WeatherContract.LocationEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        return  cursor;
    }
}
