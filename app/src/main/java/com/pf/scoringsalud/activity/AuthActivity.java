package com.pf.scoringsalud.activity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.UserCancellationException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiUsuario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pf.scoringsalud.api.infraestructura.Reporte;
import com.pf.scoringsalud.api.infraestructura.UserValueCallback;
import com.pf.scoringsalud.notifications.AdminSQLite;
import com.pf.scoringsalud.notifications.NotificationActivity;
import com.pf.scoringsalud.user.Domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AuthActivity extends AppCompatActivity implements OnFailureListener {
    private int GOOGLE_SIGN_IN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //Analitycs
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("message", "integracion firebase completa");
        analytics.logEvent("InitScreen", bundle);

        //setup
        setup();
        session();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConstraintLayout authLayout = findViewById(R.id.authLayout);
        authLayout.setVisibility(View.VISIBLE);
    }

    private void session() {
        Log.i("Session","Sesion");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = preferences.getString("email", null);
        String provider = preferences.getString("provider", null);

        if(user!=null && email==null){
            Log.i("USer", user.getEmail());
            email=user.getEmail();
            provider=ProviderType.GOOGLE.toString();
        }
        if (email != null && provider != null) {
            Log.i("email and provider 79", email);
            ConstraintLayout authLayout = findViewById(R.id.authLayout);
            authLayout.setVisibility(View.INVISIBLE);
            showHome(email, ProviderType.valueOf(provider));
        }
    }

    private void setup() {
        //Button botonRegistro = findViewById(R.id.logOutButton);
        Button botonLogin = findViewById(R.id.loginButton);
        final TextView email = findViewById(R.id.emailEditText);
        final TextView pass = findViewById(R.id.passwordEditText);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(pass.getText().toString())) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("task successful line 105:", task.getResult().getUser().getEmail());
                                showHome(task.getResult().getUser().getEmail(), ProviderType.BASIC);
                            } else {
                                showAlert(task.getException().toString());
                            }
                        }
                    });
                }
            }
        });

        googleLogin();
    }

    private void googleLogin(){
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        final Button googleButton = findViewById(R.id.googleButton);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            GOOGLE_SIGN_IN);
                }catch (Exception e){
                    Log.i("Exception on click",e.toString());
                }


                /*METODO ANTERIOR ON CLICK PARA GOOGLE LOGIN
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
                });*/
            }
        });
    }

    private void showAlert(String e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);
        builder.setTitle("Error");
        builder.setMessage(e);
        builder.setPositiveButton("OK", null);//second parameter used for onclicklistener
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showHome(final String email, ProviderType provider) {
        try{
            Log.i("it's home line 143:", email);
            ApiUsuario usuarioApi = new ApiUsuario();
            usuarioApi.getUsuario(email, new UserValueCallback() {
                @Override
                public void onSuccess(User value) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("usuario", value);
                    Log.i("NO NULO: ", "user not null");
                    Toast.makeText(getApplicationContext(), "Bienvenid@: "+value.getNombre(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                @Override
                public void onFailure() {
                    Log.i("onFailure", "onFailure 176 - AuthActivity");
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            });
            destActivity(email);
        }catch(NullPointerException e){
            Log.i("Exception 146:", e.toString());
        }
    }

    private void destActivity(final String email){
        ApiUsuario usuarioApi = new ApiUsuario();
        //usuarioApi.getUsuario(email, HomeActivity.class, AuthActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            try{
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (resultCode == RESULT_OK) {
                    // Successfully signed in
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.i("task successful line 167:", user.getEmail());
                    showHome(user.getEmail(), ProviderType.GOOGLE);
                    // ...
                } else {
                    Log.i("OnActivityResult task not successful", Integer.toString(requestCode));
                    Toast.makeText(this,"Ocurrio un error: "+response.getError().getErrorCode(),Toast.LENGTH_LONG);
                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // response.getError().getErrorCode() and handle the error.
                    // ...
                }
            }catch(Exception e){
                Log.i("Exception response",e.toString());
            }
        }
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        int errorCode = ((UserCancellationException) exception).getErrorCode();
        String errorMessage = exception.getMessage();
        Log.i("Exception on Failure","Error msg: "+errorMessage+", code: "+errorCode);
        // test the errorCode and errorMessage, and handle accordingly
    }
}