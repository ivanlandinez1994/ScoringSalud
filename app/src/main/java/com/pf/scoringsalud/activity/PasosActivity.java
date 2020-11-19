package com.pf.scoringsalud.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.StringValueCallback;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class PasosActivity extends AppCompatActivity {

    SensorManager sm;
    Sensor sensor;
    private double magnitudePrevious = 0;
    private Integer stepCount = 0;
    TextView tv_pasos_conteo;
    TextView tv_Km;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, Sensor.TYPE_ACCELEROMETER);
        }

        tv_pasos_conteo= (TextView) findViewById(R.id.tv_pasos_conteo);
        tv_Km = (TextView) findViewById(R.id.tvKm);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
        });

        //boton antras (vuelve a la ultima acttivity vista
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
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
                                Log.i("COUNT", value);
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
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }


    /**
    private void start() {
        sm.registerListener(this, sensor, sm.SENSOR_DELAY_FASTEST);
    }

    private void stop() {
        sm.unregisterListener(this);
    }**/
}