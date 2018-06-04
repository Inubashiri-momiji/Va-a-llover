package project.mobile.una.com.vaallover.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;
import project.mobile.una.com.vaallover.Adapter.ForecastWeatherRecycleAdapter;
import project.mobile.una.com.vaallover.MainMenuActivity;
import project.mobile.una.com.vaallover.Model.List;
import project.mobile.una.com.vaallover.Model.WeatherForecastContainer;
import project.mobile.una.com.vaallover.R;
import project.mobile.una.com.vaallover.interfaces.FragmentUpdate;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ForecastWeatherFragment extends Fragment implements FragmentUpdate {

    WeatherForecastContainer weather;
    Realm realm;
    ForecastWeatherRecycleAdapter adapter;
    RealmList<List> forecast;
    Button submit;
    EditText query;
    TextView currentCity;
    String cityFormat;
    Place queryPlace;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public ForecastWeatherFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        realm = Realm.getDefaultInstance();
        forecast = new RealmList<>();
        adapter = new ForecastWeatherRecycleAdapter(this, forecast);
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
        if (realm.isClosed())
            realm = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast_main_menu, container, false);
        submit = rootView.findViewById(R.id.forecast_button);
        query = rootView.findViewById(R.id.forecast_search_criteria);
        currentCity = rootView.findViewById(R.id.forecast_city_name);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!query.getText().toString().matches("^\\s*$"))
                    ((MainMenuActivity)getActivity()).requestForecastWeather(queryPlace.getLatLng().latitude, queryPlace.getLatLng().longitude);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(Objects.requireNonNull(getActivity()));
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

            }
        });
        RecyclerView recyclerView = rootView.findViewById(R.id.weather_list);
        recyclerView.setAdapter(adapter);

        if (weather == null){
            update();
        }


        //update();
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                queryPlace = PlaceAutocomplete.getPlace(getContext(), data);
                query.setText(queryPlace.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    @Override
    public void update() {
        if (realm.isClosed())
            realm = Realm.getDefaultInstance();

        weather = realm.where(WeatherForecastContainer.class).findFirst();
        try {

           forecast.clear();
           cityFormat = weather.getCity().getName() + " ( " + weather.getCity().getCountry() + " )";
           currentCity.setText(cityFormat);
           forecast.addAll(weather.getList());
           adapter.notifyDataSetChanged();
        }catch (Exception ignored){}

    }
}

