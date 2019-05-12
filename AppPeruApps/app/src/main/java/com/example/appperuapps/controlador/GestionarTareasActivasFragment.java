package com.example.appperuapps.controlador;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appperuapps.R;
import com.example.appperuapps.database.DatabaseHelper;
import com.example.appperuapps.modelo.Tareas;

import java.util.ArrayList;
import java.util.List;


public class GestionarTareasActivasFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view= inflater.inflate(R.layout.fragment_gestionar_tareas_activas_fragment, container, false);


        DatabaseHelper db = new DatabaseHelper(getContext());
        final List<Tareas> tareas = db.getAllTareas();
        ListView lv = (ListView) view.findViewById(R.id.list_main);

        final List<String> strs = new ArrayList<String>();

        for(Tareas t: tareas) {
            strs.add(t.titulo);
        }


        final ListaAdapter listaAdapter = new ListaAdapter(getContext(), R.layout.lista_item, tareas);

        lv.setAdapter(listaAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int posicion, long id) {

                final Tareas t = tareas.get(posicion);


                AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle(t.id+" - "+t.titulo);
                alert.setMessage(t.fecha+" - " +t.hora+" \n " +t.descripcion);

                alert.setButton(AlertDialog.BUTTON_POSITIVE,"Finalizar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface d, int which){

                        DatabaseHelper db = new DatabaseHelper(getContext());
                        db.finalizarTarea(t.id);
                        tareas.remove(posicion);
                        listaAdapter.notifyDataSetChanged();
                        d.dismiss();
                    }
                });
                alert.setButton(AlertDialog.BUTTON_NEGATIVE,"Eliminar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface d, int which){

                        DatabaseHelper db = new DatabaseHelper(getContext());
                        db.eliminarTarea(t.id);
                        tareas.remove(posicion);
                        listaAdapter.notifyDataSetChanged();
                        d.dismiss();
                    }
                });

                alert.setButton(AlertDialog.BUTTON_NEUTRAL,"Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface d, int which){
                        d.dismiss();
                    }
                });


                alert.show();
            }
        });

        return view;
    }
}
