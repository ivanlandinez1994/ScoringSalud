/*****package com.pf.scoringsalud.stepCounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.StringValueCallback;
import com.pf.scoringsalud.stepCounter.StepCounter;
import com.pf.scoringsalud.stepCounter.StepService;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class PasosActivitySegundoPlano extends AppCompatActivity {

    /**SensorManager sm;
     Sensor sensor;
     private double magnitudePrevious = 0;
     private Integer stepCount = 0;**/
  /******  TextView tv_pasos_conteo;
    TextView tv_Km;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_pasos_conteo.setText(StepCounter.STEP_COUNT+"");

            Log.i("HANDLER", StepCounter.STEP_COUNT+"");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);
        /**
         if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
         //ask for permission
         requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, Sensor.TYPE_ACCELEROMETER);
         }*/
        //Iniciar Step Counter
     /******   if(isMyServiceRunning(StepService.class, getApplicationContext())) {
            Log.i("YACORRE", "YACORRE");
        }else{
            Intent serviceIntent =  new Intent(getApplicationContext(), StepService.class);
            startService(serviceIntent);
            Log.i("NOCORRIA", "NOCORRIA");
        }

        tv_pasos_conteo= (TextView) findViewById(R.id.tv_pasos_conteo);
        tv_Km = (TextView) findViewById(R.id.tvKm);
        tv_pasos_conteo.setText(StepCounter.STEP_COUNT+"");
        tv_Km.setText(String.valueOf(StepCounter.DISTANCIA));
        Log.i("ONCREATE", StepCounter.STEP_COUNT+"");
        /**sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
         sensor = (Sensor) sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
         //sensor =(Sensor) sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         ApiPuntuable ap = new ApiPuntuable();
         ap.actualizarPuntuable(user.getEmail(), "Pasos", -1, getApplicationContext(), new StringValueCallback() {
        @Override
        public void onSuccess(String value) {
        tv_pasos_conteo.setText(value);
        stepCount = Integer.parseInt(value);
        }

        @Override
        public void onFailure() {

        }
        });*/

   /****     //boton antras (vuelve a la ultima acttivity vista
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        /**
         SensorEventListener stepDetector = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent!= null){
        float x_acceleration = sensorEvent.values[0];
        float y_acceleration = sensorEvent.values[1];
        float z_acceleration = sensorEvent.values[2];

        double magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
        double magnitudeDelta = magnitude-magnitudePrevious;
        magnitudePrevious = magnitude;

        if (magnitudeDelta > 6){
        stepCount++;
        }
        if( stepCount  % 5 == 0) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.actualizarPuntuable(user.getEmail(), "Pasos", stepCount, getApplicationContext(), new StringValueCallback() {
        @Override
        public void onSuccess(String value) {

        }

        @Override
        public void onFailure() {

        }
        });
        }
        tv_pasos_conteo.setText(stepCount.toString());
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        double kms = stepCount* 0.0007;
        double kmsFormatted = new Double(df.format(kms));
        tv_Km.setText(String.valueOf(kmsFormatted));
        }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
        };

         sm.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
         */
   /** }

    protected void onPause() {
        super.onPause();

    }

    protected void onStop() {
        super.onStop();

    }

    protected void onResume() {
        super.onResume();
        tv_pasos_conteo.setText(StepCounter.STEP_COUNT+"");
        tv_Km.setText(String.valueOf(StepCounter.DISTANCIA));
        Log.i("ONRESUMEACTIVITY", StepCounter.STEP_COUNT+"");
    }




    protected static boolean isMyServiceRunning(Class<?> serviceClass, Context cont) {
        ActivityManager manager = (ActivityManager) cont.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = manager
                .getRunningServices(Integer.MAX_VALUE);

        if (list == null || list.isEmpty()) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : list) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
*/