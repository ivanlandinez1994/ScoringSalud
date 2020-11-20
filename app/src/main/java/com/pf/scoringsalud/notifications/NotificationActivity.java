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
    private TextView notificationsTime;
    private TextView notificationsTimeEnd;
    private int horaInicioProgramada;
    private int minutoInicioProgramada;
    private int horaFinProgramada;
    private int minutoFinProgramada;
    private int alarmID = 1;
    private String finalInicio;
    private String finalFin;
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
                        finalInicio = finalHourInicio + ":" + finalMinuteInicio;
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
                        finalFin = finalHourFin + ":" + finalMinuteFin;
                        horaFinProgramada = Integer.parseInt(finalHourFin);
                        minutoFinProgramada = Integer.parseInt(finalMinuteFin);


                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", "00");
                        edit.putString("minute","00");

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
            final PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this,AlarmReceiver.REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            long firstMillis = System.currentTimeMillis();
            int intervalMillis = 1* 120 * 1000;
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
                    lunes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[1]=2;
                }else{
                    lunes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[1]=0;
                }
            }
        });
        martes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dias[2]==0){
                    martes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[2]=3;
                }else{
                    martes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[2]=0;
                }




            }
        });
        miercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[3]==0){
                    miercoles.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[3]=4;
                }else{
                    miercoles.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[3]=0;
                }
            }
        });
        jueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[4]==0){
                    jueves.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[4]=5;
                }else{
                    jueves.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[4]=0;
                }
            }
        });
        viernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[5]==0){
                    viernes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[5]=6;
                }else{
                    viernes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[5]=0;
                }
            }
        });
        sabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[6]==0){
                    sabado.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[6]=7;
                }else{
                    sabado.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                    dias[6]=0;
                }
            }
        });
        domingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dias[0]==0){
                    domingo.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                    dias[0]=1;
                }else{
                    domingo.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
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
                    registro.put("horaIni", finalInicio);
                    registro.put("horaFin", finalFin );
                    registro.put("inicioFijo", finalInicio);
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

