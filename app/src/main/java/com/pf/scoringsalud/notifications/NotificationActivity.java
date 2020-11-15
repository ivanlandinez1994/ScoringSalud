package com.pf.scoringsalud.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pf.scoringsalud.R;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {


    private Button btNotificacion;
    private PendingIntent pendingIntent;
    //  private final static String CHANNEL_ID = "NOTIFICACION";
    // private final static int NOTIFICACION_ID = 1;


    private TextView notificationsTime;
    private TextView notificationsTimeEnd;
    private int horaInicioProgramada;
    private int minutoInicioProgramada;
    private int horaFinProgramada;
    private int minutoFinProgramada;
    private int alarmID = 1;

    private SharedPreferences settings;
    Calendar calendario = Calendar.getInstance();
    int horaActual, minutoActual;
    private int dias[];
    private Button lunes, martes, miercoles, jueves, viernes, sabado, domingo;
    private int diaActual;
    private AdminSQLite admin;
    private SQLiteDatabase bd;
    private ContentValues registro;
    boolean lunesOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        //creamos la base de datos donde alamacenamos las alarmas
        admin = new AdminSQLite(this, "Taller", null, 8);
        bd = admin.getWritableDatabase();

        diaActual = calendario.get(Calendar.DAY_OF_WEEK);
        horaActual = calendario.get(Calendar.HOUR_OF_DAY);
        minutoActual = calendario.get(Calendar.MINUTE);

/*
        String minuto_sistema,dia_sistema,hora_sistema;
        dia_sistema = String.valueOf(diaActual);
        hora_sistema = String.valueOf(horaActual);
        minuto_sistema = String.valueOf(minutoActual);
*/


        dias = new int [7];

        lunes = findViewById(R.id.lun);
        martes = findViewById(R.id.mar);
        miercoles = findViewById(R.id.mie);
        jueves =  findViewById(R.id.jue);
        viernes = findViewById(R.id.vie);
        sabado = findViewById(R.id.sab);
        domingo = findViewById(R.id.dom);


        //boton atras (vuelve a la ultima acttivity vista
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        btNotificacion = findViewById(R.id.btNotificacion);
        btNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
                llenar();
                finish();
            }
        });
        //Boton NOTIFICACION

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;

        hour = settings.getString("hour","");
        minute = settings.getString("minute","");

        notificationsTime = (TextView) findViewById(R.id.notifications_time);
        notificationsTimeEnd = (TextView) findViewById(R.id.notifications_time_end);
        if(hour.length() > 0)
        {
            notificationsTime.setText(hour + ":" + minute);
            notificationsTimeEnd.setText(hour+ ":" + minute);
        }


        findViewById(R.id.change_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hourInicio = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minuteInicio = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NotificationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHourInicio, finalMinuteInicio;

                        finalHourInicio = "" + selectedHour;
                        finalMinuteInicio = "" + selectedMinute;
                        if (selectedHour < 10) finalHourInicio = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinuteInicio = "0" + selectedMinute;

                        notificationsTime.setText(finalHourInicio + ":" + finalMinuteInicio);
                        horaInicioProgramada = Integer.parseInt(finalHourInicio);
                        Log.i("hora ", "hora"+horaInicioProgramada);
                        minutoInicioProgramada = Integer.parseInt(finalMinuteInicio);


                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHourInicio);
                        edit.putString("minute", finalMinuteInicio);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                    }
                }, hourInicio, minuteInicio, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });

        findViewById(R.id.change_notificationFin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hourFin = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minuteFin = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NotificationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHourFin, finalMinuteFin;

                        finalHourFin = "" + selectedHour;
                        finalMinuteFin = "" + selectedMinute;
                        if (selectedHour < 10) finalHourFin = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinuteFin = "0" + selectedMinute;
                        notificationsTimeEnd.setText(finalHourFin + ":" + finalMinuteFin);
                        horaFinProgramada = Integer.parseInt(finalHourFin);
                        minutoFinProgramada = Integer.parseInt(finalMinuteFin);


                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHourFin);
                        edit.putString("minute", finalMinuteFin);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                    }
                }, hourFin, minuteFin, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });


        validar();
        servicio();

    }



    public void servicio(){
        try{
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            Log.i("Exeption servicio", "intent de servicio");
            final PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this,AlarmReceiver.REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            Log.i("Exeption servicio", "pending de servicio");
            long firstMillis = System.currentTimeMillis();
            Log.i("Exeption servicio", "hora "+firstMillis);
            int intervalMillis = 1000*5;
            System.out.println();
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,firstMillis,intervalMillis, pendingIntent);


        }catch(IllegalArgumentException e ){

            Log.i("Exeption servicio", e.getMessage());

        }
    }






    private void validar(){

        lunes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(dias[1]==0){
                    lunes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[1]=2;
                }else{
                    lunes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[1]=0;
                }
            }
        });
        martes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dias[2]==0){
                    martes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[2]=3;
                }else{
                    martes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[2]=0;
                }




            }
        });
        miercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[3]==0){
                    miercoles.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[3]=4;
                }else{
                    miercoles.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[3]=0;
                }



            }
        });
        jueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[4]==0){
                    jueves.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[4]=5;
                }else{
                    jueves.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[4]=0;
                }
            }
        });
        viernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[5]==0){
                    viernes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[5]=6;
                }else{
                    viernes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[5]=0;
                }
            }
        });
        sabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[6]==0){
                    sabado.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[6]=7;
                }else{
                    sabado.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[6]=0;
                }
            }
        });
        domingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[0]==0){
                    domingo.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[0]=1;
                }else{
                    domingo.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[0]=0;
                }
            }
        });

    }

    public void llenar() {
        try {


            AdminSQLite admin = new AdminSQLite(this, "Taller", null, 8);
            admin.onUpgrade(bd,0,0);
            bd = admin.getReadableDatabase();
            bd = admin.getWritableDatabase();

            for (int i = 0; i<dias.length; i++){
                int d = dias[i];
                registro = new ContentValues();
                registro.put("encabezado", "Notificacion");
                registro.put("dia", d);
                registro.put("horaIni", horaInicioProgramada );
                registro.put("minutoIni", minutoInicioProgramada);
                registro.put("horaFin", horaFinProgramada );
                registro.put("minutoFin", minutoFinProgramada);

                bd.insert("alarma", null, registro);
                Log.i("hora en llenar ", "hora"+registro.toString());

            }
            bd.close();

        }catch(Exception e){
            Toast.makeText(NotificationActivity.this, e.getMessage(),Toast.LENGTH_SHORT);
            bd.close();
        }


    }


}


/* private void createNotification(){
        Intent intentActividad  = new Intent(this,  EjerciciosActivity.class);
        intentActividad.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //DEfinimos la nueva actividad como tarea
        PendingIntent pendingActividad = PendingIntent.getActivity(this, 0, intentActividad, 0);
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_sms_black_24dp);
            builder.setContentTitle("Realiza una Actividad");
            builder.setContentText("Llego la hora de hacer una actividad");
            builder.setColor(Color.BLUE);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setLights(Color.MAGENTA, 1000, 1000);
            builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingActividad);
            //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext())
            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(NOTIFICACION_ID, builder.build());
        }catch (Exception e ){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }*/


//
/*
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NOTIFICACION_ACTIVIDAD";
            String description = "Channel para remiender de Actividad";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NOTIFICACION", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            /*
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
*/