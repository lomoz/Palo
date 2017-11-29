package com.example.lorcan.palo;

import android.app.Fragment;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lorcan on 09.11.17.
 */

public class GetEncodedImageFromDB {

    private RequestQueue requestQueue;
    private static final String STR_URL = "http://palo.square7.ch/getEncodedImage.php";
    private StringRequest stringRequest;
    private String android_id;
    public String responseEncodedImage;
    public ProfileFragment profileFragment;


    public GetEncodedImageFromDB() {

    }

    public void getResponseEncodedImage(String android_id, ProfileFragment profileFragment) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        new GetImageTask().execute();

    }

        public class GetImageTask extends AsyncTask<Void, Void, Void>{


            @Override
            protected Void doInBackground(Void... voids) {

                requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
                stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseEncodedImage = response;
                        handleresponse(response);
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
public void handleresponse(String response) {

    profileFragment.setEncodedImageAsImageView(response);
}

}
