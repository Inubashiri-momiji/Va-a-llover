package project.mobile.una.com.vaallover.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmList;
import project.mobile.una.com.vaallover.Fragment.ForecastWeatherFragment;
import project.mobile.una.com.vaallover.Model.List;
import project.mobile.una.com.vaallover.Model.Weather;
import project.mobile.una.com.vaallover.R;

public class ForecastWeatherRecycleAdapter extends RecyclerView.Adapter<ForecastWeatherRecycleAdapter.ViewHolder> {

    private final ForecastWeatherFragment mParentActivity;
    private RealmList<List> mValues;
    private HashMap<Integer, ArrayList<List>> data;



    public ForecastWeatherRecycleAdapter(ForecastWeatherFragment parent, RealmList<List> items) {
        mValues = items;
        mParentActivity = parent;
        data = new HashMap<>();
        processData();
    }

    public void processData(){
        ArrayList<List> result = new ArrayList<>();
        String dia;
        int dayCounter = 0;

        for (int i = 0; i<mValues.size(); i++) {
            //Integer dia, ArrayList horas
            dia = mValues.get(0).getDtTxt();

            if (dia.contains("03:00:00"))
                result = new ArrayList<>();

            result.add(mValues.get(0));

            if (dia.contains("21:00:00"))
                data.put(dayCounter++, result);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Weather wea = mValues.get(position).getWeather().get(0);
        List info = mValues.get(position);



        holder.pressure3H.setText(String.valueOf(info.getMain().getPressure()));
        holder.currentTemperature3h.setText(String.valueOf(mValues.get(position).getMain().getTemp()));
        holder.maxTemperature3h.setText(String.valueOf(mValues.get(position).getMain().getTempMax()));
        holder.minTemperature3h.setText(String.valueOf(mValues.get(position).getMain().getTempMin()));
        holder.wetness3h.setText(String.valueOf(mValues.get(position).getMain().getHumidity()));
        holder.wind3h.setText(String.valueOf(mValues.get(position).getWind().getSpeed()));
        holder.clouds3h.setText(String.valueOf(mValues.get(position).getClouds().getAll()));

        holder.itemView.setTag(mValues.get(position));
    }

    private void loadImage(ImageView imageContainer, String type){
        String url = "http://openweathermap.org/img/w/" + type + ".png";
        Picasso.get().load(url).into(imageContainer);
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView pressure3H,  currentTemperature3h, maxTemperature3h, minTemperature3h, wetness3h, wind3h, clouds3h;
        final TextView pressure6H,  currentTemperature6h, maxTemperature6h, minTemperature6h, wetness6h, wind6h, clouds6h;
        final TextView pressure9H,  currentTemperature9h, maxTemperature9h, minTemperature9h, wetness9h, wind9h, clouds9h;
        final TextView pressure12H, currentTemperature12h, maxTemperature12h, minTemperature12h, wetness12h, wind12h, clouds12h;
        final TextView pressure15H,  currentTemperature15h, maxTemperature15h, minTemperature15h, wetness15h, wind15h, clouds15h;
        final TextView pressure18H,  currentTemperature18h, maxTemperature18h, minTemperature18h, wetness18h, wind18h, clouds18h;
        final TextView pressure21H,  currentTemperature21h, maxTemperature21h, minTemperature21h, wetness21h, wind21h, clouds21h;
        final ImageView icon3H, icon6H, icon9H, icon12H, icon15H, icon18H, icon21H;
        final TextView day;

        ViewHolder(View view) {
            super(view);

            day = view.findViewById(R.id.forecast_Date);

            pressure3H = view.findViewById(R.id.forecast_3_hour_pressure);
            icon3H = view.findViewById(R.id.forecast_3_hour_icon);
            currentTemperature3h = view.findViewById(R.id.forecast_3_hour_current_temperature);
            maxTemperature3h = view.findViewById(R.id.forecast_3_hour_max_temperature);
            minTemperature3h = view.findViewById(R.id.forecast_3_hour_min_temperature);
            wetness3h = view.findViewById(R.id.forecast_3_hour_wetness);
            wind3h = view.findViewById(R.id.forecast_3_hour_wind);
            clouds3h = view.findViewById(R.id.forecast_3_hour_clouds);

            pressure6H = view.findViewById(R.id.forecast_6_hour_pressure);
            icon6H = view.findViewById(R.id.forecast_6_hour_icon);
            currentTemperature6h = view.findViewById(R.id.forecast_6_hour_current_temperature);
            maxTemperature6h = view.findViewById(R.id.forecast_6_hour_max_temperature);
            minTemperature6h = view.findViewById(R.id.forecast_6_hour_min_temperature);
            wetness6h = view.findViewById(R.id.forecast_6_hour_wetness);
            wind6h = view.findViewById(R.id.forecast_6_hour_wind);
            clouds6h = view.findViewById(R.id.forecast_6_hour_clouds);

            pressure9H = view.findViewById(R.id.forecast_9_hour_pressure);
            icon9H = view.findViewById(R.id.forecast_9_hour_icon);
            currentTemperature9h = view.findViewById(R.id.forecast_9_hour_current_temperature);
            maxTemperature9h = view.findViewById(R.id.forecast_9_hour_max_temperature);
            minTemperature9h = view.findViewById(R.id.forecast_9_hour_min_temperature);
            wetness9h = view.findViewById(R.id.forecast_9_hour_wetness);
            wind9h = view.findViewById(R.id.forecast_9_hour_wind);
            clouds9h = view.findViewById(R.id.forecast_9_hour_clouds);

            pressure12H = view.findViewById(R.id.forecast_12_hour_pressure);
            icon12H = view.findViewById(R.id.forecast_12_hour_icon);
            currentTemperature12h = view.findViewById(R.id.forecast_12_hour_current_temperature);
            maxTemperature12h = view.findViewById(R.id.forecast_12_hour_max_temperature);
            minTemperature12h = view.findViewById(R.id.forecast_12_hour_min_temperature);
            wetness12h = view.findViewById(R.id.forecast_12_hour_wetness);
            wind12h = view.findViewById(R.id.forecast_12_hour_wind);
            clouds12h = view.findViewById(R.id.forecast_12_hour_clouds);

            pressure15H = view.findViewById(R.id.forecast_15_hour_pressure);
            icon15H = view.findViewById(R.id.forecast_15_hour_icon);
            currentTemperature15h = view.findViewById(R.id.forecast_15_hour_current_temperature);
            maxTemperature15h = view.findViewById(R.id.forecast_15_hour_max_temperature);
            minTemperature15h = view.findViewById(R.id.forecast_15_hour_min_temperature);
            wetness15h = view.findViewById(R.id.forecast_15_hour_wetness);
            wind15h = view.findViewById(R.id.forecast_15_hour_wind);
            clouds15h = view.findViewById(R.id.forecast_15_hour_clouds);

            pressure18H = view.findViewById(R.id.forecast_18_hour_pressure);
            icon18H = view.findViewById(R.id.forecast_18_hour_icon);
            currentTemperature18h = view.findViewById(R.id.forecast_18_hour_current_temperature);
            maxTemperature18h = view.findViewById(R.id.forecast_18_hour_max_temperature);
            minTemperature18h = view.findViewById(R.id.forecast_18_hour_min_temperature);
            wetness18h = view.findViewById(R.id.forecast_18_hour_wetness);
            wind18h = view.findViewById(R.id.forecast_18_hour_wind);
            clouds18h = view.findViewById(R.id.forecast_18_hour_clouds);

            pressure21H = view.findViewById(R.id.forecast_21_hour_pressure);
            icon21H = view.findViewById(R.id.forecast_21_hour_icon);
            currentTemperature21h = view.findViewById(R.id.forecast_21_hour_current_temperature);
            maxTemperature21h = view.findViewById(R.id.forecast_21_hour_max_temperature);
            minTemperature21h = view.findViewById(R.id.forecast_21_hour_min_temperature);
            wetness21h = view.findViewById(R.id.forecast_21_hour_wetness);
            wind21h = view.findViewById(R.id.forecast_21_hour_wind);
            clouds21h = view.findViewById(R.id.forecast_21_hour_clouds);

        }
    }



}