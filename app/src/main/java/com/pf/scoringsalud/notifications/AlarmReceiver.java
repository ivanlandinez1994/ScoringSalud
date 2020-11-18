package com.pf.scoringsalud.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver{
        public static final int REQUEST_CODE = 12345;


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentActividad = new Intent(context, NotificationService.class);
        context.startService(intentActividad);
        Calendar calendario = Calendar.getInstance();

    }
}

