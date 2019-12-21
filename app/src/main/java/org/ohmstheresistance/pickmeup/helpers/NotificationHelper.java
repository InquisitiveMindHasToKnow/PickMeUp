package org.ohmstheresistance.pickmeup.helpers;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import android.support.v4.app.NotificationCompat;

import org.ohmstheresistance.pickmeup.R;
import org.ohmstheresistance.pickmeup.activities.TestNotificationActivity;

public class NotificationHelper extends ContextWrapper {
    public static final String NOTIFICATION_CHANNEL_ID = "notificationChannelID";
    public static final String NOTIFICATION_CHANNEL_NAME = "Notification Channel Name";

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent notificationIntent = new Intent(getApplicationContext(), TestNotificationActivity.class);
        notificationIntent.putExtra("FromNotification", true);

        PendingIntent notificationPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Pick Me Up")
                .setContentText("You need that boost of inspiration? We've got you!")
                .setContentIntent(notificationPendingIntent)
                .setVibrate(new long[]{250, 250, 250, 250})
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.selfmotivated);
}

}