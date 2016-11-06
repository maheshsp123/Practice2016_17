package bvb.android.example.com.practice2016_17;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Date;

import bvb.android.example.com.practice2016_17.data.WeatherContract;
import bvb.android.example.com.practice2016_17.data.WeatherDbHelper;

public class contentProviderTest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_test1);
        WeatherContract.logAllMembersAndFunctionsValues();


        //DELETE EXISTING DATABASE
        getApplicationContext().deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        //WeatherContract.logAllMembersAndFunctionsValues();
        WeatherContract.rawInsertTestRows(getApplicationContext());

        Uri uri=WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                "6411",new Date(2014,12,20).getTime());// December 20th, 2014 is 1419033600L new Date(2014,12,20)
        Log.i("contentActivity",uri.toString());
        Cursor c=getContentResolver().query(
                uri,
                null,
                null,//"location.location_setting = ? AND date = ?" will be supplied in content provider,
                null,//new String[]{"6411","1419033600"} will be supplied from URI within content provider ,
                null);

        StringBuffer temp;
        if (c.moveToFirst()) {
            Log.i("Db", "content provider row obtained");
            do {
                temp=new StringBuffer();
                for(int i=0;i<c.getColumnCount();i++)
                    temp.append(" "+c.getString(i));
                Log.i("Db", temp.toString());
            } while (c.moveToNext());
        }else
            Log.i("Db", "content provider failed");






    }
}
