package com.pf.scoringsalud.factory;


import com.pf.scoringsalud.puntuable.Actividad;
import com.pf.scoringsalud.puntuable.Medidor.Acelerometro;
import com.pf.scoringsalud.puntuable.Medidor.Contador;
import com.pf.scoringsalud.puntuable.Medidor.Medible;
import com.pf.scoringsalud.puntuable.Puntuable;

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
                        2131165332,"",false,3,medibles);
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

                posicionUno = new double[]{8,0,0};
                posicionDos = new double[]{-8,0,0};
                acelerometro = new Acelerometro(posicionUno,posicionDos);
                contador = new Contador(5000);
                medibles = new ArrayList<Medible>();
                medibles.add(acelerometro);
                medibles.add(contador);

                p = new Actividad(codigo,"Estiramiento de CUELLO",100,"CUELLO",
                        2131165334,"",false,1,medibles);
                break;
            case "RODILLA":

                posicionUno = new double[]{8,0,0};
                posicionDos = new double[]{-8,0,0};
                acelerometro = new Acelerometro(posicionUno,posicionDos);
                contador = new Contador(5000);
                medibles = new ArrayList<Medible>();
                medibles.add(acelerometro);
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
