package project.mobile.una.com.vaallover;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import project.mobile.una.com.vaallover.Adapter.SectionsPagerAdapter;
import project.mobile.una.com.vaallover.Model.WeatherCurrentContainer;
import project.mobile.una.com.vaallover.Model.WeatherForecastContainer;
import project.mobile.una.com.vaallover.service.ServiceHandler;

public class MainMenuActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections.
     * We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory.
     * If this becomes too memory intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    Toolbar toolbar;
    FloatingActionButton fab;
    ServiceHandler handler;
    Map<String, Object> params;
    SectionsPagerAdapter mSectionsPagerAdapter;
    LocationManager locationManager;
    ViewPager mViewPager;
    LocationListener locationListener;
    CoordinatorLayout layout;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    // Get a Realm instance for this thread
    Realm realm;
    WeatherCurrentContainer currentWeather;
    WeatherForecastContainer forecastWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        currentWeather = realm.where(WeatherCurrentContainer.class).findFirst();
        forecastWeather = realm.where(WeatherForecastContainer.class).findFirst();

        if (currentWeather == null) {
            realm.beginTransaction();
            currentWeather = realm.createObject(WeatherCurrentContainer.class);
            realm.commitTransaction();
        }else
            changeWallpaper();

        if (forecastWeather == null) {
            realm.beginTransaction();
            forecastWeather = realm.createObject(WeatherForecastContainer.class);
            realm.commitTransaction();
        }

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSectionsPagerAdapter.notifyDataSetChanged();
                if (isLocationEnabled())  //Si tiene el gps encendido entonces
                        enableLocationTrack();
                else
                        checkGPSAlert(); //enviar alerta
            }
        });

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        if (isLocationEnabled())  //Si tiene el gps encendido entonces
            enableLocationTrack();
        else
            checkGPSAlert(); //enviar alerta
    }

    

    public void onResume() {
        super.onResume();
        if (realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }

        if (isLocationEnabled()) { //Si tiene el gps encendido entonces
            if (currentWeather == null) //Obtenga el clima de la ubicacion
                enableLocationTrack();
            else{ //caso contrario utiliza el almacenado
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
        }
        else
        if (currentWeather == null) //caso contrario si no se tiene almacenado nada,
            checkGPSAlert(); //enviar alerta
        else{ //caso contrario utiliza el almacenado
            mSectionsPagerAdapter.notifyDataSetChanged();
        }


    }

    @Override
    protected void onStop() {
        realm.close();
        super.onStop();
    }

    @Override
    public void onPause() {
        realm.close();
        locationManager.removeUpdates(locationListener);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }



    public void checkGPSAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(R.string.GPS_alert_title);
        alertDialog.setMessage(R.string.GPS_alert_message);

        alertDialog.setPositiveButton(R.string.GPS_alert_Button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplicationContext().startActivity(intent);
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null)
            for (String permission : permissions)
                if (ActivityCompat.checkSelfPermission(context, permission) !=
                        PackageManager.PERMISSION_GRANTED) return false;
        return true;
    }

    private boolean isLocationEnabled() {
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) &&
                hasPermissions(this, PERMISSIONS);
    }

    private void enableLocationTrack(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 100, locationListener);
        else
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
    }



    private void requestCurrentWeather(double Latitude, double Longitude){
        params = new ArrayMap<>();
        params.put("APPID","63dc9dd7b6386052325bd9c6885402a0");
        params.put("lat", Latitude);
        params.put("lon", Longitude);
        if(Locale.getDefault().getLanguage().equals("en")) {
            params.put("units", "imperial");
            params.put("lang", "en");
        }
        else {
            params.put("units", "metric");
            params.put("lang", Locale.getDefault().getLanguage());
        }

        handler.objectRequest("http://api.openweathermap.org/data/2.5/weather", Request.Method.GET, params, WeatherCurrentContainer.class, new Response.Listener<WeatherCurrentContainer>() {
            @Override
            public void onResponse(WeatherCurrentContainer response) {
                final RealmResults<WeatherCurrentContainer> results = realm.where(WeatherCurrentContainer.class).findAll();
                // Persist your data in a transaction
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.insert(response);
                realm.commitTransaction();
                currentWeather = response;
                mSectionsPagerAdapter.notifyDataSetChanged();
                changeWallpaper();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //en caso de problemas ejecutar esto
                Log.d("my Weather", error.toString());
            }
        });

    }

    public void requestForecastWeather(double Latitude, double Longitude){
        params = new ArrayMap<>();
        params.put("APPID","63dc9dd7b6386052325bd9c6885402a0");
        params.put("lat", Latitude);
        params.put("lon", Longitude);
        if(Locale.getDefault().getLanguage().equals("en")) {
            params.put("units", "imperial");
            params.put("lang", "en");
        }
        else {
            params.put("units", "metric");
            params.put("lang", Locale.getDefault().getLanguage());
        }

        handler.objectRequest("http://api.openweathermap.org/data/2.5/forecast", Request.Method.GET, params, WeatherForecastContainer.class, new Response.Listener<WeatherForecastContainer>() {
            @Override
            public void onResponse(WeatherForecastContainer response) {

                final RealmResults<WeatherForecastContainer> results = realm.where(WeatherForecastContainer.class).findAll();

                // Persist your data in a transaction
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.insert(response);
                realm.commitTransaction();

                mSectionsPagerAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //en caso de problemas ejecutar esto
                Log.d("my Weather", error.toString());
            }
        });
    }

    public void requestForecastWeather(String city){
        params = new ArrayMap<>();
        params.put("APPID","63dc9dd7b6386052325bd9c6885402a0");
        params.put("q", city);
        if(Locale.getDefault().getLanguage().equals("en")) {
            params.put("units", "imperial");
            params.put("lang", "en");
        }
        else {
            params.put("units", "metric");
            params.put("lang", Locale.getDefault().getLanguage());
        }

        handler.objectRequest("http://api.openweathermap.org/data/2.5/forecast", Request.Method.GET, params, WeatherForecastContainer.class, new Response.Listener<WeatherForecastContainer>() {
            @Override
            public void onResponse(WeatherForecastContainer response) {

                final RealmResults<WeatherForecastContainer> results = realm.where(WeatherForecastContainer.class).findAll();

                // Persist your data in a transaction
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.insert(response);
                realm.commitTransaction();

                mSectionsPagerAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //en caso de problemas ejecutar esto
                Log.d("my Weather", error.toString());
            }
        });
    }

    private void init(){

        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.pageViewContainer);
        fab = findViewById(R.id.fab);
        layout = findViewById(R.id.main_content);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        currentWeather = new WeatherCurrentContainer();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Initialize Realm (just once per application)
        Realm.init(this);
        handler = new ServiceHandler();
        params = new ArrayMap<>();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double Latitude = location.getLatitude();
                double Longitude = location.getLongitude();
                //requestCurrentWeather(Latitude, Longitude);
                //requestForecastWeather(Latitude, Longitude);
                mSectionsPagerAdapter.notifyDataSetChanged();
                locationManager.removeUpdates(locationListener);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    private void changeWallpaper(){
        if (currentWeather.getWeather().get(0) != null) {
            switch (currentWeather.getWeather().get(0).getIcon()){
                case "01d": //clear sky
                    layout.setBackgroundResource(R.drawable.clear);
                    break;
                case "01n": //clear sky
                    layout.setBackgroundResource(R.drawable.nclear);
                    break;
                case "02d": //few clouds
                    layout.setBackgroundResource(R.drawable.someclouds);
                    break;
                case "02n": //few clouds
                    layout.setBackgroundResource(R.drawable.nsomecloud);
                    break;
                case "03d": //scattered clouds
                    layout.setBackgroundResource(R.drawable.clouds);
                    break;
                case "03n": //scattered clouds
                    layout.setBackgroundResource(R.drawable.nclouds);
                    break;
                case "04d": // broken clouds
                    layout.setBackgroundResource(R.drawable.clouds);
                    break;
                case "04n": // broken clouds
                    layout.setBackgroundResource(R.drawable.nclouds);
                    break;
                case "09d": // shower rain
                    layout.setBackgroundResource(R.drawable.rain);
                    break;
                case "09n": // shower rain
                    layout.setBackgroundResource(R.drawable.nrain);
                    break;
                case "10d": //rain
                    layout.setBackgroundResource(R.drawable.rain);
                    break;
                case "10n": //rain
                    layout.setBackgroundResource(R.drawable.nrain);
                    break;
                case "11d": //thunderstorm
                    layout.setBackgroundResource(R.drawable.thunder);
                    break;
                case "11n": //thunderstorm
                    layout.setBackgroundResource(R.drawable.nthunder);
                    break;
                case "13d": //snow
                    layout.setBackgroundResource(R.drawable.snow);
                    break;
                case "13n": //snow
                    layout.setBackgroundResource(R.drawable.nsnow);
                    break;
                case "50d": //mist
                    layout.setBackgroundResource(R.drawable.fog);
                    break;
                case "50n": //mist
                    layout.setBackgroundResource(R.drawable.nfog);
                    break;
            }
        }
    }

}
