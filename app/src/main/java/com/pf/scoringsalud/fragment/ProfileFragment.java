package com.pf.scoringsalud.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.ArrayListReportValueCallback;
import com.pf.scoringsalud.api.infraestructura.Reporte;
import com.pf.scoringsalud.user.Data.LoadImage;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileFragment extends Fragment {

    Button back;
    TextView tvEmail;
    TextView tvNombre;
    ImageView ivUser;
    Button btnSedentario, btnMedioActivo, btnActivo, btnAltoActivo;
    ArrayList<Reporte> reportes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        btnSedentario =view.findViewById(R.id.sedentario);
        btnMedioActivo =view.findViewById(R.id.medioActivo);
        btnActivo =view.findViewById(R.id.activo);
        btnAltoActivo =view.findViewById(R.id.altoActivo);

        setData(view);
        setActividadFisica();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setData(View view){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tvEmail = (TextView)view.findViewById(R.id.tvEmail);
        tvNombre = (TextView)view.findViewById(R.id.tvNombre);
        ivUser = (ImageView)view.findViewById(R.id.ivUser);
        Log.i("path",user.getPhotoUrl().normalizeScheme().toString()+", "+ user.getPhotoUrl().normalizeScheme().getLastPathSegment()+", "+user.getPhotoUrl().normalizeScheme().getPathSegments());
        if(user!=null) {
            tvEmail.setText(user.getEmail());
            tvNombre.setText(user.getDisplayName());
            ivUser.setImageBitmap(null);
            try {
                LoadImage loadImage = new LoadImage(ivUser);
                loadImage.execute(user.getPhotoUrl().toString());
            } catch(Exception e){
                ivUser.setImageResource(R.drawable.prof);
                Log.i("Exception 120-profileFragment",e.getMessage());
            }
        }else{
            tvEmail.setText("Jhon");
            tvNombre.setText("Doe");
        }




    }

    private void setActividadFisica(){
        ApiPuntuable apiPuntuable = new ApiPuntuable();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        apiPuntuable.obtenerReportes(user.getEmail(), "año", new ArrayListReportValueCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> values) {
                reportes = values;
                Reporte reporteMesActual = reportes.get(Calendar.getInstance().get(Calendar.MONTH));
                int puntosMonth = reporteMesActual.getPuntos();
                if (puntosMonth<=15000){
                    btnSedentario.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                }else if(puntosMonth>15000 && puntosMonth<=30000){
                    btnMedioActivo.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                }else if(puntosMonth>30000 && puntosMonth<=45000){
                    btnActivo.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                }else if(puntosMonth>45000){
                    btnAltoActivo.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                }

            }
            @Override
            public void onFailure() {

            }
        });
    }
}
