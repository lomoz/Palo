package com.example.lorcan.palo.GetFromDatabase;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.Fragments.ProfileFragment;
import com.example.lorcan.palo.MapFragment;
import com.example.lorcan.palo.MyApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class GetStatusFromDB {


    private String android_id;
    private ProfileFragment profileFragment;
    private MapFragment mapFragment;
    private EditText etStatus;
    private EditText etStatusInMap;
    public GetStatusAsyncTask getStatusAsyncTask;

    public void getStatus(final String android_id, ProfileFragment profileFragment, EditText etStatus) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        this.etStatus = etStatus;
        getStatusAsyncTask = new GetStatusAsyncTask(android_id, profileFragment, etStatus);
        getStatusAsyncTask.execute();

    }

    public void getStatus(final String android_id, MapFragment mapFragment, EditText etStatusInMap) {
        this.android_id = android_id;
        this.mapFragment = mapFragment;
        this.etStatusInMap = etStatusInMap;
        new GetStatusAsyncTask(android_id, mapFragment, etStatusInMap).execute();
    }

}
