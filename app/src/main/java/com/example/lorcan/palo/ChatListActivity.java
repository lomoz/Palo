package com.example.lorcan.palo;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatListActivity extends AppCompatActivity {
    public JSONChatDB jsonChatDB = new JSONChatDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        createList();
    }


    public void createList() {
        String list = JSONChatDB.getData(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        JSONArray listNicknames = null;

        try {
            JSONObject jsonObject = new JSONObject(list);
            listNicknames = jsonObject.getJSONArray("Users");
        } catch (JSONException e) {
            System.out.println("LIST:" + new JSONChatDB().getData(this));
        } catch (NullPointerException ne){
            ne.printStackTrace();
        }
        if(listNicknames != null) {
            if (listNicknames.length() > 1) {
                for (int i = 1; i < listNicknames.length(); i++) {
                    try {
                        if (listNicknames.get(i).toString() != "") {
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutChatList);
                            LinearLayout linearLayout1 = new LinearLayout(ChatListActivity.this);
                            final TextView txt1 = new TextView(ChatListActivity.this);
                            ImageView imageView = new ImageView(ChatListActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                            if ((i % 2) == 0) {
                                txt1.setBackgroundResource(R.color.hhu_blue);
                                txt1.setTextColor(txt1.getResources().getColor(R.color.white));
                            }
                            else {
                                txt1.setBackgroundResource(R.color.white);
                                txt1.setTextColor(txt1.getResources().getColor(R.color.hhu_blue));
                            }

                            imageView.setImageResource(R.drawable.quantum_ic_clear_white_24);
                            imageView.setBackgroundResource(R.color.hue_red);

                            layoutParams.weight = 1;
                            layoutParams.height = 100;
                            layoutParams.width = 40;
                            layoutParams.gravity = Gravity.CENTER;

                            imageView.setLayoutParams(layoutParams);
                            imageView.setMaxWidth(width / 5);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    JSONChatDB jsonChatDB = new JSONChatDB();
                                    jsonChatDB.deleteUser(txt1.getText().toString());
                                    ChatListActivity.this.recreate();
                                }
                            });

                            txt1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                                    intent.putExtra("name", txt1.getText().toString());
                                    startActivity(intent);
                                }
                            });

                            txt1.setWidth(width - width / 5);
                            txt1.setHeight(height / 8);
                            txt1.setTextSize(20);
                            txt1.setGravity(Gravity.CENTER_VERTICAL);
                            txt1.setPadding(40, 0, 0, 0);

                            linearLayout1.setBackgroundResource(R.color.hue_red);
                            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                            try {
                                txt1.setText(listNicknames.get(i).toString());
                                linearLayout1.addView(txt1);
                                linearLayout1.addView(imageView);
                                linearLayout.addView(linearLayout1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(listNicknames);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Toast.makeText(ChatListActivity.this, R.string.no_chats, Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(ChatListActivity.this, R.string.no_chats, Toast.LENGTH_LONG).show();
        }
    }
}
