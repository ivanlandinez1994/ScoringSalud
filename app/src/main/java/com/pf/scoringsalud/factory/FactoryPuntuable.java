package com.pf.scoringsalud.factory;


import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.medidor.Contador;
import com.pf.scoringsalud.puntuable.medidor.Medible;
import com.pf.scoringsalud.puntuable.Puntuable;
import com.pf.scoringsalud.puntuable.medidor.Proximity;

import java.util.ArrayList;

public class FactoryPuntuable {
    public FactoryPuntuable(){

    };

    public static Puntuable actividad(String codigo){
        String n = codigo.toUpperCase();
        Puntuable p;
        double[] posicionUno;
        double[] posicionDos;
        Medible acelerometro;
        Medible proximity;
        Medible contador;
        ArrayList<Medible> medibles;
        switch(n){
            case "HOMBRO":

                posicionUno = new double[]{8,0,1};
                posicionDos = new double[]{-8,0,1};
                acelerometro = new Acelerometro(posicionUno,posicionDos);
                contador = new Contador(10000);
                medibles = new ArrayList<Medible>();
                medibles.add(acelerometro);
                medibles.add(contador);

                p = new Actividad(codigo,"Estiramiento de HOMBRO",100,"Hombro",
                        2131165332,"Sostener el celular con la mano derecha mirando hacia abajo y apuntando a su izquierda" +
                        ". Estirar el brazo, colocar la otra mano en el codo y tirar. Una vez finalizado el contador, repetir con el brazo contrario," +
                        " cambiando el celular de mano, mirando hacia abajo y apuntando a su derecha.",false,3,medibles);
                break;

            case "CADERA":

                posicionUno = new double[]{8,0,1};
                posicionDos = new double[]{-8,0,0};
                acelerometro = new Acelerometro(posicionUno,posicionDos);
                contador = new Contador(10000);
                medibles = new ArrayList<Medible>();
                medibles.add(acelerometro);
                medibles.add(contador);

                p = new Actividad(codigo,"Estiramiento de CADERA",100,"CADERA",
                        2131165328,"Colocarse de pie y dar un paso al frente con" +
                        " una de las piernas, flexionando ligeramente. Luego, estirar la pierna " +
                        "contraria totalmente estirada hacia atrás y flexionar la de adelante. " +
                        "Asegurarse que la pelvis vaya hacia adelante. Realizar 10 repeticiones " +
                        "(vaivenes) con cada pierna. Se conseguirá así un estiramiento de los" +
                        " flexores de la pierna atrasada.",false,3,medibles);
                break;

                case "CUELLO":

                    posicionUno = new double[]{8,0,0};
                    posicionDos = new double[]{-8,0,0};
                    acelerometro = new Acelerometro(posicionUno,posicionDos);
                    contador = new Contador(5000);
                    medibles = new ArrayList<Medible>();
                    medibles.add(acelerometro);
                    medibles.add(contador);

                    p = new Actividad(codigo,"Estiramiento de CUELLO",100,"CUELLO",
                            2131165330,"",false,1,medibles);
                    break;
            case "MUNIECA":

                posicionUno = new double[]{-8,0,0};
                posicionDos = new double[]{8,0,0};
                acelerometro = new Acelerometro(posicionUno,posicionDos);
                contador = new Contador(5000);
                medibles = new ArrayList<Medible>();
                medibles.add(acelerometro);
                medibles.add(contador);

                p = new Actividad(codigo,"Estiramiento de MUÑECAS",100,"MUÑECAS",
                        2131165334,"Sostener el Celular de forma horizontal mirando hacia abajo" +
                        ". Doblar la muñeca hacia abajo logrando que el celular te mire a vos " +
                        "y el contador se iniciará. Al llegar a cero doblar la muñeca hacia arriba, logrando que el celular " +
                        "mire hacia adelante, el contador se iniciará y al llegar a cero, la vibracion le indicara que debe cambiar de mano"+
                        " para repetir los mismos movimientos",false,3,medibles);
                break;
            case "RODILLA":

                proximity = new Proximity();
                contador = new Contador(5000);
                medibles = new ArrayList<Medible>();
                medibles.add(proximity);
                medibles.add(contador);

                p = new Actividad(codigo,"Estiramiento de CUELLO",100,"CUELLO",
                        2131165336,"",false,1,medibles);
                break;
            default:
                p=null;
                break;
        }
        return p;
    }


}
