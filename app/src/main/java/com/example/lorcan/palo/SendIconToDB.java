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

public class SendIconToDB {

    private static final String URL = "http://palo.square7.ch/setNewIconGami.php";
    private String iconNum;
    private String android_id;
    public SendIconToDB() {

    }

    public void sendStatus(final String iconNum, final String android_id) {


        this.iconNum = iconNum;
        this.android_id = android_id;
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println("Response setIcon: " + response);
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

                hashMap.put("iconNum", iconNum);
                hashMap.put("android_id", android_id);
                System.out.println("WHAT IS SEND FROM THE SetIcon: " + hashMap);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
