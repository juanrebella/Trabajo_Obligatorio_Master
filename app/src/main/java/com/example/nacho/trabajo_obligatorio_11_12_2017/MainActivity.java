package com.example.nacho.trabajo_obligatorio_11_12_2017;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.mipmap.ic_lupa);
        actionbar.setDisplayUseLogoEnabled(true);
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.nav_drawer);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflamos el menú
        getMenuInflater().inflate(R.menu.menu_icons, menu);
        return true;
    }
}
