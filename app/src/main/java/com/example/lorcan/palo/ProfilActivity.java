package com.example.lorcan.palo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lorcan.palo.GetFromDatabase.GetPointsDB;
import com.example.lorcan.palo.GetFromDatabase.GetProfilInfoFromDB;

import java.io.Serializable;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifTextView;

public class ProfilActivity extends AppCompatActivity {

    String name;
    public int points;

    Point size = new Point();
    int width = size.x;
    int height = size.y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Serializable k = getIntent().getSerializableExtra("name");
        name = k.toString();
        ProfilActivity.this.setTitle("Profil von " + name);

        GetProfilInfoFromDB getProfilInfoFromDB = new GetProfilInfoFromDB();
        getProfilInfoFromDB.getInfo(ProfilActivity.this, name);

        GetPointsDB getPointsDB = new GetPointsDB();
        points = getPointsDB.getPoints(name);

        display.getSize(size);
        width = size.x;
        height = size.y;

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
        ScrollView scrollView = new ScrollView(this);

        RelativeLayout.LayoutParams iconListLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        iconListLayoutParams.setMargins(0, 700, 0, 0);
        iconListLayoutParams.addRule(RelativeLayout.BELOW, statusTextView.getId());
        iconListLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        scrollView.setLayoutParams(iconListLayoutParams);
        scrollView.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        iconListLayoutParams.width = 850;
        iconListLayoutParams.height = 550;
        scrollView.setBackgroundColor(Color.LTGRAY);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);


        int cnt = 0;
        for(int i = 3; i < list.size(); i++){
            list.get(i).trim();
            if(list.get(i).equals("1")){
                cnt = cnt +1;
                ImageView iconIV = new ImageView(this);
                iconIV.setScaleX(0.50f);
                iconIV.setScaleY(0.50f);
                if(i == 3) {
                    iconIV.setBackgroundResource(R.drawable.herz); //"icon"+ i-1
                }
                if(i == 4) {
                    iconIV.setBackgroundResource(R.drawable.chat); //"icon"+ i-1
                }
                if(i == 5) {
                    iconIV.setBackgroundResource(R.drawable.haufen); //"icon"+ i-1
                }
                if(i == 6) {
                    iconIV.setBackgroundResource(R.drawable.kaffee); //"icon"+ i-1
                }
                if(i == 7) {
                    iconIV.setBackgroundResource(R.drawable.kamera); //"icon"+ i-1
                }
                if(i == 8) {
                    iconIV.setBackgroundResource(R.drawable.kreisel); //"icon"+ i-1
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
                linearLayout1.addView(iconIV);
                if(cnt == 4){
                    cnt = 0;
                    linearLayout.addView(linearLayout1);
                    linearLayout1 = new LinearLayout(this);
                    linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                }
            }

        }


        LevelPointsConverter levelPointsConverter = new LevelPointsConverter();
        String lvl = levelPointsConverter.convertPointsToLevel(points);
        TextView txtView = new TextView(this);
        txtView.setText("  P: " + points);
        txtView.setPadding(0,8, 0, 0);
        txtView.setTextColor(Color.WHITE);


        RelativeLayout progressBarBox = new RelativeLayout(this);
        RelativeLayout.LayoutParams progressBarBoxLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 90);
        progressBarBox.setBackgroundColor(Color.LTGRAY);
        progressBarBoxLayout.setMargins(0, 450, 0, 0);
        progressBarBoxLayout.addRule(RelativeLayout.BELOW, ivImage.getId());
        progressBarBox.setPadding(5, 5, 5, 5);
        progressBarBox.setLayoutParams(progressBarBoxLayout);

        int progress = Integer.parseInt(lvl) * ((width-10)/10);
        RelativeLayout progressBar = new RelativeLayout(this);
        RelativeLayout.LayoutParams progressBarLayout = new RelativeLayout.LayoutParams(progress, 80);
        progressBar.setBackgroundColor(getResources().getColor(R.color.hhu_blue));
        progressBar.setLayoutParams(progressBarLayout);




        //----------------
        scrollView.addView(linearLayout);
        progressBarBox.addView(progressBar);
        progressBarBox.addView(txtView);

        //---------------

        rel.addView(ivImage);
        rel.addView(statusTextView);
        rel.addView(scrollView);
        rel.addView(progressBarBox);


    }

}
