package com.example.lorcan.palo;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    TextView tv_settings_text;
    Locale myLocale;
    float markerColorFloat;
    TextView tv_color;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        /*
        InputStream inputStream = getResources().openRawResource(R.raw.lorem_ipsum);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String entireFile = "";

        try {
            while ((line = bufferedReader.readLine()) != null) {
                entireFile += (line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tv_settings_text = (TextView) view.findViewById(R.id.tv_content);

        if (tv_settings_text != null) {
            tv_settings_text.setText(entireFile);
        }
        */

        Locale current = getResources().getConfiguration().locale;
        final String language = current.getLanguage();
        System.out.println("******************* Current language: " + language + " *********************");

        final RadioButton rb_german = (RadioButton) view.findViewById(R.id.rb_german);
        final RadioButton rb_english = (RadioButton) view.findViewById(R.id.rb_english);

        if (language.equals("de")) {
            rb_german.setChecked(true);
        }
        if (language.equals("en")) {
            rb_english.setChecked(true);
        }

        Button btn_change_language = (Button) view.findViewById(R.id.btn_change_language);
        btn_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rb_german.isChecked() && language != "de") {
                    setLocal("de");
                }
                if (rb_english.isChecked() && language != "en") {
                    setLocal("en");
                }
            }
        });

        tv_color = (TextView) view.findViewById(R.id.tv_color);

        final FloatingActionButton fab_azure = (FloatingActionButton) view.findViewById(R.id.fab_azure);
        fab_azure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_azure);
                markerColorFloat = BitmapDescriptorFactory.HUE_AZURE;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_blue = (FloatingActionButton) view.findViewById(R.id.fab_blue);
        fab_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_blue);
                markerColorFloat = BitmapDescriptorFactory.HUE_BLUE;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_cyan = (FloatingActionButton) view.findViewById(R.id.fab_cyan);
        fab_cyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_cyan);
                markerColorFloat = BitmapDescriptorFactory.HUE_CYAN;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_green = (FloatingActionButton) view.findViewById(R.id.fab_green);
        fab_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_green);
                markerColorFloat = BitmapDescriptorFactory.HUE_GREEN;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });
        final FloatingActionButton fab_magenta = (FloatingActionButton) view.findViewById(R.id.fab_magenta);
        fab_magenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_magenta);
                markerColorFloat = BitmapDescriptorFactory.HUE_MAGENTA;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_orange = (FloatingActionButton) view.findViewById(R.id.fab_orange);
        fab_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_orange);
                markerColorFloat = BitmapDescriptorFactory.HUE_ORANGE;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_red = (FloatingActionButton) view.findViewById(R.id.fab_red);
        fab_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_red);
                markerColorFloat = BitmapDescriptorFactory.HUE_RED;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_rose = (FloatingActionButton) view.findViewById(R.id.fab_rose);
        fab_rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_rose);
                markerColorFloat = BitmapDescriptorFactory.HUE_ROSE;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_violet = (FloatingActionButton) view.findViewById(R.id.fab_violet);
        fab_violet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_violet);
                markerColorFloat = BitmapDescriptorFactory.HUE_VIOLET;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        final FloatingActionButton fab_yellow = (FloatingActionButton) view.findViewById(R.id.fab_yellow);
        fab_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundleColor = new Bundle();

                tv_color.setText(R.string.color_yellow);
                markerColorFloat = BitmapDescriptorFactory.HUE_YELLOW;
                bundleColor.putFloat("markerColor", markerColorFloat);
                mapFragment.setArguments(bundleColor);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setLocal(String string) {

        myLocale = new Locale(string);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        startActivity(intent);

    }

}
