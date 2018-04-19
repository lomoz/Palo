package com.example.lorcan.palo.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteDataSource {

    private static final String LOG_TAG = SQLiteDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private SQLiteDataDBHelper dbHelper;


    public SQLiteDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new SQLiteDataDBHelper(context);
    }
}
