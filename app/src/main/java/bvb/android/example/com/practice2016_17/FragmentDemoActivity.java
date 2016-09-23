package bvb.android.example.com.practice2016_17;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FragmentDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);


        Configuration configuration=getResources().getConfiguration();
        if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(FragmentDemoActivity.this, "landscape", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment2, new DetailedFragment()).commit();
        }
    }
}
