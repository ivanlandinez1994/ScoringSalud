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
import com.hadiidbouk.charts.ChartProgressBar;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.ArrayListReportValueCallback;
import com.pf.scoringsalud.api.infraestructura.Reporte;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgresoAnioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgresoAnioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ChartProgressBar mChart;

    public ProgresoAnioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgresoAnioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgresoAnioFragment newInstance(String param1, String param2) {
        ProgresoAnioFragment fragment = new ProgresoAnioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fragment_progreso_anio, container, false);
    }
    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
    TextView tv_cantPuntos, tv_cantEjer, tv_cantSuenio, tv_cantAgua, tv_cantPasos, tv_mes;
    Button btn_enero, btn_febrero, btn_marzo, btn_abril, btn_mayo, btn_junio, btn_julio, btn_agosto, btn_septiembre, btn_octubre, btn_noviembre, btn_diciembre;
    ArrayList<Reporte> reportes = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_cantPuntos = view.findViewById(R.id.cantPuntos);
        tv_cantEjer = view.findViewById(R.id.cantEjer);
        tv_cantSuenio = view.findViewById(R.id.cantSuenio);
        tv_cantAgua = view.findViewById(R.id.cantAgua);
        tv_cantPasos = view.findViewById(R.id.cantPasos);
        tv_mes = view.findViewById(R.id.mes);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.obtenerReportes(user.getEmail(), "año", new ArrayListReportValueCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> values) {
                reportes = values;
                Reporte reporteMesActual = reportes.get(Calendar.getInstance().get(Calendar.MONTH));
                setTextValues(reporteMesActual);
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
        tv_mes.setText(reporte.getTitulo());
    }
    private void setBtnValues(View view, final ArrayList<Reporte> reportesArray){
        /**int maxHeight = 125;
         int primeroSize = maxHeight * reportesArray.get(6).getPuntos() / 1000;
         ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams( ConstraintLayout.LayoutParams.WRAP_CONTENT,  ConstraintLayout.LayoutParams.WRAP_CONTENT);
         final float scale = getContext().getResources().getDisplayMetrics().density;
         lp.height = (int) (primeroSize * scale + 0.5f);
         btn_primero.setLayoutParams( lp );**/
        btn_enero= view.findViewById(R.id.enero);
        btn_enero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(0));
            }
        });
        btn_febrero= view.findViewById(R.id.febrero);
        btn_febrero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(1));
            }
        });
        btn_marzo= view.findViewById(R.id.marzo);
        btn_marzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(2));
            }
        });
        btn_abril= view.findViewById(R.id.abril);
        btn_abril.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(3));
            }
        });
        btn_mayo= view.findViewById(R.id.mayo);
        btn_mayo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(4));
            }
        });
        btn_junio= view.findViewById(R.id.junio);
        btn_junio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(5));
            }
        });
        btn_julio= view.findViewById(R.id.julio);
        btn_julio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(6));
            }
        });
        btn_agosto= view.findViewById(R.id.agosto);
        btn_agosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(7));
            }
        });
        btn_septiembre= view.findViewById(R.id.septiembre);
        btn_septiembre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(8));
            }
        });
        btn_octubre= view.findViewById(R.id.octubre);
        btn_octubre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(9));
            }
        });
        btn_noviembre= view.findViewById(R.id.noviembre);
        btn_noviembre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(10));
            }
        });
        btn_diciembre= view.findViewById(R.id.diciembre);
        btn_diciembre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextValues(reportesArray.get(11));
            }
        });
    }
}