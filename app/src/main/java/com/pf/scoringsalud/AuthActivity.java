package com.pf.scoringsalud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //Analitycs
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("message","integracion firebase completa");
        analytics.logEvent("InitScreen", bundle);

        //setup
        setup();

    }

    private void setup(){
        Button botonRegistro = findViewById(R.id.logOutButton);
        Button botonLogin = findViewById(R.id.loginButton);
        final TextView email = findViewById(R.id.emailEditText);
        final TextView pass = findViewById(R.id.passwordEditText);
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(pass.getText().toString())  ){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                showHome(task.getResult().getUser().getEmail(), ProviderType.BASIC);
                            }else{
                                showAlert(task.getException().toString());
                            }
                        }
                    });
                }
            }
        });
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(pass.getText().toString())  ){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                showHome(task.getResult().getUser().getEmail(), ProviderType.BASIC);
                            }else{
                                showAlert(task.getException().toString());
                            }
                        }
                    });
                }
            }
        });
    }

    private void showAlert(String e){
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);
        builder.setTitle("Error");
        builder.setMessage(e);
        builder.setPositiveButton("OK", null);//second parameter used for onclicklistener
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showHome(String email, ProviderType provider){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("provider",provider.name());
        startActivity(intent);
    }
}