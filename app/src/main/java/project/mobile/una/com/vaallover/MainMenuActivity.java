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

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import project.mobile.una.com.vaallover.Adapter.SectionsPagerAdapter;
import project.mobile.una.com.vaallover.Model.WeatherCurrentContainer;
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
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    // Get a Realm instance for this thread
    Realm realm;
    WeatherCurrentContainer currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        currentWeather = realm.where(WeatherCurrentContainer.class).findFirst();

        if (currentWeather == null) {
            realm.beginTransaction();
            currentWeather = realm.createObject(WeatherCurrentContainer.class);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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



    private void requestWeather(){
        //handler.objectRequest("http://api.openweathermap.org/data/2.5/forecast", Request.Method.GET, params, WeatherForecastContainer.class, new Response.Listener<WeatherForecastContainer>() {
        handler.objectRequest("http://api.openweathermap.org/data/2.5/weather", Request.Method.GET, params, WeatherCurrentContainer.class, new Response.Listener<WeatherCurrentContainer>() {
            @Override
            public void onResponse(WeatherCurrentContainer response) {

                // Persist your data in a transaction
                realm.beginTransaction();
                realm.deleteAll();
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
                params.clear();
                params.put("APPID","63dc9dd7b6386052325bd9c6885402a0");
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                //requestWeather();
                mSectionsPagerAdapter.notifyDataSetChanged();
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

}
