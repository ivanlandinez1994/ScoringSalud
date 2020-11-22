/**
package com.pf.scoringsalud.stepCounter;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;

public class StepService extends Service {
    public static Boolean FLAG = false;

    private SensorManager mSensorManager;
    private StepCounter stepCounter;

    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;



    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();


    }
    @SuppressLint("InvalidWakeLockTag")
    private void startStepDetector() {
        FLAG = true;
        stepCounter = new StepCounter();

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        mSensorManager.registerListener(stepCounter,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);


        mPowerManager = (PowerManager) this
                .getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");
        mWakeLock.acquire();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FLAG = false;
        if (stepCounter != null) {
            mSensorManager.unregisterListener(stepCounter);
        }

        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
*/