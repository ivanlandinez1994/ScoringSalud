package com.pf.scoringsalud;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.User.Data.LoadImage;

public class ProgresoFragment extends Fragment {
    Button seleccionarFecha;
    TextView tvNombre;
    ImageView ivUser;
    //private SharedPreferences settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_progreso, container, false);


        seleccionarFecha = view.findViewById(R.id.back);
        seleccionarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment d = new DialogFragment();
               ;
            }
        });

        setData(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setData(View view){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tvNombre = (TextView)view.findViewById(R.id.tvNombre);
        ivUser = (ImageView)view.findViewById(R.id.ivUser);
        Log.i("path",user.getPhotoUrl().normalizeScheme().toString()+", "+ user.getPhotoUrl().normalizeScheme().getLastPathSegment()+", "+user.getPhotoUrl().normalizeScheme().getPathSegments());
        if(user!=null) {
            tvNombre.setText(user.getDisplayName());
            ivUser.setImageBitmap(null);
            LoadImage loadImage = new LoadImage(ivUser);
            loadImage.execute(user.getPhotoUrl().toString());
        }else{
            tvNombre.setText("Doe");
        }




    }


}
