package com.example.appperuapps.controlador;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {
    int ntabs;

    public TabAdapter(FragmentManager fm, int nt){
        super(fm);
        this.ntabs=nt;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                Fragment f = new GestionarTareasActivasFragment();
                return f;
            case 1:
                Fragment f1 = new MostrarTareasFinalizadasFragments();
                return f1;
            default: return null;
        }
    }

    public int getCount(){
        return ntabs;
    }

}
