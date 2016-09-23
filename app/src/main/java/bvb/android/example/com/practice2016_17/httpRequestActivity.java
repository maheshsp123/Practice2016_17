package bvb.android.example.com.practice2016_17;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class httpRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_request);
    }

    @Override
    protected void onStart() {
        super.onStart();
    featchWeatherAsynchTask f=new featchWeatherAsynchTask();
    f.execute();
    }

    class featchWeatherAsynchTask extends AsyncTask<Void,Void,Void>{
        Context mContext;
        String LOG_CAT=featchWeatherAsynchTask.class.getSimpleName();


        /*featchWeatherAsynchTask(Context mContext){
            this.mContext=mContext;
        }*/

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(LOG_CAT, "doInBackground");
            try {
                //put your API key here in place of 1111111111111111111111111111111
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q={Hubli},{IN}&APPID=11111111111111111111111111111");
            }
            catch (MalformedURLException e){
                Log.i(LOG_CAT, e.toString());

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
