package com.example.lorcan.palo;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    boolean german_active = true;
    Locale myLocale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

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

        Button btn_change_language = (Button) view.findViewById(R.id.btn_change_language);
        btn_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setLocal("en");

                /*
                if (german_active) {

                    setLocal("de");
                    german_active = false;
                }
                else {

                    setLocal("en");
                    german_active = true;
                }
                */
            }
        });

        return view;
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
