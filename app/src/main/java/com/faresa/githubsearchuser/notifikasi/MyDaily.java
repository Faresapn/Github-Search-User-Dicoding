package com.faresa.githubsearchuser.notifikasi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.activity.HomeActivity;

public class MyDaily extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        Intent daily_intent1 = new Intent(context, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,101,daily_intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("daily","daily");

        NotificationManager daily_notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder daily_builder = new NotificationCompat.Builder(context,"101")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_new)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_new))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.notif))
                .setAutoCancel(true);
        Notification notification = daily_builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("101", "alarmku", NotificationManager.IMPORTANCE_DEFAULT);
            daily_builder.setChannelId("101");

            if (daily_notificationManager != null) {
                daily_notificationManager.createNotificationChannel(channel);
            }
        }
        if (daily_notificationManager !=null){
            daily_notificationManager.notify(2,notification);
        }
    }
}
