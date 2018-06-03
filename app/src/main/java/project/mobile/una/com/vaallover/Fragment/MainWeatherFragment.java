package project.mobile.una.com.vaallover.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import project.mobile.una.com.vaallover.Model.Sys;
import project.mobile.una.com.vaallover.Model.WeatherCurrentContainer;
import project.mobile.una.com.vaallover.R;
import project.mobile.una.com.vaallover.interfaces.FragmentUpdate;

public class MainWeatherFragment extends Fragment implements FragmentUpdate {

    TextView country, morningTime, eveningTime, pressure, currentTemperature, maxTemperature, minTemperature, wetness, wind, clouds;
    WeatherCurrentContainer weather;
    Realm realm;

    public MainWeatherFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onStop() {
        realm.close();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        country = rootView.findViewById(R.id.main_menu_current_country);
        morningTime = rootView.findViewById(R.id.main_menu_morning);
        eveningTime = rootView.findViewById(R.id.main_menu_evening);
        pressure = rootView.findViewById(R.id.main_menu_pressure);
        currentTemperature = rootView.findViewById(R.id.main_menu_actual_temperature);
        maxTemperature = rootView.findViewById(R.id.main_menu_max_temperature);
        minTemperature = rootView.findViewById(R.id.main_menu_min_temperature);
        wetness = rootView.findViewById(R.id.main_menu_current_wetness);
        wind = rootView.findViewById(R.id.main_menu_current_wind);
        clouds = rootView.findViewById(R.id.main_menu_current_clouds);
        update();
        return rootView;
    }

    @Override
    public void update() {
        if (realm.isClosed())
            realm = Realm.getDefaultInstance();

        weather = realm.where(WeatherCurrentContainer.class).findFirst();
        try {
            country.setText(weather.getName());
            morningTime.setText(String.valueOf(weather.getSys().getSunrise()));
            eveningTime.setText(String.valueOf(weather.getSys().getSunset()));
            pressure.setText(String.valueOf(weather.getMain().getPressure()));
            currentTemperature.setText(String.valueOf(weather.getMain().getTemp()));
            maxTemperature.setText(String.valueOf(weather.getMain().getTempMax()));
            minTemperature.setText(String.valueOf(weather.getMain().getTempMin()));
            wetness.setText(String.valueOf(weather.getMain().getHumidity()));
            wind.setText(String.valueOf(weather.getWind().getSpeed()));
            clouds.setText(String.valueOf(weather.getClouds().getAll()));
        }catch (Exception ignored){}

    }
}
