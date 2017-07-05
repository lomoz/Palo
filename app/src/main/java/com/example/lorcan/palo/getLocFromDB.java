package com.example.lorcan.palo;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by paul on 01.07.17.
 */

public class getLocFromDB {


    String result = null;
    InputStream is = null;
    StringBuilder sb;
    String nutzerEmail;
    double nutzerLat;
    double nutzerLng;

    public getLocFromDB(Context context){

    }

    public void getLocation(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //http post
                    try{
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://palo.square7.de/getLocation.php");
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    }catch(Exception e){
                        Log.e("log_tag", "Error in http connection"+e.toString());
                    }
                    //convert response to string

                    try{
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                        sb = new StringBuilder();
                        sb.append(reader.readLine() + "\n");
                        String line = "0";
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                        System.out.println("-----RESULT1------ : " + result);
                    }catch(Exception e){
                        Log.e("log_tag", "Error converting result "+e.toString());
                    }
                    //paring data

                    String[] resultArray = result.split("#");
                    String[] yourArray = Arrays.copyOfRange(resultArray, 1, resultArray.length);

                    try{

                        for (int i = 0; i < yourArray.length; i++) {
                            JSONObject jsonObject = new JSONObject(yourArray[i]);
                            System.out.println(jsonObject);
                            nutzerEmail = jsonObject.getString("Email");
                            nutzerLat = jsonObject.getDouble("Lat");
                            nutzerLng = jsonObject.getDouble("Lng");
                            System.out.println("LOCATION: " + nutzerLat + " / " + nutzerLng);

                        }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }

            }).start();
        }
}
