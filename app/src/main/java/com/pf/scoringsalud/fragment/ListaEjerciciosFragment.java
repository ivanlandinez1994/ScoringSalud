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


public class ListaEjerciciosFragment extends Fragment implements View.OnClickListener {

    Button cadera, hombro,rodilla, cuello, muñeca;
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

        cadera = (Button) view.findViewById(R.id.btn_listaejercicios_cadera);
        cadera.setOnClickListener(this);
        hombro = (Button) view.findViewById(R.id.btn_listaejercicios_hombros);
        hombro.setOnClickListener(this);
        rodilla = (Button) view.findViewById(R.id.btn_listaejercicios_rodilla);
        rodilla.setOnClickListener(this);
        cuello = (Button) view.findViewById(R.id.btn_listaejercicios_cuello);
        cuello.setOnClickListener(this);
        muñeca = (Button) view.findViewById(R.id.btn_listaejercicios_muñeca);
        muñeca.setOnClickListener(this);


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
            case R.id.btn_listaejercicios_muñeca:
                bundle = new Bundle();
                bundle.putString("codigo","Munieca");
                break;
        }
        getParentFragmentManager().setFragmentResult("btn_ejercicio",bundle);
        EjerDescFragment ed= new EjerDescFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.listaEjer, ed );
        transaction.commit();
    }
}