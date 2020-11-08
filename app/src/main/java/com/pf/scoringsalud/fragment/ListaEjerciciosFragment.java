package com.pf.scoringsalud.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pf.scoringsalud.R;

<<<<<<< HEAD:app/src/main/java/com/pf/scoringsalud/ListaEjercicios.java
public class ListaEjercicios extends Fragment implements View.OnClickListener {
=======

public class ListaEjerciciosFragment extends Fragment {
>>>>>>> cfcd42294e0395ee178c1f8289ea208e5e112984:app/src/main/java/com/pf/scoringsalud/fragment/ListaEjerciciosFragment.java

    Button cadera, hombro,rodilla, cuello;
    Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista_ejercicios, container, false);



       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

<<<<<<< HEAD:app/src/main/java/com/pf/scoringsalud/ListaEjercicios.java
        cadera = (Button) view.findViewById(R.id.btn_listaejercicios_cadera);
        cadera.setOnClickListener(this);
        hombro = (Button) view.findViewById(R.id.btn_listaejercicios_hombros);
        hombro.setOnClickListener(this);
        rodilla = (Button) view.findViewById(R.id.btn_listaejercicios_rodilla);
        rodilla.setOnClickListener(this);
        cuello = (Button) view.findViewById(R.id.btn_listaejercicios_cuello);
        cuello.setOnClickListener(this);
=======
        //hardcode booton e1 a descripcion de ejercicio, no persistencia
        e1= view.findViewById(R.id.e1);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("codigo","Cuello");

                getParentFragmentManager().setFragmentResult("btn_ejercicio",bundle);
                EjerDescFragment ed= new EjerDescFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.listaEjer, ed );
                transaction.commit();
            }
        });
>>>>>>> cfcd42294e0395ee178c1f8289ea208e5e112984:app/src/main/java/com/pf/scoringsalud/fragment/ListaEjerciciosFragment.java


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_listaejercicios_cadera:
                bundle = new Bundle();
                bundle.putString("codigo","Cadera");
                break;
            case R.id.btn_listaejercicios_cuello:
                bundle = new Bundle();
                bundle.putString("codigo","Cuello");
                break;
            case R.id.btn_listaejercicios_hombros:
                bundle = new Bundle();
                bundle.putString("codigo","Hombro");
                break;
            case R.id.btn_listaejercicios_rodilla:
                bundle = new Bundle();
                bundle.putString("codigo","Rodilla");
                break;
        }
        getParentFragmentManager().setFragmentResult("btn_ejercicio",bundle);
        EjerDesc ed= new EjerDesc();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.listaEjer, ed );
        transaction.commit();
    }
}