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
import android.widget.ImageView;
import android.widget.TextView;

import com.pf.scoringsalud.activity.EjerciciosActivity;
import com.pf.scoringsalud.factory.FactoryPuntuable;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.medidor.Contador;


public class EjerDescFragment extends Fragment {
    Button bacK;
    Button go;
    String dato;
    TextView tv_ejedesc_nombre, tv_ejedesc_articulacion, tv_ejedesc_duracion, tv_ejerdesc_descripcion;
    Actividad a;
    ImageView iv_ejedesc;


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
               // EjerciciosActivity ea= new EjerciciosActivity();
             //   FragmentTransaction transaction = getFragmentManager().beginTransaction();
              //  transaction.replace(R.id.ejerDesc, ea );
              //  transaction.commit();

                Intent intent = new Intent(getActivity(), EjerciciosActivity.class);
                getActivity().startActivity(intent);
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
        tv_ejerdesc_descripcion = view.findViewById(R.id.tv_ejerdesc_descripcion);
        iv_ejedesc = (ImageView) view.findViewById(R.id.iv_ejedesc);

        getParentFragmentManager().setFragmentResultListener("btn_ejercicio",
                this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                recuperarDato(result.getString("codigo").toString());
                a = (Actividad) FactoryPuntuable.actividad(dato);
                setearDatos();

            }
        });


        go= view.findViewById(R.id.btn_ejerdesc_go);
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
                        tv_ejedesc_duracion.setText("Tiempo: " + ((((Contador) a.getMedidores().get(i)).getDuracionSegundos()) * a.getRepeticiones()) + " Segundos");
                    }

                }


            }else {
                tv_ejedesc_duracion.setText("--:--");
            }

        tv_ejedesc_nombre.setText(a.getNombre());
        tv_ejedesc_articulacion.setText("Articulacion: "+a.getArticulacion());
        iv_ejedesc.setImageResource(getResources().getIdentifier(a.getRutaGif(), "drawable",getContext().getPackageName()));
        tv_ejerdesc_descripcion.setText(a.getDescripcion());

    }

}