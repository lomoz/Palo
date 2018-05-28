package com.example.lorcan.palo.GetFromDatabase;

import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.Fragments.ProfileFragment;
import com.example.lorcan.palo.MainActivity;
import com.example.lorcan.palo.MyApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class GetImageTask extends AsyncTask<Void, Void, Void> {

    private String android_id;
    private MainActivity mainActivity;
    private ProfileFragment profileFragment;
    private static final String STR_URL = "http://palo.square7.ch/getEncodedImage.php";


    GetImageTask(String android_id, MainActivity mainActivity) {
        this.android_id = android_id;
        this.mainActivity = mainActivity;
    }

    GetImageTask(String android_id, ProfileFragment profileFragment) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

    private void handleResponse(String response) {
        this.cancel(true);
        profileFragment.setEncodedImageAsProfileImage(response);
    }

    private void handleResponseMain(String response) {
        this.cancel(true);
        mainActivity.setEncodedImageAsNavImage(response);
    }

}
