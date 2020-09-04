package com.pf.scoringsalud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.prefs.Preferences;

enum ProviderType {
    BASIC,
    GOOGLE
}

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setup
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        setup(bundle.getString("email"), bundle.getString("provider"));

        //Persistir Datos
        SharedPreferences.Editor preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        preferences.putString("email", bundle.getString("email"));
        preferences.putString("provider", bundle.getString("provider"));
        preferences.apply();
    }

    private void setup(String email, String provider) {
        TextView correoElectronico = findViewById(R.id.emailTextView);
        TextView prov = findViewById(R.id.providerTextView);
        Button botonLogOut = findViewById(R.id.logOutButton);

        correoElectronico.setText(email);
        prov.setText(provider);
        botonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //borrado de datos
                SharedPreferences.Editor preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
                preferences.clear();
                preferences.apply();

                FirebaseAuth.getInstance().signOut();
                onBackPressed();
            }
        });
    }
}