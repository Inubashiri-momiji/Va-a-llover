package project.mobile.una.com.vaallover.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.RealmList;
import project.mobile.una.com.vaallover.Fragment.ForecastWeatherFragment;
import project.mobile.una.com.vaallover.Model.List;
import project.mobile.una.com.vaallover.Model.Weather;
import project.mobile.una.com.vaallover.R;

public class ForecastWeatherRecycleAdapter extends RecyclerView.Adapter<ForecastWeatherRecycleAdapter.ViewHolder> {

    private final ForecastWeatherFragment mParentActivity;
    private HashMap<Integer, ArrayList<List>> mValues;



    public ForecastWeatherRecycleAdapter(ForecastWeatherFragment parent, HashMap<Integer, ArrayList<List>> items) {
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
        List weather3h = new List();
        List weather6h = new List();
        List weather9h = new List();
        List weather12h = new List();
        List weather15h = new List();
        List weather18h = new List();
        List weather21h = new List();
        try {
             weather3h = mValues.get(position).get(0);
             weather6h = mValues.get(position).get(1);
             weather9h = mValues.get(position).get(2);
             weather12h = mValues.get(position).get(3);
             weather15h = mValues.get(position).get(4);
             weather18h = mValues.get(position).get(5);
             weather21h = mValues.get(position).get(6);
        }catch (Exception ignored){

        }
        Date date = new Date();
        SimpleDateFormat time, day;
        time = new SimpleDateFormat("h:mm a", Locale.getDefault());
        day = new SimpleDateFormat("EEEE", Locale.getDefault());
        time.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        day.setTimeZone(TimeZone.getTimeZone("GMT-0"));

        try {
            date = new Date(weather3h.getDt() * 1000L);
            holder.day.setText(day.format(date));
            holder.hour3H.setText(time.format(date));
            holder.currentTemperature3h.setText(String.valueOf(weather3h.getMain().getTemp()));

            if(Locale.getDefault().getLanguage().equals("en")) {
                //imperial
                holder.maxTemperature3h.setText(String.format("%s °F", String.valueOf(weather3h.getMain().getTempMax())));
                holder.minTemperature3h.setText(String.format("%s °F", String.valueOf(weather3h.getMain().getTempMin())));
                holder.wind3h.setText(String.format("%s mph", String.valueOf(weather3h.getWind().getSpeed())));
            }
            else {
                //metric
                holder.maxTemperature3h.setText(String.format("%s ℃", String.valueOf(weather3h.getMain().getTempMax())));
                holder.minTemperature3h.setText(String.format("%s ℃", String.valueOf(weather3h.getMain().getTempMin())));
                holder.wind3h.setText(String.format("%s mps", String.valueOf(weather3h.getWind().getSpeed())));
            }

            holder.pressure3H.setText(String.format("%s hPa", String.valueOf(weather3h.getMain().getPressure())));
            holder.wetness3h.setText(String.format("%s %%", String.valueOf(weather3h.getMain().getHumidity())));
            holder.clouds3h.setText(String.format("%s %%", String.valueOf(weather3h.getClouds().getAll())));
            loadImage(holder.icon3H, weather3h.getWeather().get(0).getIcon());
        }catch (Exception ignored){
            holder.layoutHour3.setVisibility(View.GONE);

        }

        try {
            date = new Date(weather6h.getDt() * 1000L);
            holder.day.setText(day.format(date));
            holder.hour6H.setText(time.format(date));
            holder.currentTemperature6h.setText(String.valueOf(weather6h.getMain().getTemp()));

            if(Locale.getDefault().getLanguage().equals("en")) {
                //imperial
                holder.maxTemperature6h.setText(String.format("%s °F", String.valueOf(weather6h.getMain().getTempMax())));
                holder.minTemperature6h.setText(String.format("%s °F", String.valueOf(weather6h.getMain().getTempMin())));
                holder.wind6h.setText(String.format("%s mph", String.valueOf(weather6h.getWind().getSpeed())));
            }
            else {
                //metric
                holder.maxTemperature6h.setText(String.format("%s ℃", String.valueOf(weather6h.getMain().getTempMax())));
                holder.minTemperature6h.setText(String.format("%s ℃", String.valueOf(weather6h.getMain().getTempMin())));
                holder.wind6h.setText(String.format("%s mps", String.valueOf(weather6h.getWind().getSpeed())));
            }

            holder.pressure6H.setText(String.format("%s hPa", String.valueOf(weather6h.getMain().getPressure())));
            holder.wetness6h.setText(String.format("%s %%", String.valueOf(weather6h.getMain().getHumidity())));
            holder.clouds6h.setText(String.format("%s %%", String.valueOf(weather6h.getClouds().getAll())));
            loadImage(holder.icon6H, weather6h.getWeather().get(0).getIcon());
        }catch (Exception ignored){
            holder.layoutHour6.setVisibility(View.GONE);

        }

        try {
            date = new Date(weather9h.getDt() * 1000L);
            holder.day.setText(day.format(date));
            holder.hour9H.setText(time.format(date));
            holder.currentTemperature9h.setText(String.valueOf(weather9h.getMain().getTemp()));

            if(Locale.getDefault().getLanguage().equals("en")) {
                //imperial
                holder.maxTemperature9h.setText(String.format("%s °F", String.valueOf(weather9h.getMain().getTempMax())));
                holder.minTemperature9h.setText(String.format("%s °F", String.valueOf(weather9h.getMain().getTempMin())));
                holder.wind9h.setText(String.format("%s mph", String.valueOf(weather9h.getWind().getSpeed())));
            }
            else {
                //metric
                holder.maxTemperature9h.setText(String.format("%s ℃", String.valueOf(weather9h.getMain().getTempMax())));
                holder.minTemperature9h.setText(String.format("%s ℃", String.valueOf(weather9h.getMain().getTempMin())));
                holder.wind9h.setText(String.format("%s mps", String.valueOf(weather9h.getWind().getSpeed())));
            }

            holder.pressure9H.setText(String.format("%s hPa", String.valueOf(weather9h.getMain().getPressure())));
            holder.wetness9h.setText(String.format("%s %%", String.valueOf(weather9h.getMain().getHumidity())));
            holder.clouds9h.setText(String.format("%s %%", String.valueOf(weather9h.getClouds().getAll())));
            loadImage(holder.icon9H, weather9h.getWeather().get(0).getIcon());
        }catch (Exception ignored){
            holder.layoutHour9.setVisibility(View.GONE);

        }

        try {
            date = new Date(weather12h.getDt() * 1000L);
            holder.day.setText(day.format(date));
            holder.hour12H.setText(time.format(date));
            holder.currentTemperature12h.setText(String.valueOf(weather12h.getMain().getTemp()));

            if(Locale.getDefault().getLanguage().equals("en")) {
                //imperial
                holder.maxTemperature12h.setText(String.format("%s °F", String.valueOf(weather12h.getMain().getTempMax())));
                holder.minTemperature12h.setText(String.format("%s °F", String.valueOf(weather12h.getMain().getTempMin())));
                holder.wind12h.setText(String.format("%s mph", String.valueOf(weather12h.getWind().getSpeed())));
            }
            else {
                //metric
                holder.maxTemperature12h.setText(String.format("%s ℃", String.valueOf(weather12h.getMain().getTempMax())));
                holder.minTemperature12h.setText(String.format("%s ℃", String.valueOf(weather12h.getMain().getTempMin())));
                holder.wind12h.setText(String.format("%s mps", String.valueOf(weather12h.getWind().getSpeed())));
            }

            holder.pressure12H.setText(String.format("%s hPa", String.valueOf(weather12h.getMain().getPressure())));
            holder.wetness12h.setText(String.format("%s %%", String.valueOf(weather12h.getMain().getHumidity())));
            holder.clouds12h.setText(String.format("%s %%", String.valueOf(weather12h.getClouds().getAll())));
            loadImage(holder.icon12H, weather12h.getWeather().get(0).getIcon());
        }catch (Exception ignored){
            holder.layoutHour12.setVisibility(View.GONE);

        }

        try {
            date = new Date(weather15h.getDt() * 1000L);
            holder.day.setText(day.format(date));
            holder.hour15H.setText(time.format(date));
            holder.currentTemperature15h.setText(String.valueOf(weather15h.getMain().getTemp()));

            if(Locale.getDefault().getLanguage().equals("en")) {
                //imperial
                holder.maxTemperature15h.setText(String.format("%s °F", String.valueOf(weather15h.getMain().getTempMax())));
                holder.minTemperature15h.setText(String.format("%s °F", String.valueOf(weather15h.getMain().getTempMin())));
                holder.wind15h.setText(String.format("%s mph", String.valueOf(weather15h.getWind().getSpeed())));
            }
            else {
                //metric
                holder.maxTemperature15h.setText(String.format("%s ℃", String.valueOf(weather15h.getMain().getTempMax())));
                holder.minTemperature15h.setText(String.format("%s ℃", String.valueOf(weather15h.getMain().getTempMin())));
                holder.wind15h.setText(String.format("%s mps", String.valueOf(weather15h.getWind().getSpeed())));
            }

            holder.pressure15H.setText(String.format("%s hPa", String.valueOf(weather15h.getMain().getPressure())));
            holder.wetness15h.setText(String.format("%s %%", String.valueOf(weather15h.getMain().getHumidity())));
            holder.clouds15h.setText(String.format("%s %%", String.valueOf(weather15h.getClouds().getAll())));
            loadImage(holder.icon15H, weather15h.getWeather().get(0).getIcon());
        }catch (Exception ignored){
            holder.layoutHour15.setVisibility(View.GONE);

        }

        try {
            date = new Date(weather18h.getDt() * 1000L);
            holder.day.setText(day.format(date));
            holder.hour18H.setText(time.format(date));
            holder.currentTemperature18h.setText(String.valueOf(weather18h.getMain().getTemp()));

            if(Locale.getDefault().getLanguage().equals("en")) {
                //imperial
                holder.maxTemperature18h.setText(String.format("%s °F", String.valueOf(weather18h.getMain().getTempMax())));
                holder.minTemperature18h.setText(String.format("%s °F", String.valueOf(weather18h.getMain().getTempMin())));
                holder.wind18h.setText(String.format("%s mph", String.valueOf(weather18h.getWind().getSpeed())));
            }
            else {
                //metric
                holder.maxTemperature18h.setText(String.format("%s ℃", String.valueOf(weather18h.getMain().getTempMax())));
                holder.minTemperature18h.setText(String.format("%s ℃", String.valueOf(weather18h.getMain().getTempMin())));
                holder.wind18h.setText(String.format("%s mps", String.valueOf(weather18h.getWind().getSpeed())));
            }

            holder.pressure18H.setText(String.format("%s hPa", String.valueOf(weather18h.getMain().getPressure())));
            holder.wetness18h.setText(String.format("%s %%", String.valueOf(weather18h.getMain().getHumidity())));
            holder.clouds18h.setText(String.format("%s %%", String.valueOf(weather18h.getClouds().getAll())));
            loadImage(holder.icon18H, weather18h.getWeather().get(0).getIcon());
        }catch (Exception ignored){
            holder.layoutHour18.setVisibility(View.GONE);

        }

        try {
            date = new Date(weather21h.getDt() * 1000L);
            holder.day.setText(day.format(date));
            holder.hour21H.setText(time.format(date));
            holder.currentTemperature21h.setText(String.valueOf(weather21h.getMain().getTemp()));

            if(Locale.getDefault().getLanguage().equals("en")) {
                //imperial
                holder.maxTemperature21h.setText(String.format("%s °F", String.valueOf(weather21h.getMain().getTempMax())));
                holder.minTemperature21h.setText(String.format("%s °F", String.valueOf(weather21h.getMain().getTempMin())));
                holder.wind21h.setText(String.format("%s mph", String.valueOf(weather21h.getWind().getSpeed())));
            }
            else {
                //metric
                holder.maxTemperature21h.setText(String.format("%s ℃", String.valueOf(weather21h.getMain().getTempMax())));
                holder.minTemperature21h.setText(String.format("%s ℃", String.valueOf(weather21h.getMain().getTempMin())));
                holder.wind21h.setText(String.format("%s mps", String.valueOf(weather21h.getWind().getSpeed())));
            }

            holder.pressure21H.setText(String.format("%s hPa", String.valueOf(weather21h.getMain().getPressure())));
            holder.wetness21h.setText(String.format("%s %%", String.valueOf(weather21h.getMain().getHumidity())));
            holder.clouds21h.setText(String.format("%s %%", String.valueOf(weather21h.getClouds().getAll())));
            loadImage(holder.icon21H, weather21h.getWeather().get(0).getIcon());
        }catch (Exception ignored){
            holder.layoutHour21.setVisibility(View.GONE);
        }

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
        final TextView hour3H, pressure3H,  currentTemperature3h, maxTemperature3h, minTemperature3h, wetness3h, wind3h, clouds3h;
        final TextView hour6H, pressure6H,  currentTemperature6h, maxTemperature6h, minTemperature6h, wetness6h, wind6h, clouds6h;
        final TextView hour9H, pressure9H,  currentTemperature9h, maxTemperature9h, minTemperature9h, wetness9h, wind9h, clouds9h;
        final TextView hour12H, pressure12H, currentTemperature12h, maxTemperature12h, minTemperature12h, wetness12h, wind12h, clouds12h;
        final TextView hour15H, pressure15H,  currentTemperature15h, maxTemperature15h, minTemperature15h, wetness15h, wind15h, clouds15h;
        final TextView hour18H, pressure18H,  currentTemperature18h, maxTemperature18h, minTemperature18h, wetness18h, wind18h, clouds18h;
        final TextView hour21H, pressure21H,  currentTemperature21h, maxTemperature21h, minTemperature21h, wetness21h, wind21h, clouds21h;
        final ImageView icon3H, icon6H, icon9H, icon12H, icon15H, icon18H, icon21H;
        final TextView day;
        final LinearLayout layoutHour3, layoutHour6, layoutHour9, layoutHour12, layoutHour15, layoutHour18, layoutHour21;
        ViewHolder(View view) {
            super(view);

            day = view.findViewById(R.id.forecast_Date);

            layoutHour3 = view.findViewById(R.id.forecast_3_hour_layout);
            layoutHour6 = view.findViewById(R.id.forecast_6_hour_layout);
            layoutHour9 = view.findViewById(R.id.forecast_9_hour_layout);
            layoutHour12 = view.findViewById(R.id.forecast_12_hour_layout);
            layoutHour15 = view.findViewById(R.id.forecast_15_hour_layout);
            layoutHour18 = view.findViewById(R.id.forecast_18_hour_layout);
            layoutHour21 = view.findViewById(R.id.forecast_21_hour_layout);

            hour3H = view.findViewById(R.id.forecast_3_hour_hour);
            pressure3H = view.findViewById(R.id.forecast_3_hour_pressure);
            icon3H = view.findViewById(R.id.forecast_3_hour_icon);
            currentTemperature3h = view.findViewById(R.id.forecast_3_hour_current_temperature);
            maxTemperature3h = view.findViewById(R.id.forecast_3_hour_max_temperature);
            minTemperature3h = view.findViewById(R.id.forecast_3_hour_min_temperature);
            wetness3h = view.findViewById(R.id.forecast_3_hour_wetness);
            wind3h = view.findViewById(R.id.forecast_3_hour_wind);
            clouds3h = view.findViewById(R.id.forecast_3_hour_clouds);

            hour6H = view.findViewById(R.id.forecast_6_hour_hour);
            pressure6H = view.findViewById(R.id.forecast_6_hour_pressure);
            icon6H = view.findViewById(R.id.forecast_6_hour_icon);
            currentTemperature6h = view.findViewById(R.id.forecast_6_hour_current_temperature);
            maxTemperature6h = view.findViewById(R.id.forecast_6_hour_max_temperature);
            minTemperature6h = view.findViewById(R.id.forecast_6_hour_min_temperature);
            wetness6h = view.findViewById(R.id.forecast_6_hour_wetness);
            wind6h = view.findViewById(R.id.forecast_6_hour_wind);
            clouds6h = view.findViewById(R.id.forecast_6_hour_clouds);

            hour9H = view.findViewById(R.id.forecast_9_hour_hour);
            pressure9H = view.findViewById(R.id.forecast_9_hour_pressure);
            icon9H = view.findViewById(R.id.forecast_9_hour_icon);
            currentTemperature9h = view.findViewById(R.id.forecast_9_hour_current_temperature);
            maxTemperature9h = view.findViewById(R.id.forecast_9_hour_max_temperature);
            minTemperature9h = view.findViewById(R.id.forecast_9_hour_min_temperature);
            wetness9h = view.findViewById(R.id.forecast_9_hour_wetness);
            wind9h = view.findViewById(R.id.forecast_9_hour_wind);
            clouds9h = view.findViewById(R.id.forecast_9_hour_clouds);

            hour12H = view.findViewById(R.id.forecast_12_hour_hour);
            pressure12H = view.findViewById(R.id.forecast_12_hour_pressure);
            icon12H = view.findViewById(R.id.forecast_12_hour_icon);
            currentTemperature12h = view.findViewById(R.id.forecast_12_hour_current_temperature);
            maxTemperature12h = view.findViewById(R.id.forecast_12_hour_max_temperature);
            minTemperature12h = view.findViewById(R.id.forecast_12_hour_min_temperature);
            wetness12h = view.findViewById(R.id.forecast_12_hour_wetness);
            wind12h = view.findViewById(R.id.forecast_12_hour_wind);
            clouds12h = view.findViewById(R.id.forecast_12_hour_clouds);

            hour15H = view.findViewById(R.id.forecast_15_hour_hour);
            pressure15H = view.findViewById(R.id.forecast_15_hour_pressure);
            icon15H = view.findViewById(R.id.forecast_15_hour_icon);
            currentTemperature15h = view.findViewById(R.id.forecast_15_hour_current_temperature);
            maxTemperature15h = view.findViewById(R.id.forecast_15_hour_max_temperature);
            minTemperature15h = view.findViewById(R.id.forecast_15_hour_min_temperature);
            wetness15h = view.findViewById(R.id.forecast_15_hour_wetness);
            wind15h = view.findViewById(R.id.forecast_15_hour_wind);
            clouds15h = view.findViewById(R.id.forecast_15_hour_clouds);

            hour18H = view.findViewById(R.id.forecast_18_hour_hour);
            pressure18H = view.findViewById(R.id.forecast_18_hour_pressure);
            icon18H = view.findViewById(R.id.forecast_18_hour_icon);
            currentTemperature18h = view.findViewById(R.id.forecast_18_hour_current_temperature);
            maxTemperature18h = view.findViewById(R.id.forecast_18_hour_max_temperature);
            minTemperature18h = view.findViewById(R.id.forecast_18_hour_min_temperature);
            wetness18h = view.findViewById(R.id.forecast_18_hour_wetness);
            wind18h = view.findViewById(R.id.forecast_18_hour_wind);
            clouds18h = view.findViewById(R.id.forecast_18_hour_clouds);

            hour21H = view.findViewById(R.id.forecast_21_hour_hour);
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


/*
		                                                       ██
		                                                      █░█
		            █                     ████              ██░░██
		            ███                  █▓▓▓▓███          █▒░░░░██
		            █░░█               ██▓▓▓▓▓▓▓███       █▒░░░░░░█
		           ██░░░██            █▓▓▓▓▓▓▓▓▓▓▓▓██    █▒░░░░░░░░█
		          ██░░░░░░██        ██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██ █▒░░░░░░░░░██
		          █░░░░░░░░░██     █▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██▒░░░░░░░░░░░█
		         █░░░░░░░░░░░░██  █▓▓▓▓▓▓████▓▓▓▓▓▓▓▓▓█▒░░░░░░░█▒░░░░█      desarrollado por
		         █░░░░░░░░░░░░░███▓▓▓████░░░░█████▓▓▓█▒░░░░░░░░█▒░░░░█     Claudio Umaña Arias
		         █░░░░░░░░░░░░░░░████░░░░░░░░░░░░░███▒░░░░░░░░█▒▒░░░░██
		        ██░░█░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█▒▒▒░░░░█
		        █░░░█░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█████░░░██
		        █░░▒██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█     █░░░█
		        █░▒█  █░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█     █░░░█
		        █░▒█ █░░░█░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█    █▒░░░██
		       ██░█ █░░░█░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█   █▒▒░░░░█
		       █░█ █░░░█░░░░░░░░░░░░░░░░░░░░░░░█░░░░░░░░░░░░░░█   █▒░░░░█
		       █░░█ █░█░░░░░░░░░░░░░░░░░░░░░░░░░█░░░░░░░░░█░░░░█  █░░░░░█
		       █░█ █░░█░░░░░░░█░░░░░░░░░░░░░░░░░█░░░░░░░░░░█░░█   █░░░░░███
		       █░██░░█░░░░░░░░█░░░░░░░░░░░░░░░░░░█░░░░░░░░░█░█    █░░░░░████   ██
		       █░█ ██░░░░░░░░░█░░░░░░░░░░░░░░░░░░█░░░░░░░░░█░█    █░░░░░██ ██ ██ █
		       █░▒█ █░░█░░░░░░█░░░░░░░░░░░░░░░░░░░█░░░░░░░░░██▄   █░░░░░██ ███    █
		       █░█ █░░░█░░░░░░█░░░░░░░░░░░░░░░░░░▒█░░░░░░░░░░█▒███▒░░░░░█████     █
		      ██░░██░░░█░░░░░░█░░░░░░░░░░░░░░░░░░▒█░░░░░░░░░░█▒░░░░░░░░░░██       ██
		     ███░░█░░░█░░░░░░▒█░░░░░░░░░░░░░░░░░░▒▒█░░░░░░░░░█▒░░░░░░░░░░█         ██
		     █ █░░█░░░█░░░░░░▒█░░░░░░░░░░░░░░░░░░▒▒█░░░░░░░▒▒█▒░░░░░░░░░░░█         █
		   ███████░░░░█░░░░░░▒█░░░░░░░░░░░░░░░░░▒▒▒▒█░░░░░▒▒▒█▒░░░░░░░░░░░██         █
		  ██ ██ ██░░░░░█░░░░░▒▒█░░░░░░░░░░░░░░░░▒▒▒█ █░░▒▒▒▒█▒░░░░░░░░░░░░░█         █
		  █     █░░░░░░█░░░░░▒▒█░░░░░░░░░░░░░░░░▒▒▒█ █░░▒▒▒▓█▒░░░░░░░░░░░░░██        ██
		 ██     █░░░░░░█░░░░░▒▒█░░▒░░░░░░░░░░░▒▒▒▒██ █▒▒▒▒▒█▒░░░░░░░░░░░░░░▒█        ██
		 █      █░░░░░░█░░░░░▒▒▒█░▒▒▒▒▒░░░░░░▒▒▒██    █▒▒▒▒█▒░░░░░░░░░░░░░░▒██     ███
		 █     █░░░░░░▒██░░░░▒▒█ █▒▒▒▒▒▒▒▒▒▒▒▒██      █████▒░░░░░░░░░░░░░░░▒▒█    ███
		  █    █░░░░░░▒▒█░░▒▒▒█   ███▒▒▒▒▒▒▒██  ████████████░░░░░░░░░░░░░░░▒▒██  █
		 ██    █░░░░░░▒▒██░▒▒▒█      ███▒▒██  ████████▓▓▓▓▓█░░░░░░░░░░░░░░░▒▒▒███
		 █    █░░░░░░░▒▒▒█▒▒▒▒█         ██        █▓▓▓▓▓▓▓▓█░░░░░░░░░░░░░░░▒▒▒██
		 █    █░░░░░░░▒▒▒▒█▒▒▒█  █████            █▓▓▓▓▓▓▓▓█░░░░░░░░░░░░░░░▒▒▒██
		 █    █░░░░░░░▒▒▒▒▒██▒█████▓▓▓█           █▓▓▓▓▓▓▓▓█░░░░░░░░░░░░░░▒▒▒▒▒██
		 ██   █░░░░░░░▒▒▒▒▒████ █▓▓▓▓▓█           █▓▓▓▓▓▓▓▓█░░░░░░░░░░░░░▒▒▒▒▒▒██
		  █████░░░░░░▒▒▒▒▒▒█     █▓▓▓▓█            █▓▓▓▓▓▓██░░░░░░░░░░░░▒▒▒▒▒▒▒██
		    ██░░░░░░░▒▒▒▒▒▒▒█    █▓▓▓▓█             ██▓▓████░░░░░░░░░░░▒▒▒▒▒▒▒▒██
		    ██░░░░░░░▒▒▒▒▒▒█      █▓▓▓█               ██   █░░░░░░░░░░░▒▒▒▒▒▒▒▒▒█
		    ██░░░░░░░▒▒▒▒▒▒█      █▓▓█                    █░░░░░░░░░░░▒▒▒▒▒▒▒▒▒▒█
		    ██░░░░░░░▒▒▒▒▒█        ██                    █░░░░░░░░░░░░▒▒▒▒▒▒████
		    ██░░░░░░░▒▒▒▒▒█                    ▄▄▄      █░▒▒█░░░░░░░░▒▒▒▒▒▒█
		    ██░░░░░░░▒▒▒▒▒█                   ▀ ▄ ▀    ██████░░░░░░░▒▒▒▒▒▒█
		    ██░░░░░░▒▒▒▒▒▒█                ▄   ▄▀           █░░░░░░▒▒▒▒▒▒█
		    ██░░░░░▒▒▒▒▒▒▒▒█          ▄   ▄▀▄▄▄▀            █░░░░░▒▒▒▒▒▒█
		    ██░░░░░▒▒▒▒▒▒▒▒▒█         ▀▄▄▄▀                █░░░░▒▒▒▒▒▒██
		    ██░░░░░▒▒▒▒▒▒▒▒▒▒██                           ██░░░▒▒▒▒▒▒██
		    ██░░░░░▒▒▒▒▒▒▒▒▒▒▒▒██                      ███ █░▒▒▒▒▒▒██
		    ██░░░░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒████             █████   █▒▒▒▒▒████
		     █░░░░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█████████████        █▒██████
		     ██░░█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒███                  ██  █
		       ██ ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒███    ███              █
		            ███▒▒▒▒▒▒▒▒▒▒▒▒▒██ █   █   ██          ██
		              ███████████████   █ █      █        ██
		                   █████████     █        █    ███
		                                 █        █ ███
		                                  ██     ████
		                                    ██████
* */