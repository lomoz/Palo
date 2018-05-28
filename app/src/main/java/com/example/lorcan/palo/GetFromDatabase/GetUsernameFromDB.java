package com.example.lorcan.palo.GetFromDatabase;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.Fragments.ProfileFragment;
import com.example.lorcan.palo.MainActivity;
import com.example.lorcan.palo.MyApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class GetUsernameFromDB {

    private static final String STR_URL = "http://palo.square7.ch/getUsername.php";
    private String android_id;
    private MainActivity mainActivity;
    private ProfileFragment profileFragment;
    private TextView tvUsername;
    public String name;
    private GetUsernameTask getUsernameAsyncTask;

    public GetUsernameFromDB() {

    }

    public void getUsernameFromDB(String android_id, ProfileFragment profileFragment, TextView tvUsername) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        this.tvUsername = tvUsername;
        getUsernameAsyncTask = new GetUsernameTask(android_id, profileFragment, tvUsername);
        getUsernameAsyncTask.execute();
    }

    public String getName(){
        getUsernameAsyncTask = new GetUsernameTask();
        getUsernameAsyncTask.execute();

        return name;
    }

    public void getResponseUsername(String android_id, MainActivity mainActivity) {
        this.android_id = android_id;
        this.mainActivity = mainActivity;
        getUsernameAsyncTask = new GetUsernameTask(android_id, mainActivity);
        getUsernameAsyncTask.execute();

    }
}
