package com.example.lorcan.palo;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SetPointsDB {

    private static final String STR_URL = "http://palo.square7.ch/setPointsGami.php";


    public void setPoints(final int points){

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){

            // set of parameters in a HashMap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();

                TelephonyManager telephonyManager = (TelephonyManager) MyApplicationContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
                ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE);
                final String android_id = telephonyManager.getDeviceId();
                hashMap.put("android_id", android_id);
                hashMap.put("points", String.valueOf(points));

                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void parseResponse(String response){
        System.out.println(response);
    }
}
