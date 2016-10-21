package bvb.android.example.com.practice2016_17;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.net.URI;

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


    }
}
