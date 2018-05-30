package project.mobile.una.com.vaallover.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import project.mobile.una.com.vaallover.Fragment.PlaceholderFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return PlaceholderFragment.newInstance(position);
            case 1:
                return PlaceholderFragment.newInstance(position);

                default:
                    return PlaceholderFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
