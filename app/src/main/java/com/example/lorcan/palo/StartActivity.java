package com.example.lorcan.palo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by paul on 31.07.17.
 */

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //wait for 3secs to show Symbol

        Thread thread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(3000);
                    }
                }
                catch(InterruptedException ex){
                }

                startActivity();
            }
        };

        thread.start();
    }


    public void startActivity(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
