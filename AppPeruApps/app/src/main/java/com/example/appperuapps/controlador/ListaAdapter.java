package com.example.appperuapps.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appperuapps.R;
import com.example.appperuapps.modelo.Tareas;

import java.util.List;

public class ListaAdapter extends ArrayAdapter<Tareas> {

    public ListaAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListaAdapter(Context context, int resource, List<Tareas> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.lista_item, null);
        }

        Tareas p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tarea_titulo);
            TextView tt2 = (TextView) v.findViewById(R.id.tarea_status);


            if (tt1 != null) {
                tt1.setText(p.titulo);
            }

            if (tt2 != null) {

                tt2.setText(p.proyecto_nombre);
            }

        }

        return v;
    }

}