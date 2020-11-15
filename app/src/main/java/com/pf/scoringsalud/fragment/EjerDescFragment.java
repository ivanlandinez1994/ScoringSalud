package com.pf.scoringsalud.fragment;


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

import com.pf.scoringsalud.factory.FactoryPuntuable;
import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.Medidor.Contador;
import com.pf.scoringsalud.R;


public class EjerDescFragment extends Fragment {
    Button bacK;
    Button go;
    String dato;
    TextView tv_ejedesc_nombre, tv_ejedesc_articulacion, tv_ejedesc_duracion;
    Actividad a;

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
                ListaEjerciciosFragment ed= new ListaEjerciciosFragment();
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
        tv_ejedesc_nombre = view.findViewById(R.id.tv_ejedesc_nombre);
        tv_ejedesc_articulacion = view.findViewById(R.id.tv_ejedesc_articulacion);
        tv_ejedesc_duracion = view.findViewById(R.id.tv_ejedesc_duracion);
        getParentFragmentManager().setFragmentResultListener("btn_ejercicio", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                recuperarDato(result.getString("codigo").toString());
                a = (Actividad) FactoryPuntuable.actividad(dato);
                setearDatos();

            }
        });

        go= view.findViewById(R.id.btnGO);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("codigo",dato);
                getParentFragmentManager().setFragmentResult("btn_ejerRun",bundle);
                EjerRunFragment ed= new EjerRunFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerDesc, ed );
                transaction.commit();
            }
        });

    }
    private void recuperarDato(String dato){
        this.dato = dato;
    }
    private void setearDatos(){

        if(a.tieneContador()) {
            for (int i = 0; i < a.getMedidores().size(); i++) {
                if (a.getMedidores().get(i) instanceof Contador) {
                    tv_ejedesc_duracion.setText("Duracion: "+((((Contador) a.getMedidores().get(i)).getDuracionSegundos()) * a.getRepeticiones())+" Segundos");
                }

            }
        }else{
            tv_ejedesc_duracion.setText("--:--");
        }

        tv_ejedesc_nombre.setText(a.getNombre());
        tv_ejedesc_articulacion.setText("Articulacion "+a.getArticulacion());

    }

}