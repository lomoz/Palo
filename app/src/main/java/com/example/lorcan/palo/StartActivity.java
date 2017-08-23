package com.example.lorcan.palo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

/**
 * Created by paul on 31.07.17.
 */

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void start(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
