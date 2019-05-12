package com.example.appperuapps.controlador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appperuapps.R;
import com.example.appperuapps.database.DatabaseHelper;
import com.example.appperuapps.modelo.Grupos;

public class CrearGrupoTareas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupos);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final EditText ti = (EditText) findViewById(R.id.titulot);
        final EditText de = (EditText) findViewById(R.id.descripciont);
        Button sa =  (Button) findViewById(R.id.btnGuardarT);

        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ti.getText().toString().length()>0 ){
                    int imporant=0;
                    DatabaseHelper db = new DatabaseHelper(v.getContext());
                    Grupos tm = new Grupos();
                    tm.titulo  = ti.getText().toString();
                    tm.descripcion =  de.getText().toString();
                    db.agregarProyectos(tm);
                    finish();

                }
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
