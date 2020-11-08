package com.pf.scoringsalud.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pf.scoringsalud.factory.FactoryPuntuable;
import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.EstrategiaMedicion.MedicionEjerRun;
import com.pf.scoringsalud.puntuable.Medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.Medidor.Contador;
import com.pf.scoringsalud.puntuable.Medidor.Proximity;
import com.pf.scoringsalud.R;

import static android.content.Context.SENSOR_SERVICE;

//
public class EjerRunFragment extends Fragment implements SensorEventListener {
    Button go, end;
    String dato;
    CountDownTimer contador;
    Actividad a;
    MedicionEjerRun medicion;
    TextView tv_ejerun_tiempo, tv_ejerun_repeticiones, tv_ejerun_sensores;

    private SensorManager sm;
    private Sensor sensor;
    private Vibrator vibrator;

    private boolean acelerometro = false;
    private boolean proximity=false;
    private int contadorActividad;
    private boolean comienzo =false;

    private double x;
    private double y;
    private double z;
    private int contadorEjercicio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejer_run, container, false);
        tv_ejerun_tiempo = view.findViewById(R.id.tv_ejerun_tiempo);
        tv_ejerun_repeticiones = view.findViewById(R.id.tv_ejerun_repeticiones);
        tv_ejerun_sensores = view.findViewById(R.id.tv_ejerRun_sensores);
        end= view.findViewById(R.id.btnEnd);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjerEndFragment ed= new EjerEndFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerDesc, ed );
                transaction.commit();
            }
        });

        go= view.findViewById(R.id.btn_ejerRun_comenzar);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comenzar();
                //iniciarContador();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //recupero datos del fragment
        getParentFragmentManager().setFragmentResultListener("btn_ejerRun", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                recuperarDato(result.getString("codigo").toString());
                a = (Actividad) FactoryPuntuable.actividad(dato);
                activarSensores();
                medicion = new MedicionEjerRun(a);
            }
        });

    }

    @Override
    public void onPause() {
        stop();
        super.onPause();
    }

    @Override
    public void onResume() {
        start();
        super.onResume();
    }

    private void start() {
        sm.registerListener(this, sensor, sm.SENSOR_DELAY_FASTEST);
    }

    private void stop() {
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(acelerometro) {
            tv_ejerun_sensores.setText("x: " + x);
        }
        if(comienzo) {
            if (acelerometro) {

                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                ejercicio();
            }
            if (proximity) {
                x = sensorEvent.values[0];
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void recuperarDato(String dato){
        this.dato = dato;
    }

    public void activarSensores(){
       // if(a.getMedidores().size()!=0) {
            sm = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            for (int i = 0; i < a.getMedidores().size(); i++) {
                //Acelerometro
                if (a.getMedidores().get(i) instanceof Acelerometro) {
                    sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    acelerometro = true;
                }
                //Contador
                if (!(a.tieneContador())){
                    tv_ejerun_tiempo.setText("--:--");
                }else if(a.getMedidores().get(i) instanceof Contador) {
                    contadorActividad = ((Contador)a.getMedidores().get(i)).getTiempo();
                    tv_ejerun_tiempo.setText(""+((Contador)a.getMedidores().get(i)).getTiempo()/1000);
                }
                //Proximity
                if (a.getMedidores().get(i) instanceof Proximity) {
                    sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    proximity = true;
                }

            }
        //}

        if(a != null){
           // tv_ejerun_tiempo.setText(a.getDuracionSegundos() + " Segundos");
            tv_ejerun_repeticiones.setText(""+a.getRepeticionesRealizadas()+ " / "+a.getRepeticiones());
        }
    }

    public void iniciarContador(){
        contador = new CountDownTimer(contadorActividad,1000){

        @Override
        public void onTick(long l) {
            tv_ejerun_tiempo.setText(String.valueOf(l/1000));
        }

        @Override
        public void onFinish() {
            contadorActividad++;
        }

    }.start();
}

    public void ejercicio(){
        if (medicion.getEstrategia().posicionCorrecta(x,y,z,contadorActividad)){
            vibrator.vibrate(1000);
            comienzo = false;
            iniciarContador();

        } else if (medicion.getEstrategia().posicionCorrecta(x,y,z,contadorActividad)) {
            vibrator.vibrate(1000);
            comienzo = false;
            iniciarContador();
        }

        if (contadorActividad == 2 && a.getRepeticionesRealizadas() < a.getRepeticiones()){
            a.setRepeticionesRealizadas(a.getRepeticionesRealizadas()+1);
            tv_ejerun_repeticiones.setText("" + a.getRepeticionesRealizadas()+ "/" + a.getRepeticiones());
            contadorActividad = 0;
            ejercicio();
        }
        if(a.getRepeticionesRealizadas() == a.getRepeticiones()){
            comienzo = false;
            tv_ejerun_repeticiones.setText("FIN");
        }
    }

    public void comenzar() {
        if(medicion.getEstrategia().posicionInicio(x,y,z)) {
            contadorEjercicio =0 ;
            comienzo = true;
        }
    }

}