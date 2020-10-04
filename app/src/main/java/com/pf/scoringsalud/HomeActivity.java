package com.pf.scoringsalud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.prefs.Preferences;

enum ProviderType {
    BASIC,
    GOOGLE
}

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


                    Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });



    }








}