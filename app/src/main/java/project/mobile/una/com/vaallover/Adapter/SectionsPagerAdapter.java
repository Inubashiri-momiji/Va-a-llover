package project.mobile.una.com.vaallover.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import project.mobile.una.com.vaallover.Fragment.ForecastWeatherFragment;
import project.mobile.una.com.vaallover.Fragment.MainWeatherFragment;
import project.mobile.una.com.vaallover.Fragment.PlaceholderFragment;
import project.mobile.una.com.vaallover.interfaces.FragmentUpdate;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TOTAL_TABS = 2;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainWeatherFragment();
            case 1:
                return new ForecastWeatherFragment();

                default:
                    return PlaceholderFragment.newInstance(position);
        }
    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof FragmentUpdate)
            ((FragmentUpdate) object).update();
        return super.getItemPosition(object);
    }



    @Override
    public int getCount() {
        return TOTAL_TABS;
    }


}
