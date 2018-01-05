package com.example.lorcan.palo;

import android.content.Context;
import android.os.Build;
import android.renderscript.ScriptIntrinsicYuvToRGB;
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
            nameJSON = nameJSON.substring(0, nameJSON.length() - 1);
            FileWriter file = new FileWriter(MyApplicationContext.getAppContext().getFilesDir().getPath() + "/" + fileName);
            String old = getData(MyApplicationContext.getAppContext());

            if(old.length() <= 0) {
                nameJSON = "{ \"Status\" : [\"" + nameJSON + "\"]}";
            }else{
                old = old.substring(0, old.length() - 2);
                nameJSON = old + ", \"" + nameJSON + "\"]}";
            }
                System.out.println("NAMEJSON: " + nameJSON);
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

                FileInputStream is = new FileInputStream(f);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();


            return new String(buffer);
        } catch (IOException e) {
            System.out.println("IOExceptions");
            return null;
        } catch (NullPointerException e1){
            System.out.println("ERROR STATUS.JSON IST NICHT VORHANDEN");
            return null;
        }
    }

}
