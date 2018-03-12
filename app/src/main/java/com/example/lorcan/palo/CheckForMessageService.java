package com.example.lorcan.palo;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Win10 Home x64 on 01.03.2018.
 */

public class CheckForMessageService {

    Timer timer = new Timer();
    Boolean timerCancelled = true;

    public void checkForMessageInit() {
        // do your jobs here
        System.out.println("Start Service");
        timer.schedule(new checkForMessage(), 0, 5000);
    }
    class checkForMessage extends TimerTask {
        public void run() {

            ChatMessage getMessage = new ChatMessage();
            getMessage.isMessageThere2(CheckForMessageService.this);
        }
    }

    public void stopAndRestart(){
        if(!timerCancelled){
            timer.schedule(new checkForMessage(),0, 5000);
        }else{
            timer.schedule(new checkForMessage(),0, 5000);
            timerCancelled = false;
        }
    }

    public void stopTimer(){
        timer.cancel();
        timerCancelled = true;
    }
    public void stopRestartAndNotify(){
        SendNotification sendNotification = new SendNotification();
        sendNotification.sendNotification();
        if(!timerCancelled){
            stopTimer();
            timer.schedule(new checkForMessage(),0, 5000);
        }else{
            timer.schedule(new checkForMessage(),0, 5000);
            timerCancelled = false;
        }
    }
}
