package com.pf.scoringsalud.notifications;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Objects;

public class WorkManager extends Worker {

        public WorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, Objects.requireNonNull(workerParams));
        }



        public static void accionBase( NotificationService notiservice){


        }


        @NonNull
        @Override
    public Result doWork(){

            NotificationService noti = new NotificationService();

           // noti.onHandleIntent();



        return Result.success();
    }
}
