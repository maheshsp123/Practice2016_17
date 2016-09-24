package bvb.android.example.com.practice2016_17;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FragmentDemoActivity extends AppCompatActivity {
    Button changeFragButtonRef;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);


        Configuration configuration=getResources().getConfiguration();
        if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(FragmentDemoActivity.this, "landscape", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment2, new DetailedFragment()).commit();
            Log.i("Frag","Acti: oncreate() ");
            changeFragButtonRef=(Button)findViewById(R.id.changeFragButton);
            changeFragButtonRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag)
                    {   Log.i("Frag","flag=true");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment2, new IndexFragment()).commit();
                        flag=false;
                    }
                    else {
                        Log.i("Frag","flag=false");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment2, new DetailedFragment()).commit();
                        flag=true;
                    }
                }
            });
        }
    }
}
