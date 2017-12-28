package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.Fragments.ProfileFragment;

import java.util.HashMap;
import java.util.Map;

public class GetEncodedImageFromDB {

    private static final String STR_URL = "http://palo.square7.ch/getEncodedImage.php";
    private String android_id;
    public String responseEncodedImage;
    private ProfileFragment profileFragment;
    private MainActivity mainActivity;

    public GetEncodedImageFromDB() {

    }

    public void getResponseEncodedImage(String android_id, ProfileFragment profileFragment) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        new GetImageTask().execute();
    }

    public void getResponseEncodedImage(String android_id, MainActivity mainActivity) {
        this.android_id = android_id;
        this.mainActivity = mainActivity;
        new GetImageTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class GetImageTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    responseEncodedImage = response;

                    if (profileFragment != null) {
                        handleResponse(response);
                    }
                    else if (mainActivity != null) {
                        handleResponseMain(response);
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

            requestQueue.add(stringRequest);
            return null;
        }
    }

    public void handleResponse(String response) {
        profileFragment.setEncodedImageAsImageView(response);
    }

    public void handleResponseMain(String response) {
        mainActivity.setEncodedImageAsImageView(response);
    }
}
