package com.example.appperuapps.controlador;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.appperuapps.R;


public class GestionarAlerta extends Activity {
MediaPlayer mediaPlayer;
int reso =R.raw.chec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_alerta);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),reso);
        mediaPlayer.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String mensaje = getString(R.string.alarmatext) + getIntent().getExtras().getString(getString(R.string.titulo_mensaje));
        builder.setMessage(mensaje).setCancelable(
                false).setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        GestionarAlerta.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override

    public void onDestroy() {

        super.onDestroy();

        mediaPlayer.release();

    }

}
