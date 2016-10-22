package bvb.android.example.com.practice2016_17;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import bvb.android.example.com.practice2016_17.data.WeatherContract;
import bvb.android.example.com.practice2016_17.data.WeatherDbHelper;

public class contentProviderTest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_test1);

        //DELETE EXISTING DATABASE
        getApplicationContext().deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        //WeatherContract.logAllMembersAndFunctionsValues();
        WeatherContract.rawInsertTestRows(getApplicationContext());

        Uri uri=WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                "6411", 1419033600L);
        Log.i("contentActivity",uri.toString());
        Cursor c=getContentResolver().query(
                uri,
                null,
                "location.location_setting = ? AND date = ?",
                new String[]{"6411","1419033600"},
                null);

        StringBuffer temp;
        if (c.moveToFirst()) {
            Log.i("Db", "content provider row obtained");
        }else Log.i("Db", "content provider failed");
        do {
            temp=new StringBuffer();
            for(int i=0;i<=4;i++)
                temp.append(" "+c.getString(i));
            Log.i("Db", temp.toString());
        } while (c.moveToNext());




    }
}
