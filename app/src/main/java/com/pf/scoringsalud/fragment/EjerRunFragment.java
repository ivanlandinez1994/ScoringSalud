package com.pf.scoringsalud.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.api.consumo.ApiPuntuable;
import com.pf.scoringsalud.api.infraestructura.PuntuableEndPoint;
import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.estrategiaMedicion.MedicionEjerRun;
import com.pf.scoringsalud.puntuable.medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.medidor.Contador;
import com.pf.scoringsalud.puntuable.medidor.Proximity;
import com.pf.scoringsalud.factory.FactoryPuntuable;
import com.pf.scoringsalud.R;

import static android.content.Context.SENSOR_SERVICE;

//

public class EjerRunFragment extends Fragment implements SensorEventListener {
    private  Button go, stop;
    private String dato;
    private CountDownTimer contador;
    private Actividad a;
    private MedicionEjerRun medicion;
    private String tipo;
    private TextView tv_ejerun_tiempo, tv_ejerun_repeticiones;
    private ImageView iv_ejedesc;
    int tiempoVibracion;
    private int tiempoEspera;
    private int tiempoActividad;
    private boolean actividadIniciada;

    private SensorManager sm;
    private Sensor sensor;
    private Vibrator vibrator;
    private MediaPlayer mp;

    private boolean acelerometro = false;
    private boolean proximity=false;
    private boolean comienzo =false;

    private double x;
    private double y;
    private double z;
    private int contadorEjercicio=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejer_run, container, false);
        tv_ejerun_tiempo = view.findViewById(R.id.tv_ejerun_tiempo);
        tv_ejerun_repeticiones = view.findViewById(R.id.tv_ejerun_repeticiones);
        iv_ejedesc = view.findViewById(R.id.iv_ejedesc);
        mp = MediaPlayer.create(getContext(),R.raw.beep);
        actividadIniciada = false;
        tiempoEspera=3000;
        tipo ="Actividad";

        stop= view.findViewById(R.id.btn_ejerrun_deneter);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comienzo = false;
                contador.cancel();
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
                medicion = new MedicionEjerRun(a);
                activarSensores();
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
        if(comienzo) {
            if (acelerometro) {
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                ejercicioAcelerometro();
            }
            if (proximity) {
                x = sensorEvent.values[0];
                if(x!=0){
                    contador.cancel();
                    actividadIniciada = false;
                    comienzo =false;
                }else{
                    ejercicioProximity();
                }

            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void recuperarDato(String dato){
        this.dato = dato;
    }

    private void activarSensores(){
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
                    tiempoActividad = ((Contador)a.getMedidores().get(i)).getTiempo();
                    tv_ejerun_tiempo.setText(""+((Contador)a.getMedidores().get(i)).getTiempo()/1000);
                }
                //Proximity
                if (a.getMedidores().get(i) instanceof Proximity) {
                    sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    proximity = true;
                }

            }
        //}
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        tv_ejerun_repeticiones.setText(a.getRepeticionesRealizadas()+" / "+a.getRepeticiones());
        iv_ejedesc.setImageResource(a.getRutaGif());
    }

    private void iniciarContador(int tiempo){
        contador = new CountDownTimer(tiempo,1000) {

            @Override
            public void onTick(long l) {
                tv_ejerun_tiempo.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                tiempoVibracion=500;
                if(contadorEjercicio ==1){
                    tiempoVibracion=tiempoVibracion*2;
                }
                vibrator.vibrate(tiempoVibracion);
                mp.start();
                contadorEjercicio++;
                if(proximity  ){
                    ejercicioProximity();
                }

            }

        }.start();
}

    private void iniciarContadorEspera(int tiempo){
        contador = new CountDownTimer(tiempo,1000) {

            @Override
            public void onTick(long l) {
                tv_ejerun_tiempo.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                tiempoVibracion=1000;
                vibrator.vibrate(tiempoVibracion);
                mp.start();
                actividadIniciada=true;
                comienzo = true;
                if(proximity){
                   ejercicioProximity();
                }else {
                    ejercicioAcelerometro();
                }
            }
        }.start();
    }

    private void ejercicioAcelerometro(){
        if(medicion.getEstrategia().posicionCorrecta(x,y,z,contadorEjercicio)){
            iniciarContador(tiempoActividad);
            comienzo = false;

        } else if (medicion.getEstrategia().posicionCorrecta(x,y,z,contadorEjercicio)) {

            iniciarContador(tiempoActividad);
            comienzo = false;
        }

        if (contadorEjercicio == 2 && a.getRepeticionesRealizadas() < a.getRepeticiones()){
            a.setRepeticionesRealizadas(a.getRepeticionesRealizadas()+1);
            tv_ejerun_repeticiones.setText("" + a.getRepeticionesRealizadas()+ "/" + a.getRepeticiones());
            contadorEjercicio = 0;
            ejercicioAcelerometro();
        }
        if(a.getRepeticionesRealizadas() == a.getRepeticiones() && comienzo){
            mp.start();
            mp.start();
            comienzo = false;
            exito();
        }
    }

    private void ejercicioProximity(){
        if (contadorEjercicio == 2 && a.getRepeticionesRealizadas() < a.getRepeticiones()) {
            a.setRepeticionesRealizadas(a.getRepeticionesRealizadas() + 1);
            tv_ejerun_repeticiones.setText("" + a.getRepeticionesRealizadas() + "/" + a.getRepeticiones());
            contadorEjercicio = 0;
        }
        if(!actividadIniciada){
            iniciarContadorEspera(tiempoEspera);

        }else if(a.getRepeticionesRealizadas() == a.getRepeticiones() && comienzo){
            mp.start();
            mp.start();
            comienzo = false;
            exito();

        }else if(a.getRepeticionesRealizadas() != a.getRepeticiones()){
                iniciarContador(tiempoActividad);
        }


    }

    private void comenzar() {
    //    if(medicion.getEstrategia().posicionInicio(x,y,z)) {
            comienzo = true;
   //     }
    }

    private void exito(){
        postPuntuable();
        fragmentEnd();
    }
    private void postPuntuable(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        PuntuableEndPoint pep = new PuntuableEndPoint(tipo,a.getNombre(),
                a.getPuntosOtorgables(),"Prueba",user.getEmail());
        ApiPuntuable ap = new ApiPuntuable();
        ap.crearPuntuable(pep,getContext());

    }
    private void fragmentEnd(){
        Bundle bundle1 = new Bundle();
        bundle1.putString("key",dato);
        getParentFragmentManager().setFragmentResult("eje_end",bundle1);
        EjerEndFragment ed= new EjerEndFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.ejerRun, ed );
        transaction.commit();
    }

}