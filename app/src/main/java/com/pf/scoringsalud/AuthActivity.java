package com.pf.scoringsalud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {
    private int GOOGLE_SIGN_IN = 100;
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
        session();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayout authLayout = findViewById(R.id.authLayout);
        authLayout.setVisibility(View.VISIBLE);
    }

    private void session(){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = preferences.getString("email",null);
        String provider = preferences.getString("provider",null);
        if(email != null && provider!= null ){
            LinearLayout authLayout = findViewById(R.id.authLayout);
            authLayout.setVisibility(View.INVISIBLE);
            showHome(email,ProviderType.valueOf(provider));
        }
    }

    private void setup(){
        Button botonRegistro = findViewById(R.id.logOutButton);
        Button botonLogin = findViewById(R.id.loginButton);
        final Button googleButton = findViewById(R.id.googleButton);
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

        //Config para Login Google
        GoogleSignInOptions googleConfig = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        final GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleConfig);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut();
                startActivityForResult(googleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null ){
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential);
                    if (task.isSuccessful()){
                        showHome(task.getResult().getEmail(), ProviderType.GOOGLE);
                    }else{
                        showAlert(task.getException().toString());
                    }
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showAlert(e.toString());
                // ...
            }
        }
    }


}