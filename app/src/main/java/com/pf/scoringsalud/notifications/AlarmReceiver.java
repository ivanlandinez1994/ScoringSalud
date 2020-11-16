package com.pf.scoringsalud.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.pf.scoringsalud.activity.EjerciciosActivity;
import com.pf.scoringsalud.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver{
        public static final int REQUEST_CODE = 12345;
        private NotificationManager notificationManager;
        private final int NOTIFICATION_ID = 1;
        private AdminSQLite admin;
        private Cursor fila;
        private SQLiteDatabase database;
        private String alarma, descripcion, titulo;
        private int hora, min,dia;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentActividad  = new Intent(context,  ServiceRecordatorio.class);
        Log.i("Exeption servicio", "intent de servicioRecordatorio"+ intentActividad.toString());
        context.startService(intentActividad);
        Calendar calendario = Calendar.getInstance();

        String minuto_sistema,dia_sistema,hora_sistema;
        dia = calendario.get(Calendar.DAY_OF_WEEK);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        Log.i("actual : ", "actual"+hora);

        admin = new AdminSQLite(context, "Taller", null, 8);
        database = admin.getWritableDatabase();
        Log.i("registro : ", "registro");
        if(database!=null) {
            fila = database.rawQuery("SELECT * FROM alarma WHERE dia= @dia AND horaIni = @hora AND minutoIni =@min " , null);
            Log.i("registro : ", "registro"+fila.toString()); //verificar ingreso en condicional
            if(fila.moveToFirst()){
                alarma=fila.getString(0);
                triggerNotification(context);
            }

        }
        database.close();
    }

    private void triggerNotification(Context context){
        Intent notificationIntent = new Intent(context, EjerciciosActivity.class);
        notificationIntent.putExtra("pomodoro","Pomodoro");
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent  = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.drawable.ic_sms_black_24dp);
        builder.setContentTitle("Realiza una Actividad");
        builder.setContentText("Llego la hora de hacer una actividad");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }


}


 /*
        intentActividad.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //DEfinimos la nueva actividad como tarea
        PendingIntent pendingActividad = PendingIntent.getActivity(context, 0, intentActividad, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NOTIFICACION");
        builder.setSmallIcon(R.drawable.ic_sms_black_24dp);
        builder.setContentTitle("Realiza una Actividad");
        builder.setContentText("Llego la hora de hacer una actividad");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingActividad);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
            */

        /*
        Intent service1 = new Intent(context, NotificationService.class);
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        Log.d("WALKIRIA", " ALARM RECEIVED!!!");
        Toast.makeText(context, "ingreso alarmreceiver" , Toast.LENGTH_LONG).show();  */
