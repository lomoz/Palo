package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

public class StartActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String strUrl = "http://palo.square7.ch/checkIDIsInDB.php";
    private StringRequest request;
    private String android_id;
    public final int  PERMISSION_READ_PHONE_STATE = 1;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        if (telephonyManager != null) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_PHONE_STATE);


                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            android_id = telephonyManager.getDeviceId();
        }

    }

    public void checkID(final String android_id) {

        System.out.println("HELLO CHECK ID");
        this.android_id = android_id;
        new isIDTask().execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_PHONE_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    checkID(android_id);

                } else {

                   restart();
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class isIDTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println("RESPONSE START:" + response);
                    handleResponse(response);
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
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.cancel(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
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

    @Override
    protected void onResume() {
        super.onResume();
        new isIDTask().execute();
    }

    public void handleResponse(String response){
        System.out.println("RESPONSE START:" + response);
        String[] responseArr = response.split("§%");
        final String res = responseArr[0].trim();

        if(responseArr[1].length() > 0 && !responseArr[1].equals(" ")){
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Info")
                    .setMessage(responseArr[1])
                    .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(res.equals("1")){
                                startMain();
                            }else{
                                start();
                            }
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}