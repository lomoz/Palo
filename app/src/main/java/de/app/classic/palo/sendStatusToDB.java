package de.app.classic.palo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class sendStatusToDB {

    private static final String URL = "http://palo.square7.ch/setStatus.php";

    protected String status;
    protected String time;
    protected String android_id;
    protected int marker;


    public sendStatusToDB() {

    }

    public void sendStatus(final String status, final double lat, final double lng, final String time, final String android_id, final int marker) {

        OldStatus.addNewEntry(status);
        // using volley lib to create request

        this.android_id = android_id;
        this.status = status;
        this.time = time;
        this.marker = marker;
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println("Response from PHP file about the status: " + response);
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
                hashMap.put("status", status);
                hashMap.put("lat", String.valueOf(lat));
                hashMap.put("lng", String.valueOf(lng));
                hashMap.put("time", String.valueOf(time));
                hashMap.put("marker", String.valueOf(marker));

                System.out.println("WHAT IS SEND FROM THE STATUS: " + hashMap);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}

