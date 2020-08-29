package com.pf.scoringsalud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

enum ProviderType {
    BASIC
}

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setup
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        setup(bundle.getString("email"),bundle.getString("provider"));
    }

    private void setup(String email, String provider){
        TextView correoElectronico = findViewById(R.id.emailTextView);
        TextView prov = findViewById(R.id.providerTextView);
        Button botonLogOut = findViewById(R.id.logOutButton);

        correoElectronico.setText(email);
        prov.setText(provider);
        botonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                onBackPressed();
            }
        });
    }
}