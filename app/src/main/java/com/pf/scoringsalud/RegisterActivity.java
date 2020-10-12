package com.pf.scoringsalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.pf.scoringsalud.consumoApi.ApiUsuario;
import com.pf.scoringsalud.interfaces.UserApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    String userMail;
    EditText userMailEdit;
    EditText userDniEdit;
    EditText userNombreEdit;
    EditText userApellidoEdit;
    EditText userEdadEdit;
    EditText userPuestoEdit;
    Button btnRegister;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        userMail = bundle.getString("email");
        userMailEdit = findViewById(R.id.userUserEdit);
        userMailEdit.setText(userMail);
        userMailEdit.setEnabled(false);
        userDniEdit = findViewById(R.id.userDniEdit);
        userNombreEdit = findViewById(R.id.userNombreEdit);
        userApellidoEdit = findViewById(R.id.userApellidoEdit);
        userEdadEdit = findViewById(R.id.userEdadEdit);
        userPuestoEdit = findViewById(R.id.userPuestoEdit);
        btnRegister = findViewById(R.id.btnRegistro);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User();
                user.setMail(userMail);
                user.setDni(userDniEdit.getText().toString());
                user.setNombre(userNombreEdit.getText().toString());
                user.setApellido(userApellidoEdit.getText().toString());
                user.setEdad(userEdadEdit.getText().toString());
                user.setPuesto(userPuestoEdit.getText().toString());
                user.setPuntos(0);
                registrar(user);
            }
        });
    }

    private void registrar(User user){
        ApiUsuario userApi = new ApiUsuario();
        Response response = userApi.registrarUsuario(user, getApplicationContext());
        Log.i("Success", user.toString());
        if (response != null && response.isSuccessful()) {
            Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
            Log.i("Success", "Usuario Creado");
            FirebaseUser userFirebase;
            userFirebase = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("email", userFirebase.getEmail());
            intent.putExtra("provider", ProviderType.GOOGLE);
            startActivity(intent);
        }
        //Log.i("ERROR Successful", Integer.toString(response.code()));
    }
}