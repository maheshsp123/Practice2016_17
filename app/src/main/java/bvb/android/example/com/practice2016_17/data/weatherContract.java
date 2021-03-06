package bvb.android.example.com.practice2016_17.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;
import android.util.Log;

import java.util.Date;

/**
 * Created by msp on 03-Oct-16.
 */
public class WeatherContract {


    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY =
            "bvb.android.example.com.practice2016_17";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_WEATHER = "weather";
    public static final String PATH_LOCATION = "location";

    public static class LocationEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +
                        "/" + CONTENT_AUTHORITY + "/" +
                        PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +
                        "/" + CONTENT_AUTHORITY + "/" +
                        PATH_LOCATION;

        // Table name
        public static final String TABLE_NAME="location";
        // The location setting string is what will be sent to openweathermap
        // as the location query.
        public static final String COLUMN_LOCATION_SETTING = "location_setting";

        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String COLUMN_CITY_NAME = "city_name";

        // In order to uniquely pinpoint the location on the map when we launch the
        // map intent, we store the latitude and longitude as returned by openweathermap.
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static void logAllMembersAndFunctionsValues(){
            Log.i("","LocationEntry class---------");
            Log.i("CONTENT_URI",""+CONTENT_URI.toString());
            Log.i("buildLocationUri()=",""+buildLocationUri(123456).toString());


        }

    }

    /* Inner class that defines the table contents of the weather table */
    public static final class WeatherEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;

        public static final String TABLE_NAME = "weather";

        // Column with the foreign key into the location table.
        public static final String COLUMN_LOC_KEY = "location_id";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_DATE = "date";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_WEATHER_ID = "weather_id";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_SHORT_DESC = "short_desc";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_HUMIDITY = "humidity";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_PRESSURE = "pressure";

        // Windspeed is stored as a float representing windspeed  mph
        public static final String COLUMN_WIND_SPEED = "wind";

        // Degrees are meteorological degrees (e.g, 0 is north, 180 is south).  Stored as floats.
        public static final String COLUMN_DEGREES = "degrees";

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /*
            Student: This is the buildWeatherLocation function you filled in.
         */
        public static Uri buildWeatherLocation(String locationSetting) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }

        public static Uri buildWeatherLocationWithStartDate(
                String locationSetting, long startDate) {
            long normalizedDate = normalizeDate(startDate);
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
        }

        public static Uri buildWeatherLocationWithDate(String locationSetting, long date) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendPath(Long.toString(normalizeDate(date))).build();
        }

        public static String getLocationSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_DATE);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        }

        public static void logAllMembersAndFunctionsValues(){
            Log.i("","weatherEntry class---------");
            Log.i("CONTENT_URI =",""+CONTENT_URI.toString());
            Log.i("CONTENT_TYPE =",""+CONTENT_TYPE.toString());
            Log.i("CONTENT_ITEM_TYPE =",""+CONTENT_ITEM_TYPE.toString());
            Log.i("buildWeatherUri() =",""+buildWeatherUri(94043).toString());
            Log.i("buildWeatherLocation()=",""+buildWeatherLocation("64111" ).toString());
            Log.i("build We Lo st date()=",""+buildWeatherLocationWithStartDate("64111", 14351040000L ).toString());
            Log.i("build We Lo date()=",""+buildWeatherLocationWithDate("64111", 14351040000L ).toString());




        }
    }

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }



    public static void logAllMembersAndFunctionsValues(){
        Log.i("","WeatherContract class******");
        Log.i("CONTENT_AUTHORITY=",""+CONTENT_AUTHORITY);
        Log.i("BASE_CONTENT_URI=",""+BASE_CONTENT_URI.toString());

        LocationEntry.logAllMembersAndFunctionsValues();
        WeatherEntry.logAllMembersAndFunctionsValues();


    }

    public static void rawInsertTestRows(Context context)
    {


        rawInsertTestLocationRows(context,12345,"south Pole",46.7488,129.353);
        rawInsertTestLocationRows(context,6411,"hubli",15.35,75.17);

        //COLUMN_LOC_KEY, COLUMN_DATE, COLUMN_DEGREES, COLUMN_HUMIDITY, COLUMN_PRESSURE, COLUMN_MAX_TEMP, COLUMN_MIN_TEMP, COLUMN_SHORT_DESC
        rawInsertTestWeatherRows(context,2,
                normalizeDate( new Date(2014,12,20).getTime() ),
                1.1, 1.2, 1.3, 75, 65, "Asteroids",5.5, 321);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,7).getTime() ),45, 30, 963.36, 33, 22, "Clear",3.61, 800);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,8).getTime() ),66, 24, 963.88, 31, 18, "Clear",4.3, 800);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,9).getTime() ),87, 25, 964.75, 31, 14, "Clear",3.35, 800);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,10).getTime() ),83, 0, 965.26, 31, 14, "Clear",2.48, 800);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,11).getTime() ),208, 0, 964.3, 31, 14, "Clear",1.02, 800);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,12).getTime() ),200, 0, 964.43, 31, 14, "Clear",0.5, 800);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,13).getTime() ),0, 0, 964.53, 31, 15, "Clear",0, 800);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,14).getTime() ),169, 0, 966.39, 31, 18, "Rain",2.02, 500);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,15).getTime() ),82, 0, 966.3, 30, 21, "Rain",3.02, 501);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,16).getTime() ),80, 0, 966.7, 28, 20, "Rain",4.73, 500);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,17).getTime() ),88, 0, 965.61, 29, 22, "Rain",4.67, 500);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,18).getTime() ),92, 0, 964.37, 28, 20, "Rain",4.5, 501);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,19).getTime() ),100, 0, 964.37, 27, 20, "Rain",5.7, 501);
        rawInsertTestWeatherRows(context,2,normalizeDate( new Date(2016,11,20).getTime() ),108, 0, 965.74, 28, 21, "Rain",4.76, 500);

        logAllTableRows(context);

    }
    public static void rawInsertTestLocationRows(Context context,int column1,String column2, double column3,double column4){
        SQLiteDatabase db= new WeatherDbHelper(context).getWritableDatabase();
        long locationRowId;
        ContentValues testValues;


        //inserting location table row
        testValues = new ContentValues();
        testValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, column1);
        testValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, column2);
        testValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, column3);
        testValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, column4);
        //Insert ContentValues into database and get a row ID back
        locationRowId = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, testValues);
        // Verify we got a row back.
        if(locationRowId != -1) Log.i("Db","location table row added "+locationRowId);
        else Log.i("Db","adding row failed location table");

        db.close();
    }

    public static void rawInsertTestWeatherRows(Context context,int column1,Long column2, double column3,double column4,double column5
    ,int column6,int column7,String column8,double column9,int column10){
        SQLiteDatabase db= new WeatherDbHelper(context).getWritableDatabase();
        long weatherRowId;
        ContentValues weatherValues;

        weatherValues=new ContentValues();
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_LOC_KEY, column1);//locationRowId
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, column2);//TEST_DATE
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, column3);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, column4);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, column5);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, column6);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, column7);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,column8 );//Asteroids
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, column9);//5.5
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, column10);//321
        //Insert ContentValues into database and get a row ID back
        weatherRowId = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, weatherValues);
        // Verify we got a row back.
        if(weatherRowId != -1) Log.i("Db","weather table row added "+weatherRowId);
        else Log.i("Db","adding row failed location table");

        db.close();

    }

    public static  void logAllTableRows(Context context){
        SQLiteDatabase db= new WeatherDbHelper(context).getWritableDatabase();
        Cursor c;
        StringBuffer temp;

        Log.i("Db", "Location Rows*************");
        c = db.rawQuery("SELECT * FROM location", null);


        if (c.moveToFirst()) {
            Log.i("Db", c.getString(0));
        }
        do {
            temp=new StringBuffer();
            for(int i=0;i<=4;i++)
                temp.append(" "+c.getString(i));
            Log.i("Db", temp.toString());
        } while (c.moveToNext());

        Log.i("Db", "Weather Rows*************");
        c = db.rawQuery("SELECT * FROM weather", null);


        if (c.moveToFirst()) {
            Log.i("Db", c.getString(0));
        }
        do {
            temp=new StringBuffer();
            for(int i=0;i<=4;i++)
                temp.append(" "+c.getString(i));
            Log.i("Db", temp.toString());
        } while (c.moveToNext());
    }
}
