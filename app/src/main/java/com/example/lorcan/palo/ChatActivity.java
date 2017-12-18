package com.example.lorcan.palo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {
    String name;
    String nachricht;
    String answerMessage;
    EditText message;
    ImageView sendenBtn;
    RelativeLayout relLayoutChat;
    TelephonyManager telephonyManager = (TelephonyManager) MyApplicationContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = (EditText) findViewById(R.id.et_message1);
        sendenBtn = (ImageView) findViewById(R.id.sendenBtn1);
        Serializable k = getIntent().getSerializableExtra("name");
        name = k.toString();
        name = name.substring(0, name.length() - 1);
        Timer timer = new Timer();
        timer.schedule(new checkForMessage(), 0, 5000);

        sendenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nachricht = message.getText().toString();
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.sendMessage(name, nachricht);
                erstelleNachricht();
            }
        });
    }

    class checkForMessage extends TimerTask {
        public void run() {
            if (ActivityCompat.checkSelfPermission(ChatActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
            ChatMessage getMessage = new ChatMessage();
            getMessage.isMessageThere1(ChatActivity.this, name);
        }
    }



        public void erstelleAntwort(String answerMessage){

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            TextView txt1 = new TextView(ChatActivity.this);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutChat);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.width = width - width/3;
            layoutParams.setMargins(0, 10, width/3, 0);
            layoutParams.weight = 6;

            txt1.setText(answerMessage);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txt1.setBackgroundResource(R.drawable.rounded_corner);
                txt1.setPadding(20,15,20,15);
                txt1.setGravity(Gravity.LEFT);
            }
            txt1.setLayoutParams(layoutParams);
            linearLayout.addView(txt1);

        }
        public void erstelleNachricht(){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            TextView txt1 = new TextView(ChatActivity.this);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutChat);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 10, 0, 0);
            layoutParams.width = width - width/3;
            layoutParams.setMargins(width/3, 10, 0, 0);
            layoutParams.weight = 6;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                txt1.setBackgroundResource(R.drawable.rounded_corner);
                txt1.setPadding(20,15,20,15);
                txt1.setGravity(Gravity.RIGHT);
                txt1.setText(nachricht);
            }
            txt1.setLayoutParams(layoutParams);
            linearLayout.addView(txt1);
            message.setText("");


        }


}

