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
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        }else{
            Toast.makeText(MyApplicationContext.getAppContext(), "Es ist leider kein Profilbild vorhanden.", Toast.LENGTH_SHORT).show();
            ProfilActivity profilActivity = this;
            profilActivity.finish();
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


        rel.addView(ivImage);
        rel.addView(statusTextView);


    }

}
