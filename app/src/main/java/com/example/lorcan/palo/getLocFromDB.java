package com.example.lorcan.palo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

/**
 * Created by paul on 01.07.17.
 */

public class getLocFromDB {


    String result = null;
    InputStream is = null;
    StringBuilder sb;
    protected ArrayList<String> arrayListOtherUsers = new ArrayList<>();

    private RequestQueue requestQueue;
    private static final String URL = "http://palo.square7.ch/getLocation.php";
    private StringRequest request;
    private Context context;

    public getLocFromDB(Context context){
        this.context = context;
        getLocation();
    }


    public void getLocation(){

        this.requestQueue = Volley.newRequestQueue(context);
        this.request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
        }){

            // set of parameters in a hashmap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();

                String zugang = "yep";
                hashMap.put("zugang",zugang);


                return hashMap;
            }
        };

        requestQueue.add(request);

    }
    private void parseResponse(String response){
        String[] responseSplit = response.split("#"); //split at '#' --> PHP response looks like this: #email@test.com, 53.12, 12.123#email2@test.com, 42.123, 12.124 and so on
        System.out.println("RÜCKCGABE VON DB: "+ responseSplit[1]);
        for(int i=0; i < responseSplit.length; i++){
            arrayListOtherUsers.add(responseSplit[i]);
        }
    }

    protected ArrayList<String> getData(){
        if(arrayListOtherUsers.size() > 0){
            return arrayListOtherUsers;
        }else{
            return null;
        }
    }

}
