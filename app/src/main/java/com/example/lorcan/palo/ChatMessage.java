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

import java.util.HashMap;
import java.util.Map;


public class ChatMessage {

    public RequestQueue requestQueue;
    public final String URL = "http://palo.square7.ch/insertMessageGami.php";
    public StringRequest request;

    public RequestQueue requestQueue1;
    public final String URL1 = "http://palo.square7.ch/isMessageGami.php";
    public StringRequest request1;

    public RequestQueue requestQueue2;
    public final String URL2 = "http://palo.square7.ch/getMessageGami.php";
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

                System.out.println("DAS WAS GESENDET WIRD VOM CHAT1: " + hashMap);
                OnClickSendToDB onClickSendToDB = new OnClickSendToDB();
                onClickSendToDB.sendBtnClick(android_id, "4");
                return hashMap;
            }
        };

        requestQueue.add(request);
        System.out.println("NAME IN MESSAGE:" + nickname);
        

    }






    public String getMessage(final String android_id, final ChatActivity chatActivity, String nicknameNutzer1){
        if(nicknameNutzer1.substring(nicknameNutzer1.length() - 1).equals(" ")){
            nicknameNutzer1 = nicknameNutzer1.substring(0, nicknameNutzer1.length() - 1);
        }
        final String[] message = {""};

        this.requestQueue2 = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        final String finalNicknameNutzer = nicknameNutzer1;
        this.request2 = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response1) {

                System.out.println("RESPONSE FROM CHAT: " +response1);
                if (response1.length() > 0) {
                    chatActivity.createAnswer(response1);

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

                System.out.println("DIE PARAMETER: " + android_id +", "+ finalNicknameNutzer);
                hashMap.put("android_id", android_id);
                hashMap.put("nickname", finalNicknameNutzer);


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
         JSONChatDB jsonChatDB = new JSONChatDB();
         jsonChatDB.addNewChatUser(nickname);
         ChatMessage chatMessage = new ChatMessage();
         chatMessage.getMessage(android_id, chatActivity, nickname);

        }
    }

    public void isMessageThere2(CheckForMessageService checkForMessageService) {
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
        new ResponseTask2(checkForMessageService).execute();
    }


    @SuppressLint("StaticFieldLeak")
    public class ResponseTask2 extends AsyncTask<Void, Void, Void> {
        CheckForMessageService checkForMessageService;
        public ResponseTask2(CheckForMessageService checkForMessageService) {
            this.checkForMessageService = checkForMessageService;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            requestQueue1 = Volley.newRequestQueue(MyApplicationContext.getAppContext());
            request1 = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    responseIsMessage = response;
                    System.out.println("RESPONSE CHAT DB:" + response);
                    handleResponse2(responseIsMessage, checkForMessageService);
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

    public void handleResponse2(String response, CheckForMessageService checkForMessageService){
        if(response.contains("tr")) {
            checkForMessageService.stopRestartAndNotify();
        }else{
            checkForMessageService.stopAndRestart();
        }
    }
}
