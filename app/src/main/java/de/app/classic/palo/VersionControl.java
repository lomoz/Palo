package de.app.classic.palo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class VersionControl {
        private static String fileName = "version.json";

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

        public void setActVersion(String version){

            String old = getData(MyApplicationContext.getAppContext());
            try {
                if(old == null){
                    createNewDBDeleteOld("{ \"Version\" : ['1']}");
                    old = getData(MyApplicationContext.getAppContext());
                }
                JSONObject jsonObject = new JSONObject(old);
                JSONArray jsonArray = jsonObject.getJSONArray("Version");

                jsonArray.put(0, version);
                createNewDBDeleteOld("{ \"Version\" : "+jsonArray.toString()+"}");

            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        public String getActVersion(){
            String old = getData(MyApplicationContext.getAppContext());
            String version = "";
            try {
                JSONObject jsonObject =  new JSONObject(old);

                JSONArray jsonArray = jsonObject.getJSONArray("Version");

                version = jsonArray.get(0).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return version;
        }


}
