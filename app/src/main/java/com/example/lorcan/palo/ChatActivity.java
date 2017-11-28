package com.example.lorcan.palo;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.Serializable;

public class ChatActivity extends AppCompatActivity {
    String name;
    String nachricht;
    EditText message;
    ImageView sendenBtn;
    RelativeLayout relLayoutChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = (EditText) findViewById(R.id.et_message);
        sendenBtn = (ImageView) findViewById(R.id.sendenBtn);
        relLayoutChat = (RelativeLayout) findViewById(R.id.relLayoutChat);
        Serializable k = getIntent().getSerializableExtra("name");
        name = k.toString();
        System.out.println("NAME: " + name + "#");
        name = name.substring(0, name.length()-1);
        System.out.println("NAME 1: " + name + "#");
        sendenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nachricht = message.getText().toString();
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.sendMessage(name, nachricht);
                TextView txt1 = new TextView(ChatActivity.this);
                LinearLayout linearLayout = new LinearLayout(MyApplicationContext.getAppContext());
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                linearLayout.setLayoutParams(layout);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    LayoutParams llp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    llp.setMargins(0, 10, 0, 0);
                    txt1.setLayoutParams(llp);
                    txt1.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                    txt1.setPadding(16, 16, 16, 16);
                    txt1.setText(nachricht);
                }
                linearLayout.addView(txt1);
                relLayoutChat.addView(linearLayout);
                message.setText("");
            }
        });
    }
}

