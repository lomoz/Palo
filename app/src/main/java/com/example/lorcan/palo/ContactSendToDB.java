package com.example.lorcan.palo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Win10 Home x64 on 17.02.2018.
 */

public class ContactSendToDB {

    private static final String URL = "http://palo.square7.ch/kontakt.php";

    public  void sendContact(final String android_id, final String message, final String topic) {
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("Antwort von KONTAKT PHP File: " + response);

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
                HashMap<String, String> hashMap = new HashMap<>();


                hashMap.put("android_id", android_id);
                hashMap.put("message", message);
                hashMap.put("topic", topic);

                System.out.println("DAS WAS GESENDET WIRD1: " + hashMap);

                return hashMap;
            }
        };

        requestQueue.add(request);

    }
}
