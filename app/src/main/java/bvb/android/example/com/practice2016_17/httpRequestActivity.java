package bvb.android.example.com.practice2016_17;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class httpRequestActivity extends AppCompatActivity {

    URL url;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String forecastJsonStr = "json not yet fetched";
    String LOG_CAT;
    String data="nothing";
    TextView weatherDataTextView_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG_CAT=httpRequestActivity.class.getSimpleName();
        setContentView(R.layout.activity_http_request);
        weatherDataTextView_ref=(TextView) findViewById(R.id.weatherDataTV_id);

    }

    @Override
    protected void onStart() {
        super.onStart();
        featchWeatherAsynch background = new featchWeatherAsynch();
        background.execute();
    }

    class featchWeatherAsynch extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Replace the API key i.e &APPID=?
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=hubli,IN&APPID=11111111111111111111111111");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    data="input stream null";
                    //return data;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    data="Stream was empty.  No point in parsing";
                    //return data;
                }
                forecastJsonStr = buffer.toString();
            }
            catch (MalformedURLException e){
                Log.e(LOG_CAT, "url exception");
                Log.e(LOG_CAT, e.toString());
            }
            catch (IOException e) {
                Log.e(LOG_CAT, "Error ", e);
                e.printStackTrace();
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                data="code didn't successfully get the weather data";
                //return data;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_CAT, "Error closing stream", e);
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            weatherDataTextView_ref.setText(forecastJsonStr);

        }
    }
}
