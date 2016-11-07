package bvb.android.example.com.practice2016_17;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import bvb.android.example.com.practice2016_17.data.WeatherContract;

public class DataBinding1Activity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_binding1);
        listView=(ListView)findViewById(R.id.listViewDataBinding1);
        Uri uri= WeatherContract.WeatherEntry.buildWeatherLocation("6411");//hubli
        Cursor cursor=getContentResolver().query(
                uri,
                null,
                null,
                null,
                null);
        String[] fromColumns =
                {WeatherContract.WeatherEntry.TABLE_NAME+"."+WeatherContract.WeatherEntry.COLUMN_DATE,
                 WeatherContract.WeatherEntry.TABLE_NAME+"."+WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
                 WeatherContract.WeatherEntry.TABLE_NAME+"."+WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
                 WeatherContract.WeatherEntry.TABLE_NAME+"."+WeatherContract.WeatherEntry.COLUMN_MIN_TEMP
                };
        int[] toViews = {R.id.dateId, R.id.descriptionId,R.id.maxId,R.id.minId};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.listview_item_data_binding, cursor, fromColumns, toViews, 0);
        listView.setAdapter(adapter);
    }
}
