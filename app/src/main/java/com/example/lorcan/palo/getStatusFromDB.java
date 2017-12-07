package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class getStatusFromDB {

    private static final String strUrl = "http://palo.square7.ch/getOneStatus.php";
    private String android_id;
    public String responseStatus;
    public ProfileFragment profileFragment;
    public MapFragment mapFragment;

    // maybe another constructor without parameters to i.e. get all status (here only one specific status is reachable via lat lng)
    public getStatusFromDB(){

    }

    public String getStatus(final String android_id, ProfileFragment profileFragment) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        AsyncTask<Void, Void, Void> gt = new getStatusTask().execute();
        return null;
    }

    public String getStatus(final String android_id, MapFragment mapFragment) {
        this.android_id = android_id;
        this.mapFragment = mapFragment;
        AsyncTask<Void, Void, Void> gt = new getStatusTask().execute();
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public class getStatusTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            StringRequest request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // System.out.println("Response from PHP File at getStatusFromDB: " + response);
                    responseStatus = response;
                    System.out.println(response);

                    if (profileFragment != null) {
                        handleResponse(response);
                    }

                    else if (mapFragment != null) {
                        handleResponseMap(response);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {

                // set of parameters in a HashMap, which will be send to the php file (server side)
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("android_id", android_id);
                    return hashMap;
                }
            };
            requestQueue.add(request);
            return null;
        }
    }

    public void handleResponse(String response){
        profileFragment.setStatusToEditText(response);
        profileFragment.etStatus.setSelection(response.length());
    }

    public void handleResponseMap(String response){
        mapFragment.setStatusToEditText(response);
        mapFragment.etStatusInMap.setSelection(response.length());
    }
}
