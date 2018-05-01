package com.example.lorcan.palo.GetFromDatabase;

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
import com.example.lorcan.palo.MyApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class GetPointsDB {

    private static final String STR_URL = "http://palo.square7.ch/getPointsGami.php";
    public int points = 0;
    public GetPointsDB() {
    }

    public int getPoints(final String name){
        final int[] responseInt = new int[1];
        System.out.println(name);
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.trim();
                responseInt[0] = Integer.parseInt(response);

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
                String name1 = "";
                if(name.substring(name.length() - 1).equals(" ")){
                    name1 = name.substring(0, name.length() - 1);
                }else{
                    name1 = name;
                }
                hashMap.put("name", name1);
                System.out.println("Name to get points from MySQL DB: " + name1);
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
        return responseInt[0];
    }

}
