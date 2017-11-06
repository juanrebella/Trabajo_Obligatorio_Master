package com.example.nacho.trabajo_obligatorio_11_12_2017.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.trabajo_obligatorio_11_12_2017.Adapter.AdapterLista;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Config.URL_Rest;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Controller.Login;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Model.HttpConnection;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Properties.Listadatos_ws;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

import org.json.JSONArray;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

         /*---Variables para AsyncTask-----*/

    private ListView listViewData;
    ProgressDialog progressDialog;

    private String url = URL_Rest.urlListArticulos;

    private JSONArray jsonArray;
    private HttpConnection service;
    private int status = 0;


    AdapterLista adapter;
    public List<Listadatos_ws> lista = new LinkedList<Listadatos_ws>();
    private ListView lstMenu;


            /*----------- Variables -------------*/

    Button lupaBuscar, carritoCompra, loginButton;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    TextView textView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*- Lista de ofertas -*/
        lstMenu = (ListView)findViewById(R.id.lstMenu);

        /*- iniciamos servicio -*/

        service = new HttpConnection();
        new ListadoOfertas.excecute();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.hamburguer);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);
    }

    public class ListadoOfertas extends AsyncTask<Void, Void, JSONArray>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflamos el menú

        getMenuInflater().inflate(R.menu.menu_icons, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //ABRIMOS EL DRAWER
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.buscar:
                //Abrimos el buscador
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
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

                            case R.id.item_navigation_drawer_minutas:

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
                                Toast.makeText(MainActivity.this, "Launching " + menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_settings:

                                /*- Settings -*/

                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);

                                return true;

                            case R.id.item_navigation_drawer_sesion:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                /*- iniciar sesión -*/

                                Intent intentSesion = new Intent(MainActivity.this, Login.class);
                                finish();
                                startActivity(intentSesion);
                                return true;

                            case R.id.item_navigation_drawer_salir:

                                /*- Logout y quemar el token del ws-*/
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                        }
                        return true;
                    }
                });
    }
}
