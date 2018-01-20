package com.example.lorcan.palo;

import android.content.Context;
import android.os.Build;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.widget.Toast;

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

    public static void addNewEntry(String newStatus) {

            String old = getData(MyApplicationContext.getAppContext());
            System.out.println(old);
        try{
            if (!old.contains("{ \"Status\" : [\"\"")) {
                writeNewJSON("{ \"Status\" : [\"\",\""+newStatus+"\"]}");
            } else {
                old = old.substring(0, old.length() - 2);
                newStatus = old + ", \"" + newStatus + "\"]}";
                writeNewJSON(newStatus);
            }
        }catch(NullPointerException e){
            if(old == null) {
                writeNewJSON("{ \"Status\" : [\"\",\""+newStatus+"\"]}");
            }
        }




            System.out.println("NAMEJSON: " + newStatus);




    }

    public static void writeNewJSON(String newJSON){

        try {
            FileWriter file = new FileWriter(MyApplicationContext.getAppContext().getFilesDir().getPath() + "/" + fileName);
            file.write(newJSON);
            file.flush();
            file.close();

            if (checkLength()) {
                deleteLastOne();
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Boolean checkLength(){
        Boolean bool = false;
        String jsonString = getData(MyApplicationContext.getAppContext());
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("Status");
            if(jsonArray.length() > 5){
                bool = true;
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return bool;
    }

    public static void deleteLastOne(){
        String jsonString = getData(MyApplicationContext.getAppContext());
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("Status");
            System.out.println("JSONARRAY: " + jsonArray.toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jsonArray.remove(1);
            }else{
                Toast.makeText(MyApplicationContext.getAppContext(), "deine android-version ist leider zu alt dafür. irgendwann trifft es jeden. man muss ein neues smartphone kaufen.", Toast.LENGTH_LONG);
            }

            writeNewJSON("{ \"Status\" : "+jsonArray.toString()+"}");

        }catch(JSONException e){
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
