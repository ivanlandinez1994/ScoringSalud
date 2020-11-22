/**
package com.pf.scoringsalud.stepCounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;


import java.math.RoundingMode;
import java.text.DecimalFormat;


public class StepCounter implements SensorEventListener {
    public static int STEP_COUNT;
    public static double DISTANCIA;
    private double magnitudePrevious = 0;

    public StepCounter() {
        SaveSteps.getLastSavedSteps();
    }
    public static void onStepsFetched(int steps){
        STEP_COUNT = steps;
    }

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
                STEP_COUNT++;
            }
            if( STEP_COUNT  % 5 == 0) {
               SaveSteps.saveCurrentSteps(STEP_COUNT);
                Log.i("ONSENSORCHANGED", STEP_COUNT+"");
            }
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            double kms =  STEP_COUNT* 0.0007;
            DISTANCIA = new Double(df.format(kms));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
*/