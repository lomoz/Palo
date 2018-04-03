package com.example.lorcan.palo.Fragments.OptionsMenu;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.lorcan.palo.MainActivity;
import com.example.lorcan.palo.R;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    RadioButton rb_german, rb_english;
    Locale myLocale;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        rb_german = (RadioButton) view.findViewById(R.id.rb_german);
        rb_english = (RadioButton) view.findViewById(R.id.rb_english);
        Button btn_change_language = (Button) view.findViewById(R.id.btn_change_language);

        Locale current = getResources().getConfiguration().locale;
        final String language = current.getLanguage();
        System.out.println("******************* Current language: " + language + " *********************");

        switch (language) {
            case "de":
                rb_german.setChecked(true);
                break;

            case "en":
                rb_english.setChecked(true);
                break;
        }

        btn_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rb_german.isChecked() && !language.equals("de")) {
                    setLocal("de");
                }
                else if (rb_english.isChecked() && !language.equals("en")) {
                    setLocal("en");
                }
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
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = myLocale;
        resources.updateConfiguration(configuration, displayMetrics);
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        startActivity(intent);
    }
}
