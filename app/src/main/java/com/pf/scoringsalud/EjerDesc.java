package com.pf.scoringsalud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EjerDesc extends Fragment {
    Button bacK;
    Button go;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejer_desc, container, false);

        //boton a fragment ejercicios
        bacK= view.findViewById(R.id.back);
        bacK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListaEjercicios ed= new ListaEjercicios();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerDesc, ed );
                transaction.commit();
            }
        });

        go= view.findViewById(R.id.btnGO);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjerRun ed= new EjerRun();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerDesc, ed );
                transaction.commit();
            }
        });





        return view;
    }
}