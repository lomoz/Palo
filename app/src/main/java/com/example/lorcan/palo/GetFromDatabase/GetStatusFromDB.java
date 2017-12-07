package com.example.lorcan.palo.GetFromDatabase;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.MapFragment;
import com.example.lorcan.palo.MyApplicationContext;
import com.example.lorcan.palo.ProfileFragment;

import java.util.HashMap;
import java.util.Map;

public class GetStatusFromDB {

    private static final String strUrl = "http://palo.square7.ch/getOneStatus.php";
    private String android_id;
    private String responseStatus;
    private ProfileFragment profileFragment;
    private MapFragment mapFragment;
    private EditText etStatus;
    private EditText etStatusInMap;

    public void getStatus(final String android_id, ProfileFragment profileFragment, EditText etStatus) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        this.etStatus = etStatus;
        GetStatusAsyncTask getStatusAsyncTask = new GetStatusAsyncTask();
        getStatusAsyncTask.execute();
    }

    public void getStatus(final String android_id, MapFragment mapFragment, EditText etStatusInMap) {
        this.android_id = android_id;
        this.mapFragment = mapFragment;
        this.etStatusInMap = etStatusInMap;
        GetStatusAsyncTask getStatusAsyncTask = new GetStatusAsyncTask();
        getStatusAsyncTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class GetStatusAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            StringRequest request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    responseStatus = response;
                    System.out.println(response);

                    if (profileFragment != null) {
                        handleResponse(response);
                    }

                    else if (mapFragment != null) {
                        handleResponseMap(response);
                    }
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
                    hashMap.put("android_id", android_id);
                    return hashMap;
                }
            };
            requestQueue.add(request);
            return null;
        }
    }

    private void handleResponse(String response){
        etStatus.setText(response);
        etStatus.setSelection(response.length());
    }

    private void handleResponseMap(String response){
        etStatusInMap.setText(response);
        etStatusInMap.setSelection(response.length());
    }
}
