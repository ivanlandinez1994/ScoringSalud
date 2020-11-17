package com.pf.scoringsalud.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.ArrayListReportValueCallback;
import com.pf.scoringsalud.api.infraestructura.Reporte;

import java.util.ArrayList;

public class ProgresoSemanaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgresoSemanaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProgresoSemanaFragment newInstance(String param1, String param2) {
        ProgresoSemanaFragment fragment = new ProgresoSemanaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView tv_cantPuntos, tv_cantEjer, tv_cantSuenio, tv_cantAgua, tv_cantPasos, tv_dia;
    Button btn_primero, btn_segundo, btn_tercero, btn_cuarto, btn_quinto, btn_sexto, btn_septimo;
    ArrayList<Reporte> reportes = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progreso_semana, container, false);
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_cantPuntos = view.findViewById(R.id.cantPuntos);
        tv_cantEjer = view.findViewById(R.id.cantEjer);
        tv_cantSuenio = view.findViewById(R.id.cantSuenio);
        tv_cantAgua = view.findViewById(R.id.cantAgua);
        tv_cantPasos = view.findViewById(R.id.cantPasos);
        tv_dia = view.findViewById(R.id.dia);

        btn_primero = view.findViewById(R.id.enero);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.obtenerReportes(user.getEmail(), "semana", new ArrayListReportValueCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> values) {
                reportes = values;
                Reporte reporteHoy = reportes.get(0);
                setTextValues(reporteHoy);
                setBtnValues(view, reportes);
            }
            @Override
            public void onFailure() {

            }
        });



    }

    private void setTextValues(Reporte reporte){
        tv_cantPuntos.setText(Integer.toString(reporte.getPuntos()));
        tv_cantEjer.setText(Integer.toString(reporte.getCantEjercicios()));
        tv_cantSuenio.setText(Integer.toString(reporte.getHorasDeSueño()));
        tv_cantAgua.setText(Integer.toString(reporte.getVasosDeAgua()));
        tv_cantPasos.setText(Integer.toString(reporte.getCantidadDePasos()));
        tv_dia.setText(reporte.getTitulo());
    }
    private void setBtnValues(View view, final ArrayList<Reporte> reportesArray){
        /**int maxHeight = 125;
        int primeroSize = maxHeight * reportesArray.get(6).getPuntos() / 1000;
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams( ConstraintLayout.LayoutParams.WRAP_CONTENT,  ConstraintLayout.LayoutParams.WRAP_CONTENT);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        lp.height = (int) (primeroSize * scale + 0.5f);
        btn_primero.setLayoutParams( lp );**/

        btn_primero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(6));
            }
        });
        btn_segundo= view.findViewById(R.id.febrero);
        btn_segundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(5));
            }
        });
        btn_tercero= view.findViewById(R.id.marzo);
        btn_tercero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(4));
            }
        });
        btn_cuarto= view.findViewById(R.id.abril);
        btn_cuarto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(3));
            }
        });
        btn_quinto= view.findViewById(R.id.mayo);
        btn_quinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(2));
            }
        });
        btn_sexto= view.findViewById(R.id.junio);
        btn_sexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(1));
            }
        });
        btn_septimo= view.findViewById(R.id.julio);
        btn_septimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(0));
            }
        });
    }

}