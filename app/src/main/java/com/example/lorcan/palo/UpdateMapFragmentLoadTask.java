package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateMapFragmentLoadTask extends Fragment {

    private static final String strUrl = "http://palo.square7.ch/getStatus.php";
    public UpdateMapFragment updateMapFragment;
    public LoadDataTask loadData;
    MapFragment mapFragment;
    Bundle bundle1 = new Bundle();

    public double[] currLoc;

    public void loadData(double[] currLoc, UpdateMapFragment updateMapFragment, MapFragment mapFragment){
        this.currLoc = currLoc;
        this.updateMapFragment = updateMapFragment;
        this.mapFragment = mapFragment;
        loadData = new LoadDataTask();
        loadData.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class LoadDataTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            StringRequest request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
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
                    UpdateMapFragment updateMapFragment = new UpdateMapFragment();
                    FragmentManager fragmentManager = updateMapFragment.getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                            .replace(R.id.relativelayout_for_fragments,
                                    updateMapFragment,
                                    updateMapFragment.getTag()
                            ).commit();
                }
            }) {

                // set of parameters in a hashmap, which will be send to the php file (server side)
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put("zugang", "zugang");


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


    public void handleResponse(String response) throws JSONException {

        ArrayList<String> args = new ArrayList<>();


        String[] newString = response.split("#");
        args.add(String.valueOf(this.currLoc[0]));
        args.add(String.valueOf(this.currLoc[1]));

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
