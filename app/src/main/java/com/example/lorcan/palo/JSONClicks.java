package com.example.lorcan.palo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class JSONClicks {
    private static String fileName = "clicks.json";

    private static void createNewDBDeleteOld(String nameJSON) {
        try {
            System.out.println("New created DB: " + nameJSON);
            FileWriter file = new FileWriter(MyApplicationContext.getAppContext().getFilesDir().getPath() + "/" + fileName);

            file.write(nameJSON);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        }
    }

    static String getData(Context context) {

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

    public void addNewClick(int i){

        String old = getData(MyApplicationContext.getAppContext());
        System.out.println(old);
        try {
            if(old == null){
                createNewDBDeleteOld("{ \"Clicks\" : ['0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0']}");
                old = getData(MyApplicationContext.getAppContext());
            }
            JSONObject jsonObject = new JSONObject(old);
            JSONArray jsonArray = jsonObject.getJSONArray("Clicks");
            int cnt = java.lang.Integer.parseInt(jsonArray.get(i).toString());

            cnt = cnt + 1;
            jsonArray.put(i-1, cnt);
            System.out.println(jsonArray.toString());
            createNewDBDeleteOld("{ \"Clicks\" : "+jsonArray.toString()+"}");

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public int getClickDataByIndex(int i){
        String old = getData(MyApplicationContext.getAppContext());
        int cnt = 0;
        try {
        JSONObject jsonObject =  new JSONObject(old);

        JSONArray jsonArray = jsonObject.getJSONArray("Clicks");

        cnt = java.lang.Integer.parseInt(jsonArray.get(i).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cnt;
    }
}
