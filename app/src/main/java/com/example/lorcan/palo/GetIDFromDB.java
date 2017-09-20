package com.example.lorcan.palo;

import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;


/**
 * Created by Win10 Home x64 on 20.09.2017.
 */

public class GetIDFromDB extends AppCompatActivity {


    private RequestQueue requestQueue;
    private static final String URL = "http://palo.square7.ch/checkIDisInDB.php";
    private StringRequest request;
    private Context context;
    public Boolean response1;

    public GetIDFromDB(Context context, String android_id){
        this.context = context;
        checkIDIsInDB(android_id);
    }


    public void checkIDIsInDB(final String android_id){

        this.requestQueue = Volley.newRequestQueue(context);
        this.request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

                String zugang = android_id;
                hashMap.put("zugang",zugang);


                return hashMap;
            }
        };

        requestQueue.add(request);

    }
    private void parseResponse(String response){
        response1 = Boolean.parseBoolean(response);

        if(response1){
            startMapFragment();
        }else{
            start();
        }
    }
    public void start(){
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }

    public void startMapFragment(){
        MapFragment mapFragment = new MapFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                .replace(R.id.relativelayout_for_fragments,
                        mapFragment,
                        mapFragment.getTag()
                ).commit();
    }



}
