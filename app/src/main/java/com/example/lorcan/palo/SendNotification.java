package com.example.lorcan.palo;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;

/**
 * Created by Win10 Home x64 on 01.03.2018.
 */

public class SendNotification {

    public void sendNotification (){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplicationContext.getAppContext());
        builder.setSmallIcon(R.drawable.appicon);
        builder.setContentTitle("Palo: Nachricht");
        builder.setContentText("Du hast eine neue Nachricht bekommen!");
        Intent intent = new Intent(MyApplicationContext.getAppContext(), ChatListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyApplicationContext.getAppContext());
        stackBuilder.addParentStack(ChatListActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

    }

}
