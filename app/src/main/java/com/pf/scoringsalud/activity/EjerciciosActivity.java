package com.pf.scoringsalud.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.FragmentNavigator;

import com.pf.scoringsalud.R;

public class EjerciciosActivity extends AppCompatActivity {
   private String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        tipo="Actividad";
try {
    if(getIntent().getStringExtra("notificacion").equalsIgnoreCase("Pomodoro")){
        tipo = getIntent().getStringExtra("notificacion");
        getIntent().removeExtra("notificacion");
    }
}catch (Exception e){

}



        // boton atras deprecado
       /*findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });*/


        //boton antras (vuelve a la ultima acttivity vista
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }


    public String getTipo() {
        return tipo;
    }
}
