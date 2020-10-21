package com.pf.scoringsalud.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pf.scoringsalud.EjerciciosActivity;
import com.pf.scoringsalud.HomeActivity;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.Welcome;

import java.util.Calendar;

import javax.xml.transform.Result;

public class NotificationActivity extends AppCompatActivity {

    private Button btNotificacion;
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private static final int REQUEST_RESULT = 200;
    //boton notificacion

    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;
   // String GROPU_KEY = "com.pf.scoringsalud.ACTIVIDAD";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btNotificacion = findViewById(R.id.btNotificacion);
        btNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotificationChannel();
                createNotification();
            }
        });
        //Boton NOTIFICACION

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;

        hour = settings.getString("hour","");
        minute = settings.getString("minute","");

        notificationsTime = (TextView) findViewById(R.id.notifications_time);

        if(hour.length() > 0)
        {
            notificationsTime.setText(hour + ":" + minute);
        }

        findViewById(R.id.change_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NotificationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHour, finalMinute;

                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;
                        if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                        notificationsTime.setText(finalHour + ":" + finalMinute);

                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHour);
                        edit.putString("minute", finalMinute);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                        Toast.makeText(NotificationActivity.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                        Utils.setAlarm(alarmID, today.getTimeInMillis(), NotificationActivity.this);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });
    }



    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(){

        Intent resu  = new Intent(this,  EjerciciosActivity.class);
        resu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pending = PendingIntent.getActivity(this, 0, resu, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_sms_black_24dp);
        builder.setContentTitle("Tienes una Actividad");
        builder.setContentText("prueba de actividad");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pending);
        builder.setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());




    }


    //






}