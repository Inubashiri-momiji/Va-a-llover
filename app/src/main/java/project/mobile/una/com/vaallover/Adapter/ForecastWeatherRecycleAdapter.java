package project.mobile.una.com.vaallover.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmList;
import project.mobile.una.com.vaallover.Fragment.ForecastWeatherFragment;
import project.mobile.una.com.vaallover.Model.List;
import project.mobile.una.com.vaallover.Model.Weather;
import project.mobile.una.com.vaallover.R;

public class ForecastWeatherRecycleAdapter extends RecyclerView.Adapter<ForecastWeatherRecycleAdapter.ViewHolder> {

    private final ForecastWeatherFragment mParentActivity;
    private RealmList<List> mValues;

    private final OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };

    public ForecastWeatherRecycleAdapter(ForecastWeatherFragment parent, RealmList<List> items) {
        mValues = items;
        mParentActivity = parent;
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

        holder.pressure.setText(String.valueOf(info.getMain().getPressure()));
        holder.icon.setText(mValues.get(position).getDtTxt());
        holder.currentTemperature.setText(String.valueOf(mValues.get(position).getMain().getTemp()));
        holder.maxTemperature.setText(String.valueOf(mValues.get(position).getMain().getTempMax()));
        holder.minTemperature.setText(String.valueOf(mValues.get(position).getMain().getTempMin()));
        holder.wetness.setText(String.valueOf(mValues.get(position).getMain().getHumidity()));
        holder.wind.setText(String.valueOf(mValues.get(position).getWind().getSpeed()));
        holder.clouds.setText(String.valueOf(mValues.get(position).getClouds().getAll()));

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView pressure, icon, currentTemperature, maxTemperature, minTemperature, wetness, wind, clouds;

        ViewHolder(View view) {
            super(view);

            pressure = view.findViewById(R.id.forecast_pressure);
            icon = view.findViewById(R.id.forecast_icon);
            currentTemperature = view.findViewById(R.id.forecast_current_temperature);
            maxTemperature = view.findViewById(R.id.forecast_max_temperature);
            minTemperature = view.findViewById(R.id.forecast_min_temperature);
            wetness = view.findViewById(R.id.forecast_wetness);
            wind = view.findViewById(R.id.forecast_wind);
            clouds = view.findViewById(R.id.forecast_clouds);

        }
    }



}