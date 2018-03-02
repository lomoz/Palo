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

public class CheckForMessageService extends Service {

    Timer timer = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        System.out.println("Start Service");
        timer.schedule(new checkForMessage(), 0, 5000);
        return super.onStartCommand(intent, flags, startId);
    }
    class checkForMessage extends TimerTask {
        public void run() {

            ChatMessage getMessage = new ChatMessage();
            getMessage.isMessageThere2(CheckForMessageService.this);
        }
    }

    public void stopAndRestart(){
        timer.cancel();
        timer.schedule(new checkForMessage(),0, 5);
    }
    public void stopRestartAndNotify(){
        timer.cancel();
        SendNotification sendNotification = new SendNotification();
        sendNotification.sendNotification();
        timer.schedule(new checkForMessage(),0, 5);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}
