package com.example.appperuapps.controlador;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.appperuapps.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String Title = intent.getStringExtra(context.getString(R.string.tituloo));
        Intent x = new Intent(context, GestionarAlerta.class);
        x.putExtra(context.getString(R.string.tituloo), Title);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(x);
    }
}

