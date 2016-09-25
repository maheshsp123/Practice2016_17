package bvb.android.example.com.practice2016_17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    AppCompatActivity thisActivityReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Log.i("ListItem","onClick()");
        thisActivityReference=this;
        arrayList=new ArrayList<String>();
            arrayList.add("loginDemoActivity");             arrayList.add("secondActivity");
            arrayList.add("MainActivity");            arrayList.add("httpRequestActivity");
            arrayList.add("SettingsActivity");             arrayList.add("ReadPreferenceActivity");
            arrayList.add("FragmentDemoActivity");            arrayList.add("LP_minor1_2c");
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.listview_item2,R.id.listItemId2,arrayList);
        listView=(ListView)findViewById(R.id.listViewId);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListItem","list view item clicked");
                Log.i("ListItem",arrayAdapter.getItem(position));
                Toast.makeText(ListViewActivity.this, arrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                String classNameString="bvb.android.example.com.practice2016_17."+arrayAdapter.getItem(position);
                Class<?> c=null;
                try {
                    c=Class.forName(classNameString);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(thisActivityReference,c);
                startActivity(intent);
            }
        });

    }
}
