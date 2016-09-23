package bvb.android.example.com.practice2016_17;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    String LOG_CAT= MainActivity.class.getSimpleName();
    Button loginActivityLunchButton;
    //AppCompatActivity thisActivityReference;
    Button firstActBtnRefernce;
    AppCompatActivity thisActivityReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_adiv);
        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_list_view);
        Log.i(LOG_CAT,"onCreate()");
        thisActivityReference=this;
        firstActBtnRefernce= (Button) findViewById(R.id.firstActBtnId);
        firstActBtnRefernce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=
                  new Intent(thisActivityReference,FirstActivity.class);
                startActivity(intent);
            }
        });

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        thisActivityReference=this;
        loginActivityLunchButton=(Button)findViewById(R.id.loginActivityLunch);
        loginActivityLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(thisActivityReference, loginDemoActivity.class);
                startActivity(intent);
            }
        });*/
    }//end of onCreate()

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }*/



}
