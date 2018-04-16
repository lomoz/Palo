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

/**
 * Created by Win10 Home x64 on 17.02.2018.
 */

public class OnClickSendToDB {

    private static final String URL = "http://palo.square7.ch/setOnClickInDBGami.php";

    public void sendBtnClick(final String android_id, final String onClickBtnText) {

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("Antwort von OnClicked PHP File: " + response);
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
                hashMap.put("onClickNumber", onClickBtnText);

                System.out.println("DAS WAS GESENDET WIRD1: " + hashMap);

                return hashMap;
            }
        };

        requestQueue.add(request);


    }
}
