package com.example.lorcan.palo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * Created by paul on 31.07.17.
 */

public class StartActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String strUrl = "http://palo.square7.ch/checkIDIsInDB.php";
    private StringRequest request;
    private String android_id;
    public String responseStatus;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        android_id = telephonyManager.getDeviceId();
        checkID(android_id);
    }

    public String checkID(final String android_id) {
        this.android_id = android_id;
        this.requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        this.request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // System.out.println("Antwort von PHP File bei getStatusFromDB: " + response);
                responseStatus = response;
                String res = response.toString().trim();

                if(res.equals("1")){
                    startMain();
                }else{
                    start();
                }
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
                System.out.println(hashMap);
                return hashMap;
            }
        };

        requestQueue.add(request);
        return responseStatus;
    }

        public void start(){
            Intent i = new Intent(MyApplicationContext.getAppContext(), LoginActivity.class);
            startActivity(i);
        }

        public void startMain() {
            Intent i = new Intent(MyApplicationContext.getAppContext(), MainActivity.class);
            startActivity(i);
        }
}
