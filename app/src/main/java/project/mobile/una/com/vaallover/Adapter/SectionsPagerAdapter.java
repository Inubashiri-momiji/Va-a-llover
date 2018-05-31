package project.mobile.una.com.vaallover.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import project.mobile.una.com.vaallover.Fragment.ForeignWeatherFragment;
import project.mobile.una.com.vaallover.Fragment.MainWeatherFragment;
import project.mobile.una.com.vaallover.Fragment.PlaceholderFragment;
import project.mobile.una.com.vaallover.Model.WeatherForecastContainer;
import project.mobile.una.com.vaallover.Model.WeatherCurrentContainer;
import project.mobile.una.com.vaallover.interfaces.FragmentUpdate;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public static final int TOTAL_TABS = 2;
    public WeatherCurrentContainer currentWeather;
    public WeatherForecastContainer forecastWeather;
    public Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context ctx, WeatherForecastContainer data) {
        super(fm);
        forecastWeather = data;
        mContext = ctx;
    }
    public SectionsPagerAdapter(FragmentManager fm, Context ctx, WeatherCurrentContainer data) {
        super(fm);
        currentWeather = data;
        mContext = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Fragment tab1 = new MainWeatherFragment();
                Bundle args1 = new Bundle();
                args1.putSerializable("weather", currentWeather);
                tab1.setArguments(args1);
                return tab1;
            case 1:
                return PlaceholderFragment.newInstance(position);

                default:
                    return PlaceholderFragment.newInstance(position);
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof MainWeatherFragment)
            ((FragmentUpdate) object).update(currentWeather);
        if (object instanceof ForeignWeatherFragment)
            ((FragmentUpdate) object).update(forecastWeather);

        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    public void updateData(WeatherForecastContainer data){
        forecastWeather = data;
    }

    public void updateData(WeatherCurrentContainer data){
        currentWeather = data;
    }
}
