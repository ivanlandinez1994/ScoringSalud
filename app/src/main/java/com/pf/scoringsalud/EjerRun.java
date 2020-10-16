package com.pf.scoringsalud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EjerRun extends Fragment {
    Button go;

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
}