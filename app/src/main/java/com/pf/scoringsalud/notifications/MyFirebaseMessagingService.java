package com.pf.scoringsalud.notifications;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pf.scoringsalud.HomeActivity;
import com.pf.scoringsalud.R;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        if (data.size()>0){
            String title = data.get("title");
            String msg = data.get("body");

            sendNotification(title, msg);
        }else {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            String title = notification.getTitle();
            String msg = notification.getBody();

            sendNotification(title, msg);
        }
    }

    private void sendNotification(String title, String msg) {
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NotificationConfig.NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationConfig nc = new NotificationConfig(this, NotificationConfig.CHANNEL_ID_NOTIFICATIONS);
        nc.build(R.drawable.ic_launcher_foreground, title, msg, pendingIntent);
        nc.addChannel("Notificacion", NotificationManager.IMPORTANCE_DEFAULT);
        nc.createChannelGroup(NotificationConfig.CHANNEL_GROUP_GENERAL, R.string.notification_channel_group_general);
        nc.show(NotificationConfig.NOTIFICATION_ID);
    }


}