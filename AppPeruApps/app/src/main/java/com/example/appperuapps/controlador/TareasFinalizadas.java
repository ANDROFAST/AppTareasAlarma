package com.example.appperuapps.controlador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appperuapps.R;
import com.example.appperuapps.database.DatabaseHelper;
import com.example.appperuapps.modelo.Tareas;

import java.util.ArrayList;
import java.util.List;

public class TareasFinalizadas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_finalizadas);
        DatabaseHelper db = new DatabaseHelper(this);
        final List<Tareas> tasks = db.getFinilizarTareas();
        ListView lv = (ListView) findViewById(R.id.list2);

        final List<String> listaTarea = new ArrayList<String>();

        for(Tareas t: tasks) {
            listaTarea.add(t.titulo);
        }

        final ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaTarea);

        lv.setAdapter(ada);


    }
}
