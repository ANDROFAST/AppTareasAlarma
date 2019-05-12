package com.example.appperuapps.controlador;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.appperuapps.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        final TabLayout tablay = (TabLayout)findViewById(R.id.tab_layout);
        tablay.addTab(tablay.newTab().setText("Pendientes"));
        tablay.addTab(tablay.newTab().setText("Finalizadas"));
        tablay.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager tab1 = (ViewPager) findViewById(R.id.vTab);
        final TabAdapter tab = new TabAdapter(getSupportFragmentManager(), tablay.getTabCount());
        tab1.setAdapter(tab);

        tab1.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tablay){

        });

        tablay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                final TabAdapter pa = new TabAdapter(getSupportFragmentManager(), tablay.getTabCount());
                tab1.setAdapter(pa);
                tab1.setCurrentItem(tab.getPosition());
                pa.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                final TabAdapter pa = new TabAdapter(getSupportFragmentManager(), tablay.getTabCount());
                tab1.setAdapter(pa);
                tab1.setCurrentItem(tab.getPosition());
                pa.notifyDataSetChanged();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.nueva_tareas) {
            Intent i = new Intent(MainActivity.this, CrearTareas.class);
            startActivity(i);
        }
        else if (id == R.id.nuevos_grupos) {
            Intent i = new Intent(MainActivity.this, CrearGrupoTareas.class);
            startActivity(i);
        }
        else if (id == R.id.lista_grupos) {
            Intent i = new Intent(MainActivity.this, GruposActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}