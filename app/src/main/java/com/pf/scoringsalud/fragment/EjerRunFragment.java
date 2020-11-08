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
import android.widget.ImageView;
import android.widget.TextView;

<<<<<<< HEAD:app/src/main/java/com/pf/scoringsalud/EjerRun.java
import com.pf.scoringsalud.Factorys.FactoryPuntuable;
import com.pf.scoringsalud.Puntuable.Actividad;
import com.pf.scoringsalud.Puntuable.EstrategiaMedicion.MedicionEjerRun;
import com.pf.scoringsalud.Puntuable.Medidor.Acelerometro;
import com.pf.scoringsalud.Puntuable.Medidor.Contador;
import com.pf.scoringsalud.Puntuable.Medidor.Proximity;
=======
import com.pf.scoringsalud.factory.FactoryPuntuable;
import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.EstrategiaMedicion.MedicionEjerRun;
import com.pf.scoringsalud.puntuable.Medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.Medidor.Contador;
import com.pf.scoringsalud.puntuable.Medidor.Proximity;
import com.pf.scoringsalud.R;
>>>>>>> cfcd42294e0395ee178c1f8289ea208e5e112984:app/src/main/java/com/pf/scoringsalud/fragment/EjerRunFragment.java

import static android.content.Context.SENSOR_SERVICE;

//
<<<<<<< HEAD:app/src/main/java/com/pf/scoringsalud/EjerRun.java
public class EjerRun extends Fragment implements SensorEventListener {
    Button go, stop;
=======
public class EjerRunFragment extends Fragment implements SensorEventListener {
    Button go, end;
>>>>>>> cfcd42294e0395ee178c1f8289ea208e5e112984:app/src/main/java/com/pf/scoringsalud/fragment/EjerRunFragment.java
    String dato;
    CountDownTimer contador;
    Actividad a;
    MedicionEjerRun medicion;
    TextView tv_ejerun_tiempo, tv_ejerun_repeticiones, tv_ejerun_sensores;
    ImageView iv_ejedesc;

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
    private int contadorEjercicio=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ejer_run, container, false);
        tv_ejerun_tiempo = view.findViewById(R.id.tv_ejerun_tiempo);
        tv_ejerun_repeticiones = view.findViewById(R.id.tv_ejerun_repeticiones);
        tv_ejerun_sensores = view.findViewById(R.id.tv_ejerRun_sensores);
        iv_ejedesc = view.findViewById(R.id.iv_ejedesc);

        stop= view.findViewById(R.id.btn_ejerrun_deneter);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD:app/src/main/java/com/pf/scoringsalud/EjerRun.java
                comienzo = false;
                contador.cancel();
=======
                EjerEndFragment ed= new EjerEndFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.ejerDesc, ed );
                transaction.commit();
>>>>>>> cfcd42294e0395ee178c1f8289ea208e5e112984:app/src/main/java/com/pf/scoringsalud/fragment/EjerRunFragment.java
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
//tv_ejerun_sensores.setText(((Acelerometro)((EstrategiaEjerRun_Acelerometro)medicion.getEstrategia()).getAcelerometro()).getPosicionUno()[0]);
        if(comienzo) {
            if (acelerometro) {
                tv_ejerun_sensores.setText("x: " + String.valueOf(x).substring(0,2) +" Y: "
                        +String.valueOf(y).substring(0,2)+" Z: "+String.valueOf(z).substring(0,2)+
                        " Cont:"+ contadorEjercicio);
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                ejercicioAcelerometro();
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
        tv_ejerun_repeticiones.setText(a.getRepeticionesRealizadas()+" / "+a.getRepeticiones());
        iv_ejedesc.setImageResource(a.getRutaGif());
    }

    private void iniciarContador(){
        contador = new CountDownTimer(contadorActividad,1000) {

            @Override
            public void onTick(long l) {
                tv_ejerun_tiempo.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                contadorEjercicio++;
                comienzo = true;
            }

        }.start();
}

    private void ejercicioAcelerometro(){
        if(medicion.getEstrategia().posicionCorrecta(x,y,z,contadorEjercicio)){
          // vibrator.vibrate(1000);
            iniciarContador();
            comienzo = false;

        } else if (medicion.getEstrategia().posicionCorrecta(x,y,z,contadorEjercicio)) {
         // vibrator.vibrate(1000);
            iniciarContador();
            comienzo = false;
        }

        if (contadorEjercicio == 2 && a.getRepeticionesRealizadas() < a.getRepeticiones()){
            a.setRepeticionesRealizadas(a.getRepeticionesRealizadas()+1);
            tv_ejerun_repeticiones.setText("" + a.getRepeticionesRealizadas()+ "/" + a.getRepeticiones());
            contadorEjercicio = 0;
            ejercicioAcelerometro();
        }
        if(a.getRepeticionesRealizadas() == a.getRepeticiones()){
            comienzo = false;
            exito();
        }
    }

    private void comenzar() {
        if(medicion.getEstrategia().posicionInicio(x,y,z)) {
            comienzo = true;
        }
    }

    private void exito(){
    Bundle bundle1 = new Bundle();
    bundle1.putString("key",dato);
    getParentFragmentManager().setFragmentResult("eje_end",bundle1);
    EjerEnd ed= new EjerEnd();
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.ejerRun, ed );
    transaction.commit();

    }
}