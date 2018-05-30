package project.mobile.una.com.vaallover.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.mobile.una.com.vaallover.R;

public class MainWeatherFragment extends Fragment {



    public MainWeatherFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments() != null ?
                getArguments().getInt(ARG_SECTION_NUMBER) : 0));

        return rootView;
    }
}
