package com.pf.scoringsalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pf.scoringsalud.notifications.NotificationActivity;

public class Welcome extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        button= findViewById((R.id.buttonW));
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openAuthView();
            }
        });
    }

    public void openAuthView(){
        Intent intent= new Intent(this, AuthActivity.class);
        startActivity(intent);
    }
}