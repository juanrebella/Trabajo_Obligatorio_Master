package com.example.nacho.trabajo_obligatorio_11_12_2017.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Config.URL_Rest;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Model.HttpConnection;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;
import com.github.snowdream.android.widget.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Mostrar_Detalle_Imagen extends AppCompatActivity {


    Button lupaBuscar, carritoCompra, loginButton;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    TextView textView;
    Toolbar toolbar;


    private String token;
    private boolean loggedIn;
    private int userId;

    ProgressDialog progressDialog;
    private JSONObject json;
    private HttpConnection service;
    private HttpConnection serviceupdate;
    private String url = URL_Rest.urlDetalle;
    //private String urledit = URL_Rest.urlupdate;
    private int status = 0;
    private String request;
    private JSONArray jsonArray;

    private EditText txtCantProd;
    private TextView txtTituloDescrip;
    private TextView txtDescripcion;
    private TextView txtPrecio;
    private Button buttonCompra;
    private LinearLayout lstDetalle;
    private SmartImageView smartImageView;

    String idProducto;
    String restaurant;
    String nameProduct;
    String precioProduct;
    String detalleProduct;
    String imageProduct;


    private String idUserDeveloper;
    public String itemSelect ;

    RequestQueue mRequestQueque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__detalle__imagen);

        lstDetalle = (LinearLayout) findViewById(R.id.lstDetalle);
        txtCantProd = (EditText) findViewById(R.id.txtCantProd);
        txtTituloDescrip = (TextView) findViewById(R.id.txtTituloDescrip);
        txtPrecio = (TextView) findViewById(R.id.txtPrecio);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        buttonCompra = (Button) findViewById(R.id.buttonCompra);
        smartImageView = (SmartImageView) findViewById(R.id.smartImageView);


        Bundle params = getIntent().getExtras();
        if (params != null) {
            itemSelect = params.getString("id");
        } else {
            Toast.makeText(getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_LONG).show();
        }

        service = new HttpConnection();
        new postMostrar().execute();

/*
        buttonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                precioProduct = txtPrecio.getText().toString();
                detalleProduct = txtDescripcion.getText().toString();

                serviceupdate = new HttpConnection();
                new postComprar().execute();
            }
        }); */
    }

        public class postMostrar extends AsyncTask<Void, Void, JSONArray> {

            String response = "";
            HashMap<String, String> postDataParams;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(Mostrar_Detalle_Imagen.this);
                progressDialog.setMessage("Procesando..");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            protected JSONArray doInBackground(Void... params) {
                postDataParams = new HashMap<String, String>();
                postDataParams.put("id", itemSelect);


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

                                int id;
                                String precio;
                                String nombre;
                                String imagenes;
                                String idResturante;


                                JSONObject objet = jsonArray.getJSONObject(i);

                                id = objet.getInt("id");
                                nombre = objet.getString("name");
                                precio = objet.getString("precio");
                                imagenes = objet.getString("nameImage");



                                idProducto = Integer.toString(id);
                                precioProduct = precio;
                                detalleProduct = nombre;
                                imageProduct = imagenes;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        String urlfinal = URL_Rest.urlImage + imageProduct;
                        Rect rect = new Rect(smartImageView.getLeft(), smartImageView.getTop(), smartImageView.getRight(), smartImageView.getBottom());
                        smartImageView.setImageUrl(urlfinal, rect);
                        txtPrecio.setText(precioProduct);
                        txtDescripcion.setText(detalleProduct);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error al cargar los datos", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }

    /*    public class postComprar extends AsyncTask<Void, Void, Void> {

            String response = "";
            HashMap<String, String> postDataParams;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(Mostrar_Detalle_Imagen.this);
                progressDialog.setMessage("Procesando..");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            protected Void doInBackground(Void... params) {
                postDataParams = new HashMap<String, String>();



                postDataParams.put("token", token);
                postDataParams.put("id", itemSelect);
                postDataParams.put("name", nameProduct);
                postDataParams.put("description", detalleProduct);
                postDataParams.put("precio", precioProduct);
                postDataParams.put("userId", idUserDeveloper);
                response = serviceupdate.ServerDataHeader(urledit, postDataParams);

                try {
                    json = new JSONObject(response);
                    status = json.getInt("status");
                    request = json.getString("response");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    String resultdata = request;

                    response = "";
                    if (status == 200) {
                        Toast.makeText(getApplicationContext(), resultdata, Toast.LENGTH_LONG).show();

                    } else if (status == 900) {
                        Toast.makeText(getApplicationContext(), "Error de validacion token", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error de token", Toast.LENGTH_LONG).show();
                    }

                }
            }

        }*/
    }

