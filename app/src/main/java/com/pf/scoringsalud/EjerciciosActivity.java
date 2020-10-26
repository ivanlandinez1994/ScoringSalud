package com.pf.scoringsalud;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class EjerciciosActivity extends AppCompatActivity {
    public static boolean notificationClicked = false;
    androidx.fragment.app.FragmentManager fragmentManager;
    androidx.fragment.app.FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("paso por aca");
            String ejercicioFragment = getIntent().getStringExtra("ejerDesc");

        // If menuFragment is defined, then this activity was launched with a fragment selection
        if (ejercicioFragment != null) {
            notificationClicked = true;
            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (ejercicioFragment.equals("ejerDesc")) {
                fragmentManager = getSupportFragmentManager();
               // FragmentManager fragmentManager = getFragmentManager();
                EjerDesc fragment;
                fragment = new EjerDesc();
                //fragment = fragmentManager.findFragmentById(R.id.ejerDesc);
                //FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragmentTransaction = fragmentManager.beginTransaction();
                //transaction.add(R.id.ejerDesc, fragment);
                //transaction.commit();
                fragmentTransaction.add(R.id.ejerDesc, fragment, "demo");
            }
        } else {
            // Activity was not launched with a menuFragment selected -- continue as if this activity was opened from a launcher (for example)
            setContentView(R.layout.activity_ejercicios);
            System.out.println("salio por else");
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





}