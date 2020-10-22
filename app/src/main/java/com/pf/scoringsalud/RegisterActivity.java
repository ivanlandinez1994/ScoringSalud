package com.pf.scoringsalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pf.scoringsalud.User.User;
import com.pf.scoringsalud.api.consumo.ApiUsuario;

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
        userApi.registrarUsuario(user, HomeActivity.class, getApplicationContext());
        Log.i("Success", user.toString());
        //Log.i("ERROR Successful", Integer.toString(response.code()));
    }
}