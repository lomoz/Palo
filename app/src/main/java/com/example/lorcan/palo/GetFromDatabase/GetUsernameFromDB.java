package com.example.lorcan.palo.GetFromDatabase;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.TextView;

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

public class GetUsernameFromDB {

    private static final String STR_URL = "http://palo.square7.ch/getUsernameGami.php";
    private String android_id;
    private MainActivity mainActivity;
    private ProfileFragment profileFragment;
    private TextView tvUsername;
    public String name;
    private GetUsernameTask getUsernameAsyncTask;

    public GetUsernameFromDB() {

    }

    public void getUsernameFromDB(String android_id, ProfileFragment profileFragment, TextView tvUsername) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        this.tvUsername = tvUsername;
        getUsernameAsyncTask = new GetUsernameTask();
        getUsernameAsyncTask.execute();
    }

    public String getName(){
        getUsernameAsyncTask = new GetUsernameTask();
        getUsernameAsyncTask.execute();

        return name;
    }

    public void getResponseUsername(String android_id, MainActivity mainActivity) {
        this.android_id = android_id;
        this.mainActivity = mainActivity;
        getUsernameAsyncTask = new GetUsernameTask();
        getUsernameAsyncTask.execute();

    }

    @SuppressLint("StaticFieldLeak")
    public class GetUsernameTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            final RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    name = response;
                    if (profileFragment != null) {
                        handleResponseProfile(response);
                        onPostExecute(null);
                    }
                    else {
                        handleResponse(response);
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

    private void handleResponse(String response) {
        getUsernameAsyncTask.cancel(true);
        mainActivity.setUsernameInNav(response);
    }

    private void handleResponseProfile(String response) {
        getUsernameAsyncTask.cancel(true);
        tvUsername.setText(response);
    }
}
