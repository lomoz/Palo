package com.example.lorcan.palo.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDataDBHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = SQLiteDataDBHelper.class.getSimpleName();


    public SQLiteDataDBHelper(Context context) {
        super(context, "CLICKS", null, 1);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}