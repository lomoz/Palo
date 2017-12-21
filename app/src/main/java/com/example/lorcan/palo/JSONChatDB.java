package com.example.lorcan.palo;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;


public class JSONChatDB {

    static String fileName = "chats.json";

    public static void saveData(String nameJSON) {
        try {

            FileWriter file = new FileWriter(MyApplicationContext.getAppContext().getFilesDir().getPath() + "/" + fileName);
            if(getData(MyApplicationContext.getAppContext()) == null){
                nameJSON = "{ \"Users\" : [\"" + nameJSON+ "\"]}";
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

    public void addnewChatUser(String newUser){
        String old = getData(MyApplicationContext.getAppContext());
        String newJSON = "";
        if(old != null && old.length() > 2) {
            old = old.substring(0, old.length() - 2);
            newJSON = old + ", \"" + newUser + "\"]}";
        }else{
            newJSON = newUser;
        }
        saveData(newJSON);
    }
}