package com.example.appperuapps.controlador;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appperuapps.R;
import com.example.appperuapps.database.DatabaseHelper;
import com.example.appperuapps.modelo.Grupos;

import java.util.ArrayList;
import java.util.List;

public class GruposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final DatabaseHelper db = new DatabaseHelper(this);
        final List<Grupos> grupos = db.getAllProyectos();
        ListView lv = (ListView) findViewById(R.id.projects_list);

        final List<String> strs = new ArrayList<String>();

        for(Grupos t: grupos) {
            strs.add(t.titulo);
        }

        final ArrayAdapter<String> ada= new ArrayAdapter(this, android.R.layout.simple_list_item_1, strs);

        lv.setAdapter(ada);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Grupos t = grupos.get(position);


                AlertDialog alert = new AlertDialog.Builder(GruposActivity.this).create();
                alert.setTitle(t.id+" "+t.titulo);
                alert.setMessage(t.descripcion);

                alert.setButton(AlertDialog.BUTTON_NEGATIVE,"Eliminar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface d, int which){

                        db.eliminarProyecto(t.id);
                        grupos.remove(position);
                        ada.notifyDataSetChanged();
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

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
