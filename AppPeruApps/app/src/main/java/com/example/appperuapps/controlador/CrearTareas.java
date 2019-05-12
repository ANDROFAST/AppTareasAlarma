package com.example.appperuapps.controlador;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appperuapps.R;
import com.example.appperuapps.database.DatabaseHelper;
import com.example.appperuapps.modelo.Grupos;
import com.example.appperuapps.modelo.Tareas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrearTareas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_crear_tareas);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final DatePicker pickerDate = (DatePicker) findViewById(R.id.datePicker);
        final TimePicker pickerTime = (TimePicker) findViewById(R.id.timePicker);
        final TextView hora = (TextView) findViewById(R.id.txtHora);
        final TextView fecha = (TextView) findViewById(R.id.txtFecha);

        final Spinner mSpinner = (Spinner) findViewById(R.id.spinnerNoteType);
        final CheckBox checkBoxAlarm = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox checkboxnotify = (CheckBox) findViewById(R.id.checkBox2);

        final Spinner pr = (Spinner) findViewById(R.id.spinner);

        final EditText ti = (EditText) findViewById(R.id.edtTitulo);
        final EditText de = (EditText) findViewById(R.id.edtDescripcion);
        Button sa = (Button) findViewById(R.id.btnRegistrarTarea);


        pickerDate.setVisibility(View.INVISIBLE);
        pickerTime.setVisibility(View.INVISIBLE);
        hora.setVisibility(View.INVISIBLE);
        fecha.setVisibility(View.INVISIBLE);

        DatabaseHelper db = new DatabaseHelper(this);
        final List<Grupos> proyectos = db.getAllProyectos();
        final List<String> strs = new ArrayList<String>();

        for (Grupos t : proyectos) {
            strs.add(t.titulo);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, strs);
        pr.setAdapter(adapter);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                this, R.array.tipo_nota, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter2);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView parent, View view, int position, long id) {
                if (id == 0) {
                    showToast(getString(R.string.added_alert));
                    checkBoxAlarm.setEnabled(true);
                } else {
                    checkBoxAlarm.setEnabled(false);
                    checkBoxAlarm.setChecked(false);
                }
                if (id == 1) {
                    showToast(getString(R.string.notificacion));
                    checkboxnotify.setEnabled(true);
                } else {
                    checkboxnotify.setEnabled(false);
                    checkboxnotify.setChecked(false);
                }
            }

            public void onNothingSelected(AdapterView parent) {
            }
        });
        checkBoxAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pickerDate.setVisibility(View.VISIBLE);
                    pickerTime.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    fecha.setVisibility(View.VISIBLE);
                } else {
                    pickerDate.setVisibility(View.INVISIBLE);
                    pickerTime.setVisibility(View.INVISIBLE);
                    hora.setVisibility(View.INVISIBLE);
                    fecha.setVisibility(View.INVISIBLE);
                }
            }
        });
        checkboxnotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pickerDate.setVisibility(View.VISIBLE);
                    pickerTime.setVisibility(View.VISIBLE);
                    hora.setVisibility(View.VISIBLE);
                    fecha.setVisibility(View.VISIBLE);
                } else {
                    pickerDate.setVisibility(View.INVISIBLE);
                    pickerTime.setVisibility(View.INVISIBLE);
                    hora.setVisibility(View.INVISIBLE);
                    fecha.setVisibility(View.INVISIBLE);
                }
            }
        });


        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ti.getText().toString().length() > 0 && pr.getSelectedItem() != null) {
                    DatabaseHelper db = new DatabaseHelper(v.getContext());
                    Tareas tm = new Tareas();
                    tm.titulo = ti.getText().toString();
                    tm.descripcion = de.getText().toString();
                    tm.proyecto_id = proyectos.get(pr.getSelectedItemPosition()).id;


                    if (checkBoxAlarm.isChecked()) {
                        Calendar calender = Calendar.getInstance();
                        calender.clear();
                        calender.set(Calendar.MONTH, pickerDate.getMonth());
                        calender.set(Calendar.DAY_OF_MONTH, pickerDate.getDayOfMonth());
                        calender.set(Calendar.YEAR, pickerDate.getYear());
                        calender.set(Calendar.HOUR, pickerTime.getCurrentHour());
                        calender.set(Calendar.MINUTE, pickerTime.getCurrentMinute());
                        calender.set(Calendar.SECOND, 00);

                        SimpleDateFormat formateoHora = new SimpleDateFormat(getString(R.string.hora_minutos));
                        String timeString = formateoHora.format(new Date(calender.getTimeInMillis()));
                        SimpleDateFormat formateoFecha = new SimpleDateFormat(getString(R.string.formateo_fecha));
                        String dateString = formateoFecha.format(new Date(calender.getTimeInMillis()));

                        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);


                        String alertaTitulo = ti.getText().toString();
                        intent.putExtra(getString(R.string.alerta_título), alertaTitulo);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                        alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);
                        tm.hora = timeString;
                        tm.fecha = dateString;

                    } else if (checkboxnotify.isChecked()) {
                        Calendar calender = Calendar.getInstance();
                        calender.clear();
                        calender.set(Calendar.MONTH, pickerDate.getMonth());
                        calender.set(Calendar.DAY_OF_MONTH, pickerDate.getDayOfMonth());
                        calender.set(Calendar.YEAR, pickerDate.getYear());
                        calender.set(Calendar.HOUR, pickerTime.getCurrentHour());
                        calender.set(Calendar.MINUTE, pickerTime.getCurrentMinute());
                        calender.set(Calendar.SECOND, 00);

                        SimpleDateFormat formateoHora = new SimpleDateFormat(getString(R.string.hora_minutos));
                        String timeString = formateoHora.format(new Date(calender.getTimeInMillis()));
                        SimpleDateFormat formateoFecha = new SimpleDateFormat(getString(R.string.formateo_fecha));
                        String dateString = formateoFecha.format(new Date(calender.getTimeInMillis()));

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), GestionarNotificacion.class);

                        String alertaTitulo = ti.getText().toString();
                        String alertaDescripcion = de.getText().toString();
                        intent.putExtra(getString(R.string.alerta_título), alertaTitulo);
                        intent.putExtra(getString(R.string.alerta_contenido), alertaDescripcion);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

                        tm.hora = timeString;
                        tm.fecha = dateString;
                    }

                    db.agregarTareas(tm);
                    finish();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
