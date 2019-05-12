package com.example.appperuapps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appperuapps.modelo.Grupos;
import com.example.appperuapps.modelo.Tareas;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public  DatabaseHelper(Context context){
        super(context, "Tareas", null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tarea (id integer primary key, titulo text, descripcion text, finalizado int default 0, fecha text, proyecto_id int, hora text,tipo text)");
        db.execSQL("create table listaTareas (id integer primary key, titulo text, descripcion text, fecha_creacion timestamp default current_timestamp)");
        ContentValues lt = new ContentValues();
        lt.put("titulo","Bañar al perro");
        db.insert("listaTareas",null,lt);
        lt = new ContentValues();
        lt.put("titulo","Cumpleaños de Alex");
        db.insert("listaTareas",null,lt);
        lt = new ContentValues();
        lt.put("titulo","Comprar viveres");
        db.insert("listaTareas",null,lt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tarea");
        db.execSQL("drop table if exists listaTareas");
    }
    public void agregarTareas(Tareas tareas){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues lt = new ContentValues();
        lt.put("titulo",tareas.titulo);
        lt.put("descripcion",tareas.descripcion);
        lt.put("proyecto_id",tareas.descripcion);
        lt.put("fecha",tareas.fecha);
        lt.put("hora",tareas.hora);
        lt.put("tipo",tareas.tipo);
        db.insert("tarea",null,lt);
        db.close();
    }
    public void agregarProyectos(Grupos grupos){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues lt = new ContentValues();
        lt.put("titulo", grupos.titulo);
        lt.put("descripcion", grupos.descripcion);
        db.insert("proyecto",null,lt);
        db.close();
    }

    public Tareas getTareas(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =   db.rawQuery("select * from tarea where id="+id,null);

        if(c!=null){
            c.moveToFirst();
        }

        Tareas t = new Tareas();
        t.id = c.getInt(0);
        t.titulo = c.getString(1);
        t.descripcion = c.getString(2);
        t.finalizado = c.getInt(3);
        t.fecha = c.getString(4);
        t.proyecto_id= c.getInt(5);
        t.hora = c.getString(6);
        t.tipo= c.getString(7);
        db.close();

        return t;
    }

    public Grupos getProyectos(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =   db.rawQuery("select * from listaTareas where id="+id,null);

        if(c!=null){
            c.moveToFirst();
        }

        Grupos p = new Grupos();
        p.id = c.getInt(0);
        p.titulo = c.getString(1);
        p.descripcion = c.getString(2);
        p.fecha_creacion = c.getString(3);
        db.close();

        return p;
    }

    public void finalizarTarea(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("finalizado",1);

        db.update("tarea", values, "id" + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void eliminarTarea(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tarea", "id"+ " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void eliminarProyecto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("listaTareas", "id"+ " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
    public List<Tareas> getAllTareas(){
        SQLiteDatabase db = getReadableDatabase();
        List<Tareas> tasks = new ArrayList<Tareas>();
        Cursor c = db.rawQuery("select * from tarea where finalizado=0",null);
        if(c.moveToFirst()){
            do{
                Tareas t = new Tareas();
                t.id = c.getInt(0);
                t.titulo = c.getString(1);
                t.descripcion = c.getString(2);
                t.finalizado = c.getInt(3);
                t.fecha = c.getString(4);
                t.tipo = c.getString(5);
                t.hora = c.getString(6);
                t.proyecto_id= c.getInt(7);

                tasks.add(t);
            }while (c.moveToNext());
        }
        db.close();

        return tasks;
    }

    public List<Grupos> getAllProyectos(){
        SQLiteDatabase db = getReadableDatabase();
        List<Grupos> tasks = new ArrayList<Grupos>();
        Cursor c = db.rawQuery("select * from listaTareas",null);
        if(c.moveToFirst()){
            do{
                Grupos t = new Grupos();
                t.id = c.getInt(0);
                t.titulo = c.getString(1);
                t.descripcion = c.getString(2);
                t.fecha_creacion = c.getString(3);
                tasks.add(t);
            }while (c.moveToNext());
        }
        db.close();
        return tasks;
    }


    public List<Tareas> getFinilizarTareas(){
        SQLiteDatabase db = getReadableDatabase();
        List<Tareas> tasks = new ArrayList<Tareas>();
        Cursor c = db.rawQuery("select * from tarea where finalizado=1",null);
        if(c.moveToFirst()){
            do{
                Tareas t = new Tareas();
                t.id = c.getInt(0);
                t.titulo = c.getString(1);
                t.descripcion = c.getString(2);
                t.finalizado = c.getInt(3);
                t.fecha = c.getString(4);
                t.proyecto_id= c.getInt(5);
                t.fecha = c.getString(6);
                tasks.add(t);
            }while (c.moveToNext());
        }
        db.close();
        return tasks;
    }
}

