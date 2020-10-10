package com.pf.scoringsalud;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pf.scoringsalud.User.User;
import com.pf.scoringsalud.interfaces.UserApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    String userMail;
    FirebaseUser userFirebase;
    EditText userDni;
    EditText userNombre;
    EditText userApellido;
    EditText userEdad;
    EditText userPuesto;
    Button btnRegister;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userFirebase = FirebaseAuth.getInstance().getCurrentUser();
        userMail = userFirebase.getEmail();
        userDni = findViewById(R.id.userDni);
        userNombre = findViewById(R.id.userNombre);
        userApellido = findViewById(R.id.userApellido);
        userEdad = findViewById(R.id.userEdad);
        userPuesto = findViewById(R.id.userPuesto);
        btnRegister = findViewById(R.id.btnRegistro);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User();
                user.setMail(userMail);
                user.setDni(userDni.getText().toString());
                user.setNombre(userNombre.getText().toString());
                user.setApellido(userApellido.getText().toString());
                user.setEdad(userEdad.getText().toString());
                user.setPuesto(userPuesto.getText().toString());
                user.setPuntos(0);
                registrar(user);
            }
        });
    }

    private void registrar(User user){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.120:8081/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        final UserApi userApi = retrofit.create(UserApi.class);
        Call<ResponseBody> call=userApi.crearUsuario(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                        Log.i("Success", "Usuario Creado");
                    }
                    Log.i("RESPONSE",response.toString());
                    Log.i("ERROR Successful", retrofit.baseUrl().toString() + response.code());
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                Log.i("Failure", t.toString());
            }
        });
    }
}