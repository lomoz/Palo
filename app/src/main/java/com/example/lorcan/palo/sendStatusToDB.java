package com.example.lorcan.palo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.MyApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;

/**
 * Created by paul on 28.06.17.
 */

public class sendStatusToDB {

    private RequestQueue requestQueue;
    private static final String URL = "http://palo.square7.ch/setStatus.php";
    private StringRequest request;


    protected String status;
    protected Double lat;
    protected Double lng;
    protected String time;
    protected String android_id;


    public sendStatusToDB() {

    }


    public void sendStatus(final String status, final Double lat, final Double lng, final String time, final String android_id) {

        // using volley lib to create request

        this.android_id = android_id;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
        this.requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        this.request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /*try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("success")){
                        Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error" +jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                System.out.println("Antwort von PHP File über den Status: " + response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){

            // set of parameters in a hashmap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();


                hashMap.put("android_id", android_id);
                hashMap.put("status", status);
                hashMap.put("lat", String.valueOf(lat));
                hashMap.put("lng", String.valueOf(lng));
                hashMap.put("time", String.valueOf(time));

                System.out.println("DAS WAS GESENDET WIRD VOM STATUS: " + hashMap);

                return hashMap;
            }
        };

        requestQueue.add(request);


    }
}

