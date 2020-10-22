package com.pf.scoringsalud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pf.scoringsalud.User.User;
import com.pf.scoringsalud.api.interfaces.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetUser extends AppCompatActivity {
    EditText userMail;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvEdad;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user);
        userMail = findViewById(R.id.userMail);
        tvNombre = findViewById(R.id.tvNombre);
        tvApellido = findViewById(R.id.tvApellido);
        tvEdad = findViewById(R.id.tvEdad);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find(userMail.getText().toString());
            }
        });
    }

    private void find(String mail){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.120:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        final UserApi userApi = retrofit.create(UserApi.class);
        Call<User> call=userApi.obtenerUsuario(mail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        User user= response.body();
                        tvNombre.setText(user.getNombre());
                        tvApellido.setText(user.getApellido());
                        tvEdad.setText(user.getEdad());
                    }
                    Log.i("ERROR Successful",retrofit.baseUrl().toString()+response.code());
                }catch(Exception e){
                    Toast.makeText(GetUser.this, e.getMessage(),Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(GetUser.this,"Error de conexion", Toast.LENGTH_SHORT);
                Log.i("Failure",t.toString());
            }
        });
    }
}