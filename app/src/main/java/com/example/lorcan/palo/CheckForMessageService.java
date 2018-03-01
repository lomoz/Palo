package com.example.lorcan.palo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Win10 Home x64 on 01.03.2018.
 */

public class CheckForMessageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here

        return super.onStartCommand(intent, flags, startId);
    }
}
