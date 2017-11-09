package com.example.lorcan.palo;

import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul on 18.07.17.
 */

public class getStatusFromDB {

    private RequestQueue requestQueue;
    private static final String strUrl = "http://palo.square7.ch/getStatus.php";
    private StringRequest request;
    private String android_id;
    public String responseStatus;


    //evtl anderer Kontrukstor ohne Parameter um bspw. alle Statusse zu bekommen (hier nur spezieller Status über LAT LNG erreichbar)
    public getStatusFromDB(){


    }




    public String getStatus(final String android_id) {
        this.android_id = android_id;
        this.requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        this.request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // System.out.println("Antwort von PHP File bei getStatusFromDB: " + response);
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

                hashMap.put("android_id", android_id);


                return hashMap;
            }
        };

        requestQueue.add(request);
        return responseStatus;
    }

}
