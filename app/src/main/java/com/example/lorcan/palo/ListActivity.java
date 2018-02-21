package com.example.lorcan.palo;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Objects;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        createList(JSONChatDB.getData(this));
    }

    public void createList(String list) {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        JSONArray listUsername = null;

        try {
            JSONObject jsonObject = new JSONObject(list);
            listUsername = jsonObject.getJSONArray("Users");
        } catch (JSONException e) {
            System.out.println("LIST: " + JSONChatDB.getData(this));
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        if (listUsername != null) {
            if (listUsername.length() > 1) {
                for (int i = 0; i < listUsername.length(); i++) {
                    try {
                        if (!Objects.equals(listUsername.get(i).toString(), "")) {
                            LinearLayout linearLayoutChatList = (LinearLayout) findViewById(R.id.linear_layout_chat_list);
                            LinearLayout linearLayoutUsername = new LinearLayout(ListActivity.this);
                            final TextView textViewUsername = new TextView(ListActivity.this);
                            ImageView imageViewDeleteChat = new ImageView(ListActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                            if ((i % 2) == 0) {
                                textViewUsername.setBackgroundResource(R.color.hhu_blue);
                                textViewUsername.setTextColor(textViewUsername.getResources().getColor(R.color.white));
                            }
                            else {
                                textViewUsername.setBackgroundResource(R.color.white);
                                textViewUsername.setTextColor(textViewUsername.getResources().getColor(R.color.hhu_blue));
                            }

                            imageViewDeleteChat.setImageResource(R.drawable.quantum_ic_clear_white_24);
                            imageViewDeleteChat.setBackgroundResource(R.color.hue_red);

                            layoutParams.weight = 1;
                            layoutParams.height = 100;
                            layoutParams.width = 40;
                            layoutParams.gravity = Gravity.CENTER;

                            imageViewDeleteChat.setLayoutParams(layoutParams);
                            imageViewDeleteChat.setMaxWidth(width / 5);

                            imageViewDeleteChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    JSONChatDB jsonChatDB = new JSONChatDB();
                                    jsonChatDB.deleteUser(textViewUsername.getText().toString());
                                    ListActivity.this.recreate();
                                }
                            });

                            textViewUsername.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intentChat = new Intent(ListActivity.this, ChatActivity.class);
                                    intentChat.putExtra("name", textViewUsername.getText().toString());
                                    startActivity(intentChat);
                                }
                            });

                            textViewUsername.setWidth(width - width / 5);
                            textViewUsername.setHeight(height / 8);
                            textViewUsername.setTextSize(20);
                            textViewUsername.setGravity(Gravity.CENTER_VERTICAL);
                            textViewUsername.setPadding(40, 0, 0, 0);

                            linearLayoutUsername.setBackgroundResource(R.color.hue_red);
                            linearLayoutUsername.setOrientation(LinearLayout.HORIZONTAL);

                            try {
                                textViewUsername.setText(listUsername.get(i).toString());
                                linearLayoutUsername.addView(textViewUsername);
                                linearLayoutUsername.addView(imageViewDeleteChat);
                                linearLayoutChatList.addView(linearLayoutUsername);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(listUsername);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Toast.makeText(ListActivity.this, R.string.no_chats, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(ListActivity.this, R.string.no_chats, Toast.LENGTH_SHORT).show();
        }
    }
}
