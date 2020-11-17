package com.pf.scoringsalud.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;
import com.hadiidbouk.charts.OnBarClickedListener;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.fragment.ProgresoAnioFragment;
import com.pf.scoringsalud.fragment.ProgresoSemanaFragment;

import java.util.ArrayList;

public class PuntosActivity extends AppCompatActivity implements OnBarClickedListener {
    private FragmentTransaction transaction;
    private Fragment fragmentProgresoSemana, fragmentProgresoAnio;
    //private ChartProgressBar mChart;
    //private ChartProgressBar mChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fragmentProgresoSemana = new ProgresoSemanaFragment();
        fragmentProgresoAnio = new ProgresoAnioFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragmentsProgreso, fragmentProgresoSemana).commit();

    }

    @Override
    public void onBarClicked(int index) {
        Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
    }

    public void onModeSelect(View view){
        transaction = getSupportFragmentManager().beginTransaction();
        switch(view.getId()){
            case R.id.semanaBtn: transaction.replace(R.id.contenedorFragmentsProgreso, fragmentProgresoSemana);
            transaction.addToBackStack(null);
                break;
            case R.id.anioBtn: transaction.replace(R.id.contenedorFragmentsProgreso, fragmentProgresoAnio);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }



}