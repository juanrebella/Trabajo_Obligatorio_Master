package com.example.nacho.trabajo_obligatorio_11_12_2017.View;

        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.nacho.trabajo_obligatorio_11_12_2017.Adapter.AdapterLista;
        import com.example.nacho.trabajo_obligatorio_11_12_2017.Config.URL_Rest;
        import com.example.nacho.trabajo_obligatorio_11_12_2017.Model.HttpConnection;
        import com.example.nacho.trabajo_obligatorio_11_12_2017.Properties.Listadatos_ws;
        import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.LinkedList;
        import java.util.List;

public class EnsaladasDrawer extends AppCompatActivity {


         /*---Variables para AsyncTask-----*/

    private ListView lstEnsalada;
    ProgressDialog progressDialog;

    private String url = URL_Rest.urlBuscarProducto;

    private JSONArray jsonArray;
    private HttpConnection service;
    private int status = 0;


    /*-- Variables traidas del adaptador ---*/

    AdapterLista adapter;
    public List<Listadatos_ws> lista = new LinkedList<Listadatos_ws>();

    private String token;
    private boolean loggedIn;
    private int userId;

            /*----------- Variables -------------*/

    Button lupaBuscar, carritoCompra, loginButton;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    TextView textView;
    Toolbar toolbar;

    private String idUserDeveloper = "1";
    private String typemeals = "2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensaladas_drawer);



        /*- Lista de ofertas -*/
        lstEnsalada = (ListView) findViewById(R.id.lstEnsalada);

        /*- iniciamos servicio -*/

        service = new HttpConnection();
        new ListadoPasta().execute();


        /*- Action Bar-*/
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

    public class ListadoPasta extends AsyncTask<Void, Void, JSONArray> {


        String response = "";
        HashMap<String, String> postDataParams;
        //String urlparams = url + "title";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(EnsaladasDrawer.this);
            progressDialog.setMessage("Buscando datos..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected JSONArray doInBackground(Void... params) {

            postDataParams = new HashMap<String, String>();
            postDataParams.put("userId", idUserDeveloper);
            postDataParams.put("search", typemeals);

            response = service.ServerDataHeader(url, postDataParams);

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
                            String imagenes;

                            /* */

                            JSONObject objet = jsonArray.getJSONObject(i);

                            id = objet.getInt("id");
                            name = objet.getString("name");
                            precio = objet.getString("precio");
                            imagenes = objet.getString("nameImage");

                            objDatos.setId(id);
                            objDatos.setNombre(name);
                            objDatos.setPrecio(precio);
                            objDatos.setImage(imagenes);

                            lista.add(objDatos);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    adapter = new AdapterLista(EnsaladasDrawer.this, lista, R.layout.item_lista_comida);
                    lstEnsalada.setAdapter(adapter);

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

            case R.id.carrito:

                    /*-metodo carrito-*/
                carritoSession();
                return true;

               /*Intent intentCarrito = new Intent(MainActivity.this, Carrito.class);
                startActivity(intentCarrito);*/

            case R.id.buscar:
                //Abrimos el buscador
                Intent intent = new Intent(EnsaladasDrawer.this, Search.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_pasta:

                                /*- Pasta -*/


                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intent = new Intent(EnsaladasDrawer.this, PastasDrawer.class);
                                finish();
                                startActivity(intent);

                                return true;

                            case R.id.item_navigation_drawer_minutas:

                                /*- Minutas -*/
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intentMinutas = new Intent(EnsaladasDrawer.this, MinutasDrawer.class);
                                finish();
                                startActivity(intentMinutas);

                                return true;

                            case R.id.item_navigation_drawer_ensaladas:

                                 /*- Ensaladas -*/

                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intentEnsaladas = new Intent(EnsaladasDrawer.this, Carnes_Parrilla.class);
                                finish();
                                startActivity(intentEnsaladas);

                                return true;

                            case R.id.item_navigation_drawer_carneParrilla:

                                 /*- Parrilla -*/

                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intentCarne = new Intent(EnsaladasDrawer.this, Carnes_Parrilla.class);
                                finish();
                                startActivity(intentCarne);

                                return true;

                            case R.id.item_navigation_drawer_mariscos:

                                /*- Mariscos -*/

                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intentMariscos = new Intent(EnsaladasDrawer.this, Carnes_Parrilla.class);
                                finish();
                                startActivity(intentMariscos);

                                return true;

                            case R.id.item_navigation_drawer_registrarse:

                                /*- Registrarse -*/

                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intentRegistro = new Intent(EnsaladasDrawer.this, Registro.class);
                                startActivity(intentRegistro);

                                return true;

                            case R.id.item_navigation_drawer_sesion:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);

                                /*- iniciar sesión -*/

                                Intent intentSesion = new Intent(EnsaladasDrawer.this, Login.class);
                                startActivity(intentSesion);
                                return true;

                            case R.id.item_navigation_drawer_salir:

                                /*- Logout y quemar el token del ws-*/
                                menuItem.setChecked(true);
                                Logout();

                        }
                        return true;
                    }
                });
    }


    private void Logout() {
        AlertDialog.Builder alertDialogLogout = new AlertDialog.Builder(this);
        alertDialogLogout.setMessage("Desea salir de la aplicacion");
        alertDialogLogout.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = getSharedPreferences("2b507c0622169727e85e19cdc5dcea13", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("loggedIn");
                editor.remove("token");
                editor.commit();
                Intent intent = new Intent(EnsaladasDrawer.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            }
        });

        alertDialogLogout.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogLogout.create();
        alertDialog.show();
    }

    private void carritoSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("2b507c0622169727e85e19cdc5dcea13", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        loggedIn = sharedPreferences.getBoolean("loggedIn", false);
        userId = sharedPreferences.getInt("userId", 0);


        if (loggedIn != true) {
            SharedPreferences preferences = getSharedPreferences("2b507c0622169727e85e19cdc5dcea13", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("loggedIn");
            editor.remove("token");
            editor.remove("iduser");
            editor.remove("nameUser");
            editor.commit();
            Intent intent = new Intent(EnsaladasDrawer.this, Login.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(EnsaladasDrawer.this, Carrito.class);
            startActivity(intent);
        }
    }
}