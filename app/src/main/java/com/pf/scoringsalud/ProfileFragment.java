package com.pf.scoringsalud;

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
import com.pf.scoringsalud.User.Data.LoadImage;

public class ProfileFragment extends Fragment {

    Button back;
    TextView tvEmail;
    TextView tvNombre;
    ImageView ivUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inflate the layout for this fragment
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
            LoadImage loadImage = new LoadImage(ivUser);
            loadImage.execute(user.getPhotoUrl().toString());
        }else{
            tvEmail.setText("Jhon");
            tvNombre.setText("Doe");
        }
    }
}

