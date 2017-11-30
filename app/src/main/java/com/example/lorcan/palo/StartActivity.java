package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (telephonyManager != null) {
            android_id = telephonyManager.getDeviceId();
        }
        checkID(android_id);
    }

    public void checkID(final String android_id) {
        this.android_id = android_id;
        new isIDTask().execute();
    }
        public class isIDTask extends AsyncTask<Void, Void, Void>{



            @Override
            protected Void doInBackground(Void... voids) {

                requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
                request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
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
                        restart();
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
                return null;
            }


    }

        public void start(){
            Intent i = new Intent(MyApplicationContext.getAppContext(), LoginActivity.class);
            startActivity(i);
        }

        public void startMain() {
            Intent i = new Intent(MyApplicationContext.getAppContext(), MainActivity.class);
            startActivity(i);
        }

    public void restart(){
        Intent intent = new Intent(this, StartActivity.class);
        this.startActivity(intent);
    }
}
