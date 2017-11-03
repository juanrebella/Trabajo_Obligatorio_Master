package com.example.nacho.trabajo_obligatorio_11_12_2017.Comidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.trabajo_obligatorio_11_12_2017.Comidas.Listapasta;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Controller.Login;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

public class Listapasta extends AppCompatActivity {

    Button lupaBuscar, carritoCompra, loginButton;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    TextView textView, listaPasta;
    Toolbar toolbar;
    ImageView pasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listapasta);

        pasta = (ImageView)findViewById(R.id.imgPasta);
        listaPasta = (TextView)findViewById(R.id.textPasta);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflamos el menú

        lupaBuscar = (Button)findViewById(R.id.buscar);
        carritoCompra = (Button)findViewById(R.id.carrito);

        getMenuInflater().inflate(R.menu.menu_icons, menu);

        return true;
    }

    public void setupNavigationDrawerContent( NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected( MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_pasta:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent= new Intent(getApplicationContext(), Listapasta.class);
                                startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_minutas:

                             /*Intent intent= new Intent(getApplicationContext(), ListadoActivity.class);
                            startActivity(intent);*/

                                /*Crear un intent */

                                return true;
                            case R.id.item_navigation_drawer_ensaladas:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_carneParrilla:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_mariscos:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                Toast.makeText(Listapasta.this, "Launching " + menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_settings:
                                menuItem.setChecked(true);
                                Toast.makeText(Listapasta.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_sesion:
                                menuItem.setChecked(true);
                                Toast.makeText(Listapasta.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_salir:
                                menuItem.setChecked(true);
                                Toast.makeText(Listapasta.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                        }
                        return true;
                    }
                });
    }
}
