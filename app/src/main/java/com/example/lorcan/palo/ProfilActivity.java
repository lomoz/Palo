package com.example.lorcan.palo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lorcan.palo.GetFromDatabase.GetProfilInfoFromDB;

import java.io.Serializable;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifTextView;

public class ProfilActivity extends AppCompatActivity {

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Serializable k = getIntent().getSerializableExtra("name");
        name = k.toString();
        ProfilActivity.this.setTitle("Profil von " + name);

        GetProfilInfoFromDB getProfilInfoFromDB = new GetProfilInfoFromDB();
        getProfilInfoFromDB.getInfo(ProfilActivity.this, name);
    }

    public void setInfoToScreen(ArrayList<String> list){

        RelativeLayout rel = (RelativeLayout) findViewById(R.id.profilLayout);



        GifTextView gif = (GifTextView) findViewById(R.id.imageView2);
        rel.removeView(gif);

        RelativeLayout.LayoutParams layoutImageView = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ImageView ivImage = new ImageView(this);
        layoutImageView.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ivImage.setLayoutParams(layoutImageView);
        String image = list.get(0);
        if(image != null && image.length() > 0) {


            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivImage.setRotation(90);
            ivImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 400, 400, false));
            ivImage.setBackground(getDrawable(R.drawable.layout_bg));
        }else{
            Toast.makeText(MyApplicationContext.getAppContext(), "Es ist leider kein Profilbild vorhanden.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MyApplicationContext.getAppContext(), ChatListActivity.class);
            startActivity(i);
        }

        TextView statusTextView = new TextView(this);

        RelativeLayout.LayoutParams layoutTextView = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutTextView.setMargins(0, 600, 0, 0);
        layoutTextView.addRule(RelativeLayout.BELOW, ivImage.getId());
        layoutTextView.addRule(RelativeLayout.CENTER_HORIZONTAL);
        statusTextView.setLayoutParams(layoutTextView);
        statusTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        statusTextView.setWidth(600);
        statusTextView.setTextColor(Color.LTGRAY);
        String status = "\"" + list.get(1) + "\"";
        statusTextView.setText(status);


        //---ICON LIST---

        LinearLayout iconList = new LinearLayout(this);
        LinearLayout.LayoutParams iconListLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        iconList.setPadding(0, 700, 0 ,0);
        iconListLayout.height = LinearLayout.LayoutParams.MATCH_PARENT;
        iconListLayout.width = LinearLayout.LayoutParams.MATCH_PARENT;

        iconList.setLayoutParams(iconListLayout);

        TableLayout table = new TableLayout(this);
        ScrollView scrollView = new ScrollView(this);
        TableRow.LayoutParams tableRowLayout = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40);

        //--for schleife--
        TableRow tableRow = new TableRow(this);
        int cnt = 0;
        for(int i = 2; i < list.size(); i++){
            if(list.get(i).equals("1")){
                cnt = cnt +1;
                ImageView iconIV = new ImageView(this);
                if(i == 2) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 3) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 4) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 5) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 6) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 7) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 8) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 9) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 10) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 11) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 12) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 13) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 14) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 15) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 16) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 17) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 18) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 19) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 20) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 21) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 22) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 23) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 24) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                tableRow.addView(iconIV);
                tableRow.setLayoutParams(tableRowLayout);
                if(cnt == 5){
                    cnt = 0;
                    table.addView(tableRow);
                    tableRow = new TableRow(this);
                }
            }

        }


        //----------------


        scrollView.addView(table);
        iconList.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
        iconList.addView(scrollView);

        //---------------

        rel.addView(ivImage);
        rel.addView(statusTextView);
        rel.addView(iconList);


    }

}
