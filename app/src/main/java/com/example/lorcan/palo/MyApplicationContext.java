package com.example.lorcan.palo;

import android.app.Application;
import android.content.Context;

public class MyApplicationContext extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplicationContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplicationContext.context;
    }
}