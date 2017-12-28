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
import java.util.ArrayList;


public class JSONChatDB {

    static String fileName = "chats.json";

    public static void createNewDBDeleteOld(String nameJSON) {
        try {

            FileWriter file = new FileWriter(MyApplicationContext.getAppContext().getFilesDir().getPath() + "/" + fileName);
            if(getData(MyApplicationContext.getAppContext()) == null){
                nameJSON = "{ \"Users\" : [\"" + nameJSON+ "\"]}";
                System.out.println(nameJSON);
            }

            file.write(nameJSON);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
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

    public void addNewChatUser(String newUser){
        String old = getData(MyApplicationContext.getAppContext());
        Boolean bool = false;
        try {
            JSONObject jsonObject = new JSONObject(old);
            JSONArray listeNicknames = null;
            listeNicknames = jsonObject.getJSONArray("Users");
            System.out.println(listeNicknames);

            for(int i =1; i<listeNicknames.length(); i++){
                if(listeNicknames.get(i).toString().equals(newUser)){
                    bool = false;
                }else{
                    bool = true;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String newJSON = "";
        //if(bool) {
            if (old != null && old.length() > 2) {
                old = old.substring(0, old.length() - 2);
                newJSON = old + ", \"" + newUser + "\"]}";
            } else {
                newJSON = newUser;
            }
            createNewDBDeleteOld(newJSON);
        //}
    }

    public void deleteUser(String user){
        String list = JSONChatDB.getData(MyApplicationContext.getAppContext());
        JSONObject jsonObject = null;
        String data = "";
        try {
            jsonObject = new JSONObject(list);
            JSONArray listeNicknames = jsonObject.getJSONArray("Users");
            for(int i =1; i<listeNicknames.length(); i++){
                if(!listeNicknames.get(i).toString().equals(user)){
                    System.out.println("User der gelöscht werden sollte: " + user);
                    System.out.println("User die durchgelassen werden: " + listeNicknames.get(i).toString());
                    data = data + ", \"" + listeNicknames.get(i).toString() + "\"";
                }
            }
            data = "{ \"Users\" : [" + data + "]}";
            createNewDBDeleteOld(data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}