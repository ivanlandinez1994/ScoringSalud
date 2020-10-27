package com.pf.scoringsalud;

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

import com.pf.scoringsalud.Factorys.FactoryPuntuable;
import com.pf.scoringsalud.Puntuable.Actividad;


public class EjerRun extends Fragment {
    Button go;
    String dato;
    Actividad a;
    TextView tv_ejerun_tiempo, tv_ejerun_repeticiones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejer_run, container, false);

        go= view.findViewById(R.id.btnEnd);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjerEnd ed= new EjerEnd();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerDesc, ed );
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_ejerun_tiempo = view.findViewById(R.id.tv_ejerun_tiempo);
        tv_ejerun_repeticiones = view.findViewById(R.id.tv_ejerun_repeticiones);
        tv_ejerun_tiempo.setText("NULL");
        getParentFragmentManager().setFragmentResultListener("btn_ejerRun", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                System.out.println(result.getString("codigo").toString());
                recuperarDato(result.getString("codigo").toString());
                a = (Actividad) FactoryPuntuable.actividad(dato);
                if(a == null){
                    tv_ejerun_tiempo.setText("NULL");
                    tv_ejerun_repeticiones.setText("Null");

                }else{
                    tv_ejerun_tiempo.setText(a.getDuracionSegundos() + " Segundos");
                    tv_ejerun_repeticiones.setText(""+a.getRepeticionesRealizadas()+ " / "+a.getRepeticiones());

                }

            }
        });
/*
        go= view.findViewById(R.id.btnGO);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("codigo",dato);
                getParentFragmentManager().setFragmentResult("bundle",bundle);
                EjerRun ed= new EjerRun();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerDesc, ed );
                transaction.commit();
            }
        });

 */

    }
    private void recuperarDato(String dato){
        this.dato = dato;
    }
}