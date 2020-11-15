package com.pf.scoringsalud.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pf.scoringsalud.R;

public class PasosActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    Sensor sensor;
    TextView tv_pasos_conteo;
    int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION},Sensor.TYPE_STEP_COUNTER);
        }

        tv_pasos_conteo= (TextView) findViewById(R.id.tv_pasos_conteo);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensor = (Sensor) sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }else if(sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null){
            sensor =(Sensor) sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        }

        //boton antras (vuelve a la ultima acttivity vista
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensor == null){
            tv_pasos_conteo.setText("NULL"+sensorEvent.values[0]);
        } else if(sensor==sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)){
            tv_pasos_conteo.setText("TYPE_STEP_COUNTER: "+sensorEvent.values[0]);
        }else{
            tv_pasos_conteo.setText("TYPE_STEP: "+sensorEvent.values[0]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPause() {
        stop();
        super.onPause();
    }

    @Override
    public void onResume() {
        start();
        super.onResume();
    }

    private void start() {
        sm.registerListener(this, sensor, sm.SENSOR_DELAY_FASTEST);
    }

    private void stop() {
        sm.unregisterListener(this);
    }
}