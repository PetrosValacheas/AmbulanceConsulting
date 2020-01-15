package com.example.ambulanceconsulting.activities;

import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ambulanceconsulting.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody() , remoteMessage.getNotification().getClickAction());

    }

    private void showNotification(String title, String body , String clickAction) {

        Intent notiifyIntent = new Intent(clickAction);

        NotificationCompat.Builder builders = new NotificationCompat.Builder(this,"MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentText(body);

        NotificationManagerCompat manager  = NotificationManagerCompat.from(this);
        manager.notify(999, builders.build());
    }


}
