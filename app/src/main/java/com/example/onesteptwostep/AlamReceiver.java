package com.example.onesteptwostep;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class AlamReceiver extends BroadcastReceiver {
    private final String notiChannelID = "CHANNEL_ID";

    @Override
    public void onReceive(Context con, Intent intent){

        NotificationManager notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notiIntent = new Intent(con, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(con,0,notiIntent,0);

        notiIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(con,"default");
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notiBuilder.setSmallIcon(R.drawable.calendaricon);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notiCannel = new NotificationChannel(notiChannelID,notiChannelID,importance);
            notificationManager.createNotificationChannel(notiCannel);
        }

        notiBuilder.setContentTitle("Go Step!")
                .setAutoCancel(true)
                .setContentText("오늘의 그린 발자국을 남겨주세요!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);

        notificationManager.notify(1,notiBuilder.build());

        Calendar nextNotifyTime = Calendar.getInstance();
        nextNotifyTime.add(Calendar.DATE,1);
    }


}
