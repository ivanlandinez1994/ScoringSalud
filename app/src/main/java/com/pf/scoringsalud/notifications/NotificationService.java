package com.pf.scoringsalud.notifications;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.pf.scoringsalud.R;
import com.pf.scoringsalud.activity.EjerciciosActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NotificationService extends IntentService {

    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    private Notification notification;
    private AdminSQLite admin;
    private Cursor fila;
    private SQLiteDatabase database;
    private int hora, min, dia;
    private ContentValues actualizacion;


    public NotificationService(String name) {
        super(name);
    }

    public NotificationService() {
        super("SERVICE");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override



    protected void onHandleIntent(Intent intent2) {


        Calendar calendario = Calendar.getInstance();
        String NOTIFICATION_CHANNEL_ID = getApplicationContext().getString(R.string.app_name);
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, EjerciciosActivity.class); //colocamos el Activity al cual va a viajar nuestra notificacion, esta variable la implementamos en la notificacion abajo
        mIntent.putExtra("notificacion","Pomodoro");
        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        String message = getString(R.string.new_notification);



/*
        extraemos las horas del dia actual desde la clase Calendario y armamos las hora actual como String, al igual hacemos con el dia
 */
        String minuto_sistema, dia_sistema, hora_sistema;
        dia = calendario.get(Calendar.DAY_OF_WEEK);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        minuto_sistema = ""+min;
        hora_sistema = ""+hora;
        dia_sistema =""+dia;
        //completaos la validacion del string con la hora para que no queden sin 0 antes de la hora ej: 09:05
        if (hora < 10) hora_sistema = "0"+ hora_sistema;
        if (min < 10) minuto_sistema = "0" + minuto_sistema;
        hora_sistema = hora_sistema+":"+minuto_sistema;
        // imprimimos la hora actual del sistema para validarlo
        Log.i("actual : ", "actual " + hora_sistema);

        admin = new AdminSQLite(this, "Taller", null, 8);
        database = admin.getWritableDatabase();


        //realizamos el QUERY correspondiente para que valide que la hora actual del sistema y tome la alarma que corresponde al dia y el rango horario
        //este es el query ............ String query ="SELECT * FROM alarma WHERE dia='"+dia_sistema+"' AND'"+hora_sistema+"' BETWEEN horaIni AND horaFin ";

        String query ="SELECT * FROM alarma WHERE dia='"+dia_sistema+"' AND'"+hora_sistema+"' BETWEEN horaIni AND horaFin ";
        Log.i("consulta : ", "toma el query "+query ); // imprimos para control por el LOGCAT y se verifica el query con los valores que tiene en las variables

        if (database != null) {
            fila = database.rawQuery(query, null); // utilizamos el cursor para posicionar el resultado del query

                    if (fila.moveToFirst()) {

                        // almacenamos la hora actual del sistema despues del proceso en una variable del mismo tipo que luego vamos a convertir a tipo DATE para poder manipularla
                        //esa variable la incrementaremos con los metodos correspondientes en la cantidad de minutos que queremos que suene
                        //luego la volvemos a convertir en el tipo string y la introducimos en la tabla de SQLITE con un update y la condicion de que el dia sea el mismo !

                                String mihora = hora_sistema;
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm"); // aplicamos esta clase para hacer un cast en ese formato de fecha, solo con HORAS Y MINUTOS, NO MANEJAMOS SEGUNDOS

                                //implementamos un manejo de excepcion en caso de posible problemas con la conversion
                                Date d = null;
                                        try {
                                            d = df.parse(mihora);
                                            Log.i("mi hora : ", "toma el query "+d );
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                //seteamos la hora actual en esa hora para calendario
                                calendario.setTime(d);

                                //aumentamos la en la cantidad que deseeamos
                                calendario.add(Calendar.MINUTE, 3); //este parametro es el valor que queremos aumentar, intervalos de 10 0 20 min etc
                                //almacenamos la nueva hora con el incremento, nuevamente en el string para introducirlo en la tabla
                                String nuevahora = df.format(calendario.getTime());
                                //verificamos con un Log los valores que actualizamos
                                 Log.i("registro : ", "el incremento : "+nuevahora);

                                 //hacemos el update de la horaIni correspondiente al dia

                                         actualizacion = new ContentValues();
                                         actualizacion.put("horaIni", nuevahora);
                                         database.update("alarma", actualizacion, "dia ='"+dia_sistema+"' ", null);

                                         //hacemos la creacion del proceso de notificacion, incluyendo el channel, id y las caracteristicas de la misma


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            final int NOTIFY_ID = 0; // ID of notification
                            String id = NOTIFICATION_CHANNEL_ID; // default_channel_id
                            String title = NOTIFICATION_CHANNEL_ID; // Default Channel
                            PendingIntent pendingIntent;
                            NotificationCompat.Builder builder;
                            NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            if (notifManager == null) {
                                notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            }
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                            if (mChannel == null) {
                                mChannel = new NotificationChannel(id, title, importance);
                                mChannel.enableVibration(true);
                                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                notifManager.createNotificationChannel(mChannel);
                            }
                            builder = new NotificationCompat.Builder(context, id);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentTitle(getString(R.string.app_name)).setCategory(Notification.CATEGORY_SERVICE)
                                    .setSmallIcon(R.drawable.gym)   // required
                                    .setContentText("Llego la hora de una nueva actividad")
                                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.gym))
                                    .setDefaults(Notification.DEFAULT_ALL) //este metodo esta deprecado para api26
                                    .setAutoCancel(true)
                                    .setSound(soundUri)
                                    .setContentIntent(pendingIntent)
                                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                            Notification notification = builder.build();
                            notifManager.notify(NOTIFY_ID, notification);

                            startForeground(1, notification);

                        } else {
                            pendingIntent = PendingIntent.getActivity(context, 1, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification = new NotificationCompat.Builder(this)
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(R.drawable.ic_stat_ic_notification)
                                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_stat_ic_notification))
                                    .setSound(soundUri)
                                    .setAutoCancel(true)
                                    .setContentTitle(getString(R.string.app_name)).setCategory(Notification.CATEGORY_SERVICE)
                                    .setContentText(message).build();
                            notificationManager.notify(NOTIFICATION_ID, notification);
                        }



                    }

        }
        database.close();


    }
}



