package com.pf.scoringsalud.Factorys;


import com.pf.scoringsalud.Puntuable.Actividad;
import com.pf.scoringsalud.Puntuable.Medidor.Acelerometro;
import com.pf.scoringsalud.Puntuable.Medidor.Contador;
import com.pf.scoringsalud.Puntuable.Medidor.Medible;
import com.pf.scoringsalud.Puntuable.Puntuable;

import java.util.ArrayList;

public class FactoryPuntuable {
    public FactoryPuntuable(){

    };

    public static Puntuable actividad(String codigo){
        String n = codigo.toUpperCase();
        Puntuable p;
        int[] posicionUno;
        int[] posicionDos;
        Medible acelerometro;
        Medible contador;
        ArrayList<Medible> medibles;
        switch(n){
            case "HOMBRO":

                posicionUno = new int[]{8,0,1};
                posicionDos = new int[]{-8,0,1};
                acelerometro = new Acelerometro(posicionUno,posicionDos);
                contador = new Contador(10000);
                medibles = new ArrayList<Medible>();
                medibles.add(acelerometro);
                medibles.add(contador);


                p = new Actividad(codigo,"Estiramiento de HOMBRO",100,"Hombro",
                        "","",false,3,medibles);
                break;

            case "CADERA":

                posicionUno = new int[]{8,0,1};
                posicionDos = new int[]{-8,0,0};
                acelerometro = new Acelerometro(posicionUno,posicionDos);
                contador = new Contador(10000);
                medibles = new ArrayList<Medible>();
                medibles.add(acelerometro);
                medibles.add(contador);

                p = new Actividad(codigo,"Estiramiento de CADERA",100,"CADERA",
                        "","",false,3,medibles);
                break;

                case "CUELLO":

                    posicionUno = new int[]{8,0,0};
                    posicionDos = new int[]{-8,0,0};
                    acelerometro = new Acelerometro(posicionUno,posicionDos);
                    contador = new Contador(5000);
                    medibles = new ArrayList<Medible>();
                    medibles.add(acelerometro);
                    medibles.add(contador);

                    p = new Actividad(codigo,"Estiramiento de CUELLO",100,"CUELLO",
                            "","",false,2,medibles);
                    break;
            default:
                p=null;
                break;
        }
        return p;
    }


}
