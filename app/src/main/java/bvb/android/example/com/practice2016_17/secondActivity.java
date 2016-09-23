package bvb.android.example.com.practice2016_17;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class secondActivity extends Activity {
    String LOG_CAT=secondActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i(LOG_CAT,"onCreate()");

        Intent intent=this.getIntent();
        String message=intent.getStringExtra("key_from");
        Log.i(LOG_CAT,message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_CAT,"onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_CAT, "onResume()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(LOG_CAT, "onSaveInstanceState()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_CAT, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_CAT, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_CAT, "onRestart()");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(LOG_CAT, "onRestoreInstanceState()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_CAT, "onDestroy()");
    }
}
