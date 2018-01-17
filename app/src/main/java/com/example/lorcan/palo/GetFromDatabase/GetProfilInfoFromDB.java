package com.example.lorcan.palo.GetFromDatabase;

import android.app.Activity;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lorcan.palo.ChatActivity;
import com.example.lorcan.palo.MyApplicationContext;
import com.example.lorcan.palo.ProfilActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Win10 Home x64 on 17.01.2018.
 */

public class GetProfilInfoFromDB {


    public RequestQueue requestQueue;
    public final String URL = "http://palo.square7.ch/getProfilInfo.php";
    public StringRequest request;
    String name;

    public void getInfo(ProfilActivity profilActivity, String name){
        ArrayList<String> list = new ArrayList<>();
        this.name = name;
        new ResponseTask1(profilActivity);
    }

    public class ResponseTask1 extends AsyncTask<Void, Void, Void> {
        ProfilActivity profilActivity;
        public ResponseTask1(ProfilActivity activity) {
            this.profilActivity = profilActivity;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    handleResponse(response, profilActivity);
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

                    hashMap.put("nickname", name);


                    return hashMap;
                }
            };

            requestQueue.add(request);
            return null;
        }
    }

    public void handleResponse(String info, ProfilActivity profilActivity){
        ArrayList<String> list = new ArrayList<>(); //[bild, status, name]
        try {
            JSONObject jsonObject = new JSONObject(info);
            String bild = jsonObject.get("bild").toString();
            String status = jsonObject.get("status").toString();
            list.add(bild);
            list.add(status);
            list.add(name);
            profilActivity.setInfoToScreen(list);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

}
