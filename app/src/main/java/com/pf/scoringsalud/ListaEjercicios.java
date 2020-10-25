package com.pf.scoringsalud;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ListaEjercicios extends Fragment {

    Button e1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista_ejercicios, container, false);


        //hardcode booton e1 a descripcion de ejercicio, no persistencia
        e1= view.findViewById(R.id.e1);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjerDesc ed= new EjerDesc();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.listaEjer, ed );
                transaction.commit();
            }
        });




        return view;
    }

}