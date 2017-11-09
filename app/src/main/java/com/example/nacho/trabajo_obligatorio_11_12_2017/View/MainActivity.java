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
import com.example.nacho.trabajo_obligatorio_11_12_2017.Controller.Registro;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Model.HttpConnection;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Properties.Listadatos_ws;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
        new ListadoMenu().execute();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.hamburguesa);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);
    }

    public class ListadoMenu extends AsyncTask<Void, Void, JSONArray>{


        String response = "";
        HashMap<String, String> postDataParams;
        //String urlparams = url + "title";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Buscando datos..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }



        @Override
        protected JSONArray doInBackground(Void... params) {

            postDataParams = new HashMap<String, String>();
            try {
                response = service.sendGet(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonResponse;
                jsonResponse = new JSONObject(response);
                status = jsonResponse.getInt("status");
                jsonArray = jsonResponse.optJSONArray("response");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (status == 200) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            Listadatos_ws objDatos = new Listadatos_ws();

                            int id;
                            String name;
                            String precio;

                            /* */

                            JSONObject objet = jsonArray.getJSONObject(i);

                            id = objet.getInt("id");
                            name = objet.getString("name");
                            precio = objet.getString("precio");

                            objDatos.setId(id);
                            objDatos.setNombre(name);
                            objDatos.setPrecio(precio);

                            lista.add(objDatos);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    adapter= new AdapterLista(MainActivity.this,lista,R.layout.item_lista_comida);
                    lstMenu.setAdapter(adapter);

                }

            }
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

                                /*- Pasta -*/

                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                            case R.id.item_navigation_drawer_minutas:

                                /*- Minutas -*/

                                return true;

                            case R.id.item_navigation_drawer_ensaladas:

                                /*- Ensalada -*/
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_carneParrilla:
                                /*- Parrilla -*/

                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_mariscos:

                                /*- Mariscos -*/

                                menuItem.setChecked(true);

                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_registrarse:

                                /*- Registrarse -*/

                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intent = new Intent(MainActivity.this, Registro.class);
                                startActivity(intent);

                                return true;

                            case R.id.item_navigation_drawer_sesion:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                /*- iniciar sesión -*/

                                Intent intentSesion = new Intent(MainActivity.this, Login.class);
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
