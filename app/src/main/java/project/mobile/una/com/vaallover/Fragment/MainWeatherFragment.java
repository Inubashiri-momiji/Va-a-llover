package project.mobile.una.com.vaallover.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import project.mobile.una.com.vaallover.Model.Sys;
import project.mobile.una.com.vaallover.Model.WeatherCurrentContainer;
import project.mobile.una.com.vaallover.R;
import project.mobile.una.com.vaallover.interfaces.FragmentUpdate;

public class MainWeatherFragment extends Fragment implements FragmentUpdate {

    TextView country, morningTime, eveningTime, pressure, currentTemperature, maxTemperature,
            minTemperature, wetness, wind, clouds, description, tempSymbol;
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
        description = rootView.findViewById(R.id.main_menu_description);
        tempSymbol= rootView.findViewById(R.id.main_menu_actual_temperature_symbol);
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
            description.setText(weather.getWeather().get(0).getDescription());
            currentTemperature.setText(String.valueOf(weather.getMain().getTemp()));

            Date date = new Date((long)weather.getSys().getSunrise() * 1000);
            String time = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(date);
            morningTime.setText(time);

            date = new Date((long)weather.getSys().getSunset() * 1000);
            time = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(date);
            eveningTime.setText(time);

            pressure.setText(String.format("%s hPa", String.valueOf(weather.getMain().getPressure())));
            wetness.setText(String.format("%s %%", String.valueOf(weather.getMain().getHumidity())));

            if(Locale.getDefault().getLanguage().equals("en")) {
            //imperial
                tempSymbol.setText("°F");
                maxTemperature.setText(String.format("%s °F", String.valueOf(weather.getMain().getTempMax())));
                minTemperature.setText(String.format("%s °F", String.valueOf(weather.getMain().getTempMin())));
                wind.setText(String.format("%s mph", String.valueOf(weather.getWind().getSpeed())));
            }
            else {
            //metric
                tempSymbol.setText("℃");
                maxTemperature.setText(String.format("%s ℃", String.valueOf(weather.getMain().getTempMax())));
                minTemperature.setText(String.format("%s ℃", String.valueOf(weather.getMain().getTempMin())));
                wind.setText(String.format("%s mps", String.valueOf(weather.getWind().getSpeed())));
            }

            clouds.setText(String.format("%s %%", String.valueOf(weather.getClouds().getAll())));



        }catch (Exception ignored){}

    }
}
