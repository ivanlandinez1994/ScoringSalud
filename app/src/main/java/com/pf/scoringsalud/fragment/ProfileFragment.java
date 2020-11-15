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
import com.pf.scoringsalud.user.Data.LoadImage;

public class ProfileFragment extends Fragment {

    Button back;
    TextView tvEmail;
    TextView tvNombre;
    ImageView ivUser;
    Button btn1;
    Button btn2;Button btn3;Button btn4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        btn1=view.findViewById(R.id.sedentario);
        btn2=view.findViewById(R.id.medioActivo);
        btn3=view.findViewById(R.id.activo);
        btn4=view.findViewById(R.id.altoActivo);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));



            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));


            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));


            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn4.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                btn2.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn3.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));
                btn1.setBackground(getResources().getDrawable(R.drawable.rounded_corners_gray));


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
}

