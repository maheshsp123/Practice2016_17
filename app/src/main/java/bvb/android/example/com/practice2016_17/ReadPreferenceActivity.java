package bvb.android.example.com.practice2016_17;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadPreferenceActivity extends AppCompatActivity {
    TextView musicTvRef, levelTvRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_preference);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean musicPrefStrRef = sharedPref.getBoolean("pref_music", true);
        musicTvRef=(TextView)findViewById(R.id.MusicTVId);
        musicTvRef.setText(""+musicPrefStrRef);

        String levelPrefeStrRef= sharedPref.getString("pref_level","null");
        levelTvRef=(TextView)findViewById(R.id.levelTvId);
        levelTvRef.setText(levelPrefeStrRef);
    }
}
