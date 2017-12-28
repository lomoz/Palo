package com.example.lorcan.palo;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Win10 Home x64 on 28.12.2017.
 */

public class OldStatus {
    static String fileName = "status.json";

    public static void addNewEntry(String nameJSON) {
        try {

            FileWriter file = new FileWriter(MyApplicationContext.getAppContext().getFilesDir().getPath() + "/" + fileName);
            if(getData(MyApplicationContext.getAppContext()) == null){
                nameJSON = "{ \"Status\" : [\"" + nameJSON+ "\"]}";
            }
                JSONObject jsonObject = new JSONObject(getData(MyApplicationContext.getAppContext()));
                JSONArray jsonArray = jsonObject.getJSONArray("Status");

                if(jsonArray.length() > 5){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        jsonArray.remove(0);
                        jsonArray.put(nameJSON);
                        nameJSON = "{ \"Status\" :" + jsonArray.toString()+ "}";
                        System.out.println(nameJSON);
                    }
                }

            file.write(nameJSON);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getData(Context context) {

        try {
            File f = new File(context.getFilesDir().getPath() + "/" + fileName);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

}
