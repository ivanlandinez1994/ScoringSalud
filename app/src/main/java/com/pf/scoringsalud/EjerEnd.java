package com.pf.scoringsalud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EjerEnd extends Fragment {

 Button end;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejer_end, container, false);


        end= view.findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListaEjercicios ed= new ListaEjercicios();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerEnd, ed );
                transaction.commit();
            }
        });

        return view;
    }
}