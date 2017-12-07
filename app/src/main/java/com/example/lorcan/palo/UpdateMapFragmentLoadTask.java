package com.example.lorcan.palo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Win10 Home x64 on 30.11.2017.
 */

public class UpdateMapFragmentLoadTask extends Fragment {

    private RequestQueue requestQueue;
    private static final String strUrl = "http://palo.square7.ch/getStatus.php";
    private StringRequest request;
    public UpdateMapFragment updateMapFragment;
    MapFragment mapFragment;
    Bundle bundle1 = new Bundle();

    public double[] currLoc;

    public void loadData(double[] currLoc, UpdateMapFragment updateMapFragment, MapFragment mapFragment){
        this.currLoc = currLoc;
        this.updateMapFragment = updateMapFragment;
        this.mapFragment = mapFragment;
        new LoadDataTask().execute();
    }

    public class LoadDataTask extends AsyncTask<Void, Void, Void>{




        @Override
        protected Void doInBackground(Void... voids) {

            requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        handleResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
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

                    hashMap.put("zugang", "zugang");


                    return hashMap;
                }
            };
            requestQueue.add(request);
            return null;
        }

    }


    public void handleResponse(String response) throws JSONException {

        ArrayList<String> args = new ArrayList();/*

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("User");
        args.add(String.valueOf(currLoc[0]));
        args.add(String.valueOf(currLoc[1]));
        System.out.println("RESPONSE: " + response);
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jObjStatus;
            jObjStatus = new JSONObject(jsonArray.getString(i));
            args.add(jObjStatus.getString("Status"));
            args.add(jObjStatus.getString("Lat"));
            args.add(jObjStatus.getString("Lng"));
            args.add(jObjStatus.getString("Zeit"));
            args.add(jObjStatus.getString("Nickname"));

        }

        */

        String[] newString = response.split("#");
        args.add(String.valueOf(currLoc[0]));
        args.add(String.valueOf(currLoc[1]));

        for (int i = 0; i < newString.length -1; i++) {
            System.out.println(newString[i]);
            JSONObject jObjStatus;
            jObjStatus = new JSONObject(newString[i]);
            args.add(jObjStatus.getString("Status"));
            args.add(jObjStatus.getString("Lat"));
            args.add(jObjStatus.getString("Lng"));
            args.add(jObjStatus.getString("Zeit"));
            args.add(jObjStatus.getString("Nickname"));

        }

        bundle1.putStringArrayList("args", args);

        System.out.println("ARGS IN UPDATEMAPFRAGTASK: " + args);




        updateMapFragment.setData(bundle1);



    }

    /*
                    JSONObject jsonObject = new JSONObject(responseStatus);
                    JSONArray jsonArray = jsonObject.getJSONArray("User");
                    double[] currLoc = bundle1.getDoubleArray("currLoc");

                    args.add(String.valueOf(currLoc[0]));
                    args.add(String.valueOf(currLoc[1]));
                    System.out.println("ArrayList: "+ args);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jObjStatus;
                        jObjStatus = new JSONObject(jsonArray.getString(i));
                        args.add(jObjStatus.getString("Status"));
                        args.add(jObjStatus.getString("Lat"));
                        args.add(jObjStatus.getString("Lng"));
                        args.add(jObjStatus.getString("Zeit"));
                        args.add(jObjStatus.getString("Nickname"));

                    }
     */
}
