package com.example.lorcan.palo;

import android.content.Context;
import android.content.pm.PackageManager;
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


public class SendEncodedImageToDB {

    private static final String STR_URL = "http://palo.square7.ch/setEncodedImageGami.php";


    public SendEncodedImageToDB() {

    }

    public void sendEncodedImage(final String encodedImage) {

        // using volley lib to create request

        TelephonyManager tManager = (TelephonyManager) MyApplicationContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        final String android_id = tManager.getDeviceId();

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println("Response from PHP script: " + response);
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
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("encodedImage", encodedImage);
                hashMap.put("android_id", android_id);

                System.out.println("THIS WILL BE SEND: " + hashMap);

                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
