package com.example.lorcan.palo.GetFromDatabase;


import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.lorcan.palo.Fragments.ProfileFragment;

import java.util.HashMap;
import java.util.Map;

public class GetStatusFromDB {

    private static final String strUrl = "http://palo.square7.ch/getOneStatus.php";
    private String android_id;
    private ProfileFragment profileFragment;
    private MapFragment mapFragment;
    private Context context;
    private EditText etStatus;
    private EditText etStatusInMap;

    public void getStatus(final String android_id, ProfileFragment profileFragment, EditText etStatus) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        this.etStatus = etStatus;
        new GetStatusAsyncTask().execute();
    }

    public void getStatus(final String android_id, MapFragment mapFragment, EditText etStatusInMap) {
        this.android_id = android_id;
        this.mapFragment = mapFragment;
        this.etStatusInMap = etStatusInMap;
        new GetStatusAsyncTask().execute();
    }

    // call in MapFragment doesn't work. Looks like context is null.
    public void getStatusViaContext(final String android_id, Context context, EditText etStatus) {
        this.android_id = android_id;
        this.context = context;
        this.etStatus = etStatus;
    }

    @SuppressLint("StaticFieldLeak")
    public class GetStatusAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            if (profileFragment != null) {
                etStatus.setEnabled(false);
            }

            else if (mapFragment != null) {
                etStatusInMap.setEnabled(false);
            }

            else if (context != null) {
                etStatus.setEnabled(false);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            StringRequest request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    System.out.println("This is what the PHP File responses: " + response);

                    if (profileFragment != null) {
                        handleResponse(response);
                    }

                    else if (mapFragment != null) {
                        handleResponseMap(response);
                    }

                    else if (context != null) {
                        handleResponseContext(response);
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

        @Override
        protected void onPostExecute(Void aVoid) {

            if (profileFragment != null) {
                etStatus.setEnabled(true);
            }

            else if (mapFragment != null) {
                etStatusInMap.setEnabled(true);
            }

            else if (context != null) {
                etStatus.setEnabled(true);
            }
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

    private void handleResponseContext(String response) {
        etStatus.setText(response);
        etStatus.setSelection(response.length());
    }
}
