package com.example.appperuapps.controlador;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.appperuapps.R;
import com.example.appperuapps.database.DatabaseHelper;
import com.example.appperuapps.modelo.Tareas;

import java.util.List;

public class MostrarTareasFinalizadasFragments extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_tareas_finalizadas_fragment, container, false);

        DatabaseHelper db = new DatabaseHelper(getContext());
        final List<Tareas> tasks = db.getFinilizarTareas();
        ListView lv = (ListView) view.findViewById(R.id.lv_main2);


        final ListaAdapter ada= new ListaAdapter(getContext(), R.layout.lista_item, tasks);

        lv.setAdapter(ada);
        return view;

    }

}
