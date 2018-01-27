package com.example.lorcan.palo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

public class getLocFromDB {


    private ArrayList<String> arrayListOtherUsers = new ArrayList<>();

    private static final String URL = "http://palo.square7.ch/getLocation.php";
    private Context context;

    getLocFromDB(Context context){
        this.context = context;
        getLocation();
    }


    private void getLocation(){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Antwort von PHP File: " + response);

                parseResponse(response);
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

                String zugang = "yep";
                hashMap.put("zugang", zugang);


                return hashMap;
            }
        };

        requestQueue.add(request);

    }
    private void parseResponse(String response){
        String[] responseSplit = response.split("#"); //split at '#' --> PHP response looks like this: #email@test.com, 53.12, 12.123#email2@test.com, 42.123, 12.124 and so on
        System.out.println("RÜCKCGABE VON DB: "+ responseSplit[1]);
        arrayListOtherUsers.addAll(Arrays.asList(responseSplit));
    }

    public ArrayList<String> getData(){
        if(arrayListOtherUsers.size() > 0){
            return arrayListOtherUsers;
        }else{
            return null;
        }
    }

}
