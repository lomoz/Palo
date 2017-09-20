package com.example.lorcan.palo;

import android.annotation.SuppressLint;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;


import java.util.concurrent.TimeUnit;
/**
 * Created by paul on 31.07.17.
 */

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = telephonyManager.getDeviceId();
        checkAndroidId(android_id);
    }


    public void checkAndroidId(String android_id){

        GetIDFromDB getIDFromDB = new GetIDFromDB(this, android_id);

    }

}
