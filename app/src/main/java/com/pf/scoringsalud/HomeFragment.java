package com.pf.scoringsalud;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pf.scoringsalud.notifications.NotificationActivity;

public class HomeFragment extends Fragment {

    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);


        // abrir activity ejercicios
        Button btnEX= view.findViewById(R.id.buttonEjercicios);
        btnEX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EjerciciosActivity.class);
                startActivity(intent);
            }
        });


        // abrir activity agua
        Button btnAgua= view.findViewById(R.id.buttonAgua);
        btnAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WaterActivity.class);
                startActivity(intent);
            }
        });


        // abrir activity puntos
        Button btnptos= view.findViewById(R.id.btnProgress);
        btnptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Puntos.class);
                startActivity(intent);
            }
        });



        Button btnAct= view.findViewById(R.id.buttonRutinas);
        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}