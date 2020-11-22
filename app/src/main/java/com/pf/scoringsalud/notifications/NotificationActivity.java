package com.pf.scoringsalud.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
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
    private SQLiteDatabase bd, bdr;
    private ContentValues registro;
    private String programadasAntesIni, getProgramadasAntesFin;
    private static Cursor fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        //creamos la base de datos donde alamacenamos las alarmas
        admin = new AdminSQLite(this, "Taller", null, 8);
        bd = admin.getWritableDatabase();
        bdr = admin.getReadableDatabase();

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


        try {

           for(int i=0; i<dias.length;i ++){
               String id = ""+(i+1);
               String query = "SELECT dia FROM alarma where idal = '"+id+"' ";
               fila = bdr.rawQuery(query, null);
               fila.moveToFirst();
               Log.i("query dia", query);
               final String di = fila.getString(0);
               Log.i(" dia", di);
               switch(id){
                   case "2":
                       if(di.equals("0")){
                           dias[1]=0;
                           lunes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                           Log.i("lunes ", "No seleccionado segun la base de datos");
                       }else{
                           lunes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                           dias[1]=2;
                           Log.i("lunes ", "seleccionado");
                       }

                   case "3":

                       if(di.equals("0")){

                         dias[2]=0;
                         martes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                       }else{
                           martes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                           dias[2]=3;
                       }
                       case "4":

                           if(di.equals("0")){

                               dias[3]=0;
                               miercoles.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                           }else{
                               miercoles.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                               dias[3]=4;
                           }
                   case "5":

                       if(di.equals("0")){

                          dias[4]=0;
                           jueves.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                       }else{
                           jueves.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                           dias[4]=5;
                       }
                   case "6":
                       if(di.equals("0")){

                          dias[5]=0;
                           viernes.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                       }else{
                           viernes.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                           dias[5]=6;
                       }
                   case "7":

                       if(di.equals("0")){
                          dias[6]=0;
                           sabado.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                       }else{
                           sabado.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                           dias[6]=7;
                       }
                   case "1":

                       if(di.equals("0")){

                           dias[0]=0;
                           domingo.setBackground(getResources().getDrawable(R.drawable.rounded_accent));
                       }else{
                           domingo.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                           dias[0]=1;
                       }
             }
           }

        }catch(CursorIndexOutOfBoundsException e  ){

        }

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

        notificationsTime = (TextView) findViewById(R.id.notifications_time);
        notificationsTimeEnd = (TextView) findViewById(R.id.notifications_time_end);
        try {

            fila = bdr.rawQuery("SELECT horaIni FROM alarma where dia = 1", null);
            fila.moveToFirst();
            programadasAntesIni = fila.getString(0);
            notificationsTime.setText(programadasAntesIni);
            fila.moveToNext();
            Cursor f = bdr.rawQuery("SELECT horaFin FROM alarma where dia = 1", null);
            f.moveToFirst();
            getProgramadasAntesFin = f.getString(0);
            f.moveToNext();
            Log.i("Exeption servicio", programadasAntesIni + " y " + getProgramadasAntesFin);
            notificationsTimeEnd.setText(getProgramadasAntesFin);
        }catch (CursorIndexOutOfBoundsException e){
            Log.i("Exeption servicio", e.getMessage());
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
                        finalInicio = finalHourInicio + ":" + finalMinuteInicio;
                        notificationsTime.setText(finalInicio);
                        horaInicioProgramada = Integer.parseInt(finalHourInicio);
                        Log.i("hora ", "hora"+horaInicioProgramada);
                        minutoInicioProgramada = Integer.parseInt(finalMinuteInicio);
                        Calendar today = Calendar.getInstance();
                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);


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
                        finalFin = finalHourFin + ":" + finalMinuteFin;
                        notificationsTimeEnd.setText(finalFin);
                        horaFinProgramada = Integer.parseInt(finalHourFin);
                        minutoFinProgramada = Integer.parseInt(finalMinuteFin);
                        Calendar today = Calendar.getInstance();
                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);
                    }
                }, hourFin, minuteFin, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });

        validar();
        servicio();
        try {

            Cursor fila = bdr.rawQuery("SELECT horaIni FROM alarma ", null);
            fila.moveToFirst();
            programadasAntesIni = fila.getString(0);
            notificationsTime.setText(programadasAntesIni);
            fila.moveToNext();
            fila = bdr.rawQuery("SELECT horaFin FROM alarma", null);
            fila.moveToFirst();
            getProgramadasAntesFin = fila.getString(0);
            fila.moveToNext();
            Log.i("Exeption servicio", programadasAntesIni + " y " + getProgramadasAntesFin);
            notificationsTimeEnd.setText(getProgramadasAntesFin);
        }catch (CursorIndexOutOfBoundsException e){
            Log.i("Exeption servicio", e.getMessage());
        }

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

