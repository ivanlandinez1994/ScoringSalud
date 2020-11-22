package com.pf.scoringsalud.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pf.scoringsalud.activity.HomeActivity;
import com.pf.scoringsalud.factory.FactoryPuntuable;
import com.pf.scoringsalud.puntuable.Actividad;


import com.pf.scoringsalud.R;


public class EjerEndFragment extends Fragment {

 Button end;
 Actividad a;
 String dato;
 TextView tv_ejerend_descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejer_end, container, false);
        tv_ejerend_descripcion = (TextView) view.findViewById(R.id.tv_ejerend_descripcion);
        end= view.findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().finish();
                // Intent intent = new Intent(EjerEndFragment.this.getActivity(), HomeActivity.class);
               // startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //recupero datos del fragment
        getParentFragmentManager().setFragmentResultListener("eje_end", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                recuperarDato(result.getString("key").toString());
                a = (Actividad) FactoryPuntuable.actividad(dato);
                tv_ejerend_descripcion.setText("¡Has completado\n"+a.getNombre()+"!");


            }
        });

    }

    private void recuperarDato(String dato){
        this.dato=dato;

    }


}