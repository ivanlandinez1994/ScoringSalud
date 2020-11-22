package com.pf.scoringsalud.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.StringValueCallback;
import com.pf.scoringsalud.notifications.AlarmReceiver;
import com.pf.scoringsalud.user.Data.LoadImage;

enum ProviderType {
    BASIC,
    GOOGLE
}

public class HomeActivity extends AppCompatActivity {
    TextView tv_puntos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("HomeAct: Inicio HomeActivity","Inicio");

        //-----------------Validar desde donde llega (usuario o mail)-----------------//
        /*Toast.makeText(HomeActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
        Log.i("Success", "Usuario Creado");*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setup
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();


        //Persistir Datos
        SharedPreferences.Editor preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        preferences.putString("email", bundle.getString("email"));
        preferences.putString("provider", bundle.getString("provider"));
        preferences.apply();


        Log.i("HomeAct: Fin persistencia","Termino de persistir");

        //iniciar stepCounter
        /**
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 15000 ,
                AlarmManager.INTERVAL_HOUR, pending);
        **/
        //Traer y setear puntos
        tv_puntos = findViewById(R.id.textView18);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.obtenerPuntosDia(user.getEmail(), getApplicationContext(), new StringValueCallback() {
            @Override
            public void onSuccess(String value) {
                tv_puntos.setText(value);

            }

            @Override
            public void onFailure() {

            }
        });

        //drawer, comportamiento del boton para abrir drawer
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //colores en los iconos del drawer
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);


        //comportamiento para el cierre de sesion desde el drawer
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull
                    NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.salir){
                    FirebaseAuth.getInstance().signOut();

                    SharedPreferences.Editor preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
                    preferences.clear();
                    preferences.apply();

                    AuthUI.getInstance()
                            .signOut(getApplicationContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ...
                                }
                            });


                    Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });

        setCustomHeader();
    }
    @Override
    protected void onResume() {
        super.onResume();

        //Traer y setear puntos
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.obtenerPuntosDia(user.getEmail(), getApplicationContext(), new StringValueCallback() {
            @Override
            public void onSuccess(String value) {
                tv_puntos.setText(value);

            }

            @Override
            public void onFailure() {

            }
        });

    }
    private void setCustomHeader(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View hView = navigationView.getHeaderView(0);
        TextView tvEmail;
        TextView tvNombre;
        RoundedImageView ivUser;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tvEmail = hView.findViewById(R.id.tvEmailHeader);
        tvNombre = hView.findViewById(R.id.tvNameHeader);
        ivUser = hView.findViewById(R.id.imageProfile);
        if(user!=null) {

            tvEmail.setText(user.getEmail());
            tvNombre.setText(user.getDisplayName());
            ivUser.setImageBitmap(null);
            try {

                LoadImage loadImage = new LoadImage(ivUser);
                loadImage.execute(user.getPhotoUrl().toString());
                Log.i("image profe:", user.getPhotoUrl().toString());
            }catch(Exception e){
                ivUser.setImageResource(R.drawable.prof);
                Log.i("Exception 131-HomeActivity",e.getMessage());

            }
        }else{
            tvEmail.setText("Jhon");
            tvNombre.setText("Doe");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {  }
}