package com.example.lorcan.palo.GetFromDatabase;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.MyApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GetLocationFromDB {

    private static final String STR_URL = "http://palo.square7.ch/getLocationGami.php";
    private ArrayList<String> arrayListOtherUsers = new ArrayList<>();

    public GetLocationFromDB() {
        getLocation();
    }

    private void getLocation(){

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Answer from PHP File: " + response);
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

                String access = "yep";
                hashMap.put("access",access);

                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void parseResponse(String response){
        String[] responseSplit = response.split("#"); //split at '#' --> PHP response looks like this: #email@test.com, 53.12, 12.123#email2@test.com, 42.123, 12.124 and so on
        System.out.println("RESPONSE VON DB: "+ responseSplit[1]);
        arrayListOtherUsers.addAll(Arrays.asList(responseSplit));
    }

    public ArrayList<String> getData(){
        if(arrayListOtherUsers.size() > 0) {
            return arrayListOtherUsers;
        }
        else {
            return null;
        }
    }
}
