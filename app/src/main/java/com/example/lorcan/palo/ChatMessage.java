package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.protocol.ExecutionContext.HTTP_REQUEST;

/**
 * Created by Win10 Home x64 on 23.11.2017.
 */

public class ChatMessage {

    public RequestQueue requestQueue;
    public final String URL = "http://palo.square7.ch/insertMessage.php";
    public StringRequest request;

    public RequestQueue requestQueue1;
    public final String URL1 = "http://palo.square7.ch/isMessage.php";
    public StringRequest request1;

    public RequestQueue requestQueue2;
    public final String URL2 = "http://palo.square7.ch/getMessage.php";
    public StringRequest request2;


    public String nickname;
    public String nachricht;
    public String android_id;
    public MapFragment mapFragment;
    public String responseIsMessage;

    public void sendMessage(final String nickname, final String nachricht) {
System.out.println("NICKNAME CHAT: " + nickname);
        this.nickname = nickname;
        this.nachricht = nachricht;
        this.requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        TelephonyManager telephonyManager = (TelephonyManager) MyApplicationContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final String android_id = telephonyManager.getDeviceId();

        this.request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("ANTWORT VON SEND STATUS: " + response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            // set of parameters in a hashmap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();

                if(nickname.substring(nickname.length() - 1).equals(" ")){
                    String newnickname = nickname.substring(0, nickname.length() - 1);
                    hashMap.put("nickname", newnickname);
                }else {
                    hashMap.put("nickname", nickname);
                }
                hashMap.put("nachricht", nachricht);
                hashMap.put("android_id", android_id);

                System.out.println("DAS WAS GESENDET WIRD VOM STATUS1: " + hashMap);

                return hashMap;
            }
        };

        requestQueue.add(request);
        System.out.println("NAME IN MESSAGE:" + nickname);
        

    }






    public String getMessage(final String android_id, final ChatActivity chatActivity, final String nicknameNutzer1){

        final String[] message = {""};

        this.requestQueue2 = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        this.request2 = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response1) {
                if (response1.length() > 0) {
                    String[] responseArray = response1.split("Ω±≠");
                    chatActivity.erstelleAntwort(responseArray[0]);
                    JSONChatDB jsonChatDB = new JSONChatDB();
                    System.out.println("RESPONSEARRAY:" +responseArray.toString());
                    jsonChatDB.addNewChatUser(responseArray[1]);

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            // set of parameters in a hashmap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();


                hashMap.put("android_id", android_id);
                hashMap.put("nickname", nicknameNutzer1);
                hashMap.put("pw", "gibNamen");


                return hashMap;
            }
        };

        requestQueue2.add(request2);
        return message[0];


    }




    public void isMessage(MapFragment mapFragment){
        this.mapFragment = mapFragment;
        TelephonyManager telephonyManager = (TelephonyManager) MyApplicationContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final String android_id = telephonyManager.getDeviceId();
        this.android_id = android_id;
        isMessageThere();
    }


    public void isMessageThere() {
        new ResponseTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class ResponseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            requestQueue1 = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            request1 = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    responseIsMessage = response;
                    System.out.println("RESPONSE CHAT DB:" + response);
                    handleResponse(responseIsMessage, mapFragment);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {

                // set of parameters in a hashmap, which will be send to the php file (server side)
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();

                    hashMap.put("android_id", android_id);


                    System.out.println("DAS WAS GESENDET WIRD VOM STATUS: " + hashMap);

                    return hashMap;
                }
            };

            requestQueue1.add(request1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    public void handleResponse(String response, MapFragment mapFragment){
        System.out.println("HANDLERESPONSE: " + response);

        if(response.contains("tr")) {
            mapFragment.setImageViewVisibility(true);
            System.out.println("SHOW BILDCHEN");
        }else{
            mapFragment.setImageViewVisibility(false);
        }
    }

    public void isMessageThere1(ChatActivity chatActivity, String nickname) {
        TelephonyManager telephonyManager = (TelephonyManager) MyApplicationContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final String android_id = telephonyManager.getDeviceId();
        this.android_id = android_id;
        this.nickname = nickname;
        new ResponseTask1(chatActivity).execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class ResponseTask1 extends AsyncTask<Void, Void, Void> {
        ChatActivity chatActivity;
        public ResponseTask1(ChatActivity chatActivity) {
            this.chatActivity = chatActivity;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            requestQueue1 = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            request1 = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    responseIsMessage = response;
                    System.out.println("RESPONSE CHAT DB:" + response);
                    handleResponse1(responseIsMessage, chatActivity);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {

                // set of parameters in a hashmap, which will be send to the php file (server side)
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();

                    hashMap.put("android_id", android_id);

                    System.out.println("DAS WAS GESENDET WIRD VOM STATUS: " + hashMap);

                    return hashMap;
                }
            };

            requestQueue1.add(request1);
            return null;
        }
    }

    public void handleResponse1(String response, ChatActivity chatActivity){
        if(response.contains("tr")) {
         ChatMessage chatMessage = new ChatMessage();
         chatMessage.getMessage(android_id, chatActivity, nickname);

        }
    }
}
