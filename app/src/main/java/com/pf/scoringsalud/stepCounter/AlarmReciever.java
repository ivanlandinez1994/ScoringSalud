/**
package com.pf.scoringsalud.stepCounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, StepService.class);
        context.startService(service);
    }
}
*/