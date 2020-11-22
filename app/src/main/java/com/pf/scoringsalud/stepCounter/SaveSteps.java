/**
package com.pf.scoringsalud.stepCounter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.StringValueCallback;

public class SaveSteps {

    public static void saveCurrentSteps(int count) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.actualizarPuntuable(user.getEmail(), "Pasos", count, new StringValueCallback() {
            @Override
            public void onSuccess(String value) {
                Log.i("SUCCESSSAVED", value);
            }

            @Override
            public void onFailure() {

            }
        });
    }
    public static void getLastSavedSteps() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        String mail = user.getEmail();
        ap.actualizarPuntuable(user.getEmail(), "Pasos", -1, new StringValueCallback() {
            @Override
            public void onSuccess(String value) {
                StepCounter.onStepsFetched(Integer.parseInt(value));
                Log.i("LASTSAVED", value);
            }

            @Override
            public void onFailure() {
                Log.i("FAILLL", "fail");
            }
        });
    }
}
 */