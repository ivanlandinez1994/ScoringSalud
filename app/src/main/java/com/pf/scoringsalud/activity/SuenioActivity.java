package com.pf.scoringsalud.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.R;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.StringValueCallback;

import java.util.Calendar;

public class SuenioActivity extends AppCompatActivity {
    TextView tv_hsSuenio, tv_inicioSuenio, tv_finSuenio;
    ProgressBar progressBar;
    int inicioSueñoHora = 0, inicioSueñoMinuto= 0, finSueñoHora= 0, finSueñoMinuto= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suenio);
        tv_hsSuenio = findViewById(R.id.hsSuenio);
        progressBar = findViewById(R.id.progressBar);
        //llamada a api
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.actualizarPuntuable(user.getEmail(), "Sueño", -1, getApplicationContext(), new StringValueCallback() {
            @Override
            public void onSuccess(String value) {
                tv_hsSuenio.setText("+" + value+" horas");
                if (Integer.parseInt(value) > 7){
                    progressBar.setProgress(100);
                }else{
                    progressBar.setProgress(Integer.parseInt(value)*100/8);
                }
            }

            @Override
            public void onFailure() {

            }
        });
        tv_inicioSuenio = findViewById(R.id.inicioSuenio);
        tv_finSuenio = findViewById(R.id.finSuenio);
        tv_inicioSuenio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SuenioActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                inicioSueñoHora = hourOfDay;
                                inicioSueñoMinuto = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,inicioSueñoHora,inicioSueñoMinuto);
                                tv_inicioSuenio.setText(DateFormat.format("hh:mm aa", calendar));
                                calcularHoras();
                            }
                        }, 12, 0, true
                );
                timePickerDialog.updateTime(inicioSueñoHora, inicioSueñoMinuto);
                timePickerDialog.show();
            }
        });
        tv_finSuenio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SuenioActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                finSueñoHora = hourOfDay;
                                finSueñoMinuto = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,finSueñoHora,finSueñoMinuto);
                                tv_finSuenio.setText(DateFormat.format("hh:mm aa", calendar));
                                calcularHoras();
                            }
                        }, 12, 0, true
                );
                timePickerDialog.updateTime(finSueñoHora, finSueñoMinuto);
                timePickerDialog.show();

            }
        });


        //boton antras (vuelve a la ultima acttivity vista
        findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
    private void calcularHoras(){
        int horasDeSueño;
        if(inicioSueñoHora > finSueñoHora){
            horasDeSueño = (24 - inicioSueñoHora) + finSueñoHora;
        }else{
            horasDeSueño = finSueñoHora - inicioSueñoHora;
        }
        if(inicioSueñoMinuto > finSueñoMinuto){
            horasDeSueño = horasDeSueño -1;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiPuntuable ap = new ApiPuntuable();
        ap.actualizarPuntuable(user.getEmail(), "Sueño", horasDeSueño, getApplicationContext(), new StringValueCallback() {
            @Override
            public void onSuccess(String value) {
                tv_hsSuenio.setText("+" + value+" horas");
                if (Integer.parseInt(value) > 7){
                    progressBar.setProgress(100);
                }else{
                    progressBar.setProgress(Integer.parseInt(value)*100/8);
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }
}