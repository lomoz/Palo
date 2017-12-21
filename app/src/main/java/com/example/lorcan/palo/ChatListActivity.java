package com.example.lorcan.palo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        JSONChatDB jsonChatDB = new JSONChatDB();
        erstelleListe(JSONChatDB.getData(this));

    }

    public void erstelleListe(String list){
        JSONArray listeNicknames = null;

        try {
            JSONObject jsonObject = new JSONObject(list);
            listeNicknames = jsonObject.getJSONArray("Users");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=1; i<listeNicknames.length(); i++) {

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linlayoutChatList);

            LinearLayout linearLayout1 = new LinearLayout(ChatListActivity.this);

            TextView txt1 = new TextView(ChatListActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 10, 0, 0);

            layoutParams.weight = 6;
            txt1.setPadding(20, 15, 20, 15);
            txt1.setGravity(Gravity.RIGHT);
            txt1.setLayoutParams(layoutParams);
            try {
                txt1.setText(listeNicknames.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            linearLayout1.addView(txt1);
            linearLayout.addView(linearLayout1);

        }
    }
}
