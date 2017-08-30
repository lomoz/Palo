package com.example.lorcan.palo;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul on 18.07.17.
 */

public class getStatusFromDB {

    private RequestQueue requestQueue;
    private static final String URL = "http://palo.square7.ch/getStatus.php";
    private StringRequest request;
    private Double lat;
    private Double lng;
    public String responseStatus;


    //evtl anderer Kontrukstor ohne Parameter um bspw. alle Statusse zu bekommen (hier nur spezieller Status über LAT LNG erreichbar)
    public getStatusFromDB(){

    }


    public String getStatus(final Double lat, final Double lng) {
        this.lat = lat;
        this.lng = lng;
        this.requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        this.request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Antwort von PHP File: " + response);
                responseStatus = response;
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            // set of parameters in a hashmap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();

                String zugang = "yep";
                hashMap.put("lat", String.valueOf(lat));
                hashMap.put("lng", String.valueOf(lng));


                return hashMap;
            }
        };

        requestQueue.add(request);
        return responseStatus;
    }

    public String getStatusAsString(){
        //System.out.println("STATUS VON DB MIT LAT: " +  lat + " UND LNG: " + lng + " ----> " + responseStatus);
        return responseStatus;
    }

}
