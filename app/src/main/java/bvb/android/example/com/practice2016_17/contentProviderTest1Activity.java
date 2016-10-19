package bvb.android.example.com.practice2016_17;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bvb.android.example.com.practice2016_17.data.WeatherContract;

public class contentProviderTest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_test1);
        WeatherContract.logAllMembersAndFunctionsValues();
    }
}
