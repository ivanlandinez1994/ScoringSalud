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
    Sensor sensorCounter;
    Sensor sensorDetector;

    TextView tv_pasos_conteo;
    int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION},1);
        }

        tv_pasos_conteo= (TextView) findViewById(R.id.tv_pasos_conteo);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensorCounter = (Sensor) sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorDetector =(Sensor) sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

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

            tv_pasos_conteo.setText("TYPE_STEP_COUNTER: "+sensorEvent.values[0]);
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
        sm.registerListener(this, sensorCounter, sm.SENSOR_DELAY_FASTEST);
        sm.registerListener(this, sensorDetector, sm.SENSOR_DELAY_FASTEST);
    }

    private void stop() {
        sm.unregisterListener(this,sensorCounter);
        sm.unregisterListener(this,sensorDetector);
    }
}