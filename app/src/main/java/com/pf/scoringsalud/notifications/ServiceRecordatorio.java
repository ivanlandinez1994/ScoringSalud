package com.pf.scoringsalud.notifications;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
public class ServiceRecordatorio extends IntentService {


        public ServiceRecordatorio() {
            super("ServiceRecordatorio");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            //Do the task here
            Log.i("MyTestService", "Servicio ejecutandose. Recordatorios");
        }
    }

