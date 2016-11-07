package bvb.android.example.com.practice2016_17;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
//                url = new URL
//                        ("http://api.openweathermap.org/data/2.5/weather?q=hubli,IN&APPID=11111111111111111111111");
                final String FORECAST_BASE_URL =
                        "http://api.openweathermap.org/data/2.5/forecast/daily?";

                //building Uri: http://api.openweathermap.org/data/2.5/forecast/daily?q=Hubli&mode=json&units=metric&cnt=14&APPID=1111111111111111111111111111111111
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter("q", "Hubli")
                        .appendQueryParameter("mode", "json")
                        .appendQueryParameter("units", "metric")
                        .appendQueryParameter("cnt", "14")
                        .appendQueryParameter("APPID", "111111111111111111111111111111")//replace your API key here
                        .build();

                URL url = new URL(builtUri.toString());
                Log.i("Uri daily",url.toString());
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
            String[] weatherDisplayTextArray=null;
            try {
                weatherDisplayTextArray=getWeatherDataFromJson(forecastJsonStr,14);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            weatherDataTextView_ref.setText(weatherDisplayTextArray[0]+"/n"+weatherDisplayTextArray[1]);
            Log.i("forecast","LOC_KEY, DATE, DEGREES, HUMIDITY, PRESSURE, MAX_TEMP, MIN_TEMP, SHORT_DESC, WINDSPED, WETH_ID");
            for(String s:weatherDisplayTextArray){
                Log.i("forecast",s);
            }


        }
    }//end of inner class


    private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
        // OWM returns daily forecasts based upon the local time of the city that is being
        // asked for, which means that we need to know the GMT offset to translate this data
        // properly.

        // Since this data is also sent in-order and the first day is always the
        // current day, we're going to take advantage of that to get a nice
        // normalized UTC date for all of our weather.

        Time dayTime = new Time();
        dayTime.setToNow();

        // we start at the day returned by local time. Otherwise this is a mess.
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        // now we work exclusively in UTC
        dayTime = new Time();

        String[] resultStrs = new String[numDays];
        for(int i = 0; i < weatherArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            //COLUMN_LOC_KEY, COLUMN_DATE, COLUMN_DEGREES, COLUMN_HUMIDITY, COLUMN_PRESSURE, COLUMN_MAX_TEMP,
            // COLUMN_MIN_TEMP, COLUMN_SHORT, WIND_SPED, WEATHER_ID
            long dateTime;
            int degree;
            int humidity;
            double pressure;
            double maxTemperature;
            double minTemperature;
            String description;
            double windSpeed;
            int weather_id;


            String day;
            String highAndLow;

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".

            degree=dayForecast.getInt("deg");
            humidity=dayForecast.getInt("humidity");
            pressure=dayForecast.getDouble("pressure");
            windSpeed=dayForecast.getDouble("speed");



            dateTime = dayForecast.getLong("dt");
            day = getReadableDateString(dateTime);
            //day =dayTime.toString();

            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);
            weather_id=dayForecast.getJSONArray("weather").getJSONObject(0).getInt("id");

            highAndLow = formatHighLows(high, low);

            maxTemperature=high;
            minTemperature=low;
            //highAndLow = high+"/"+low;
            resultStrs[i] = day + " - " + degree + " - " + humidity+" - "+pressure+" - "+maxTemperature+" - "+minTemperature+" - "+description+" - "+windSpeed+" - "+weather_id;
        }

        return resultStrs;
    }

    private String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date).toString();
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

}
