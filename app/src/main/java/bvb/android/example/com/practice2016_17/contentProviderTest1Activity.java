package bvb.android.example.com.practice2016_17;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bvb.android.example.com.practice2016_17.data.WeatherContract;

public class contentProviderTest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_test1);
        //WeatherContract.logAllMembersAndFunctionsValues();
        WeatherContract.rawInsertTestRows(getApplicationContext());

    }
}
