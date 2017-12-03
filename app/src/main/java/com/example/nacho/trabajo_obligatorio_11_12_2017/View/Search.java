package com.example.nacho.trabajo_obligatorio_11_12_2017.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.trabajo_obligatorio_11_12_2017.Config.URL_Rest;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Model.HttpConnection;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;
import com.github.snowdream.android.widget.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.nacho.trabajo_obligatorio_11_12_2017.R.id.editBuscar;

public class Search extends AppCompatActivity {


    ActionBar actionBar;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private String idUserDeveloper = "1";
    private String buscarComida;

    EditText ingresarConsulta, txtidTitle;
    TextView textView, textSearch;
    TextView txtNombre;
    Button enviarConsulta;

    private String token;
    private boolean loggedIn;
    private int userId;

    ProgressDialog progressDialog;
    private JSONObject json;
    private HttpConnection service;
    private HttpConnection serviceupdate;
    private String url = URL_Rest.urlBuscarProducto;
    private int status = 0;
    private String request;
    private JSONArray jsonArray;
    private SmartImageView smartImageView;

    String idProducto;
    String restaurant;
    private TextView txtPrecio;
    String nameProduct;
    String precioProduct;
    String detalleProduct;
    String imageProduct;
    String busqueda;
    private int itemSearch;
    public String consulta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ingresarConsulta = (EditText)findViewById(editBuscar);
        enviarConsulta = (Button) findViewById(R.id.btnBuscarSearch);

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
        enviarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String itemSelect= ((TextView)view.findViewById(R.id.txtidTitle)).getText().toString();
                Intent intent= new Intent(Search.this , ResultadoSearch.class);
                intent.putExtra("id", itemSearch);
                intent.putExtra("busqueda", consulta);
                startActivity(intent);
            }
        });


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

                                    Toast.makeText(Search.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    drawerLayout.closeDrawer(GravityCompat.START);
                                    return true;

                                case R.id.item_navigation_drawer_registrarse:

                                /*- Registrarse -*/

                                    menuItem.setChecked(true);

                                    Intent intent = new Intent(Search.this, Registro.class);
                                    startActivity(intent);
                                    drawerLayout.closeDrawer(GravityCompat.START);

                                    return true;

                                case R.id.item_navigation_drawer_sesion:
                                    menuItem.setChecked(true);
                                    drawerLayout.closeDrawer(GravityCompat.START);

                                /*- iniciar sesión -*/

                                    Intent intentSesion = new Intent(Search.this, Login.class);
                                    finish();
                                    startActivity(intentSesion);
                                    return true;

                                case R.id.item_navigation_drawer_salir:

                                /*- Logout y quemar el token del ws-*/
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
                    Intent intent = new Intent(Search.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
    }





