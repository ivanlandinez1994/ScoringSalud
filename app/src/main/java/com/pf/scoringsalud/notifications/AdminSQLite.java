package com.pf.scoringsalud.notifications;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLite extends SQLiteOpenHelper {

    public AdminSQLite(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(context, nombre, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(" create table alarma( idal integer primary key autoincrement, encabezado text, dia integer, horaIni integer, minutoIni integer, horaFin integer, minutoFin integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists alarma" );
        db.execSQL(" create table alarma( idal integer primary key autoincrement,encabezado text,dia integer, horaIni integer, minutoIni integer, horaFin integer, minutoFin integer)");
    }


}
