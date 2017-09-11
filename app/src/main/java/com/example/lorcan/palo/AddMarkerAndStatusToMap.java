package com.example.lorcan.palo;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Win10 Home x64 on 03.09.2017.
 */

public class AddMarkerAndStatusToMap {
    Context context;
    public AddMarkerAndStatusToMap(Context context){
        this.context = context;
    }


    public void addMarkerToMap(String jsonData){

        System.out.println(jsonData);
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("User");
            System.out.println(jsonArray);
            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject jObj;
                jObj = new JSONObject(jsonArray.getString(i));
                System.out.println(jObj.get("Status"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
