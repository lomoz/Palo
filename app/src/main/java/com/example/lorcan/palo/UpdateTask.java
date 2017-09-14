package com.example.lorcan.palo;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

    //to get data from DB asynchronously
    public class UpdateTask extends AsyncTask<String, String, String> {


        private String responseStatus;
        private static final String strUrl = "http://palo.square7.ch/getStatus.php";
        private Boolean upStarted = false;

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(strUrl);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String value = bf.readLine();
                responseStatus = value;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStatus;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            upStarted = true;
        }


        public ArrayList<JSONObject> update() {
            JSONObject jsonObject = null;
            ArrayList<JSONObject> arrayList = new ArrayList<>();
            if(upStarted == false) {
                new UpdateTask().execute();
            }else {

                try {
                    jsonObject = new JSONObject(responseStatus);

                    JSONArray jsonArray = jsonObject.getJSONArray("User");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jObjStatus;
                        jObjStatus = new JSONObject(jsonArray.getString(i));

                        arrayList.add(jObjStatus);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                upStarted = false;
            }
                System.out.println("ArrayList Return: " + arrayList);
                return arrayList;
        }

    }

