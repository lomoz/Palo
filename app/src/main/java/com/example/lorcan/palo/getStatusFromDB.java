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
    private Context context;
    private String email;

    public getStatusFromDB(Context context, String email){
        this.context = context;
        this.email = email;
        getStatus();
    }


    public void getStatus() {

        this.requestQueue = Volley.newRequestQueue(context);
        this.request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Antwort von PHP File: " + response);
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
                hashMap.put("zugang", zugang);
                hashMap.put("email", email);


                return hashMap;
            }
        };

        requestQueue.add(request);

    }

}
