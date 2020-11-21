package com.pf.scoringsalud.activity;

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
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.infraestructura.StringValueCallback;
import com.pf.scoringsalud.user.Domain.User;
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
        userApi.registrarUsuario(user, new StringValueCallback() {
            @Override
            public void onSuccess(String value) {
                FirebaseUser userFirebase;
                userFirebase = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("email", userFirebase.getEmail());
                Toast.makeText(getApplicationContext(), "Bienvenid@: "+userFirebase.getDisplayName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Error de conexion, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("Success", user.toString());
    }
}