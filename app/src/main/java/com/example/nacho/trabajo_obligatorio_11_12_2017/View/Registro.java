package com.example.nacho.trabajo_obligatorio_11_12_2017.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Config.URL_Rest;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Model.HttpConnection;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class  Registro extends AppCompatActivity {


    private ProgressDialog progresDialog;
    private JSONObject json;
    private HttpConnection service;
    private String url = URL_Rest.urlRegistrarUsuario;
    private int status = 0;
    private String request;
    RequestQueue mRequestQueque;
    ProgressDialog progressDialog;


    EditText txtNombre, txtApellido,txtEmail, txtContraseña,txtRepeContraseña;
    String nombre, email,apellido,  password, repePass;
    TextView yaEstaLog;

    Button btnRegistrar;
    LinearLayout ltsContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        ltsContainer = (LinearLayout) findViewById(R.id.ltsContainer);
        txtNombre = (EditText) findViewById(R.id.edtNombre);
        txtApellido = (EditText) findViewById(R.id.edtApellido);
        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtContraseña = (EditText) findViewById(R.id.edtPass);
        txtRepeContraseña = (EditText) findViewById(R.id.edtRepPass);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        yaEstaLog = (TextView)findViewById(R.id.iniSesionReg);



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txtNombre.getText().toString();
                apellido = txtApellido.getText().toString();
                email = txtEmail.getText().toString();
                password = txtContraseña.getText().toString();
                repePass = txtRepeContraseña.getText().toString();

                if(nombre.equals("") | apellido.equals("") | email.equals("") | password.equals("") | repePass.equals("")){
                    Log.d("Error", "Validacion de datos");
                }else{
                    service = new HttpConnection();
                    new DataPostRegistro().execute();
                }

            }
        });

    }

    public class DataPostRegistro extends AsyncTask<Void, Void, Void> {

        String response = "";
        HashMap<String, String> postDataParams;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresDialog = new ProgressDialog(Registro.this);
            progresDialog.setTitle("Procesando....");
            progresDialog.setCancelable(false);
            progresDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            postDataParams = new HashMap<String, String>();
            postDataParams.put("name", nombre);
            postDataParams.put("lastname", apellido);
            postDataParams.put("email", email);
            postDataParams.put("password", password);
            postDataParams.put("repeatpassword", repePass);
            response = service.ServerDataHeader(url, postDataParams);

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

            if (progresDialog.isShowing()) {
                progresDialog.dismiss();
                String resultdata = request;

                response = "";
                if (status == 200) {
                    Toast.makeText(getApplicationContext(), resultdata, Toast.LENGTH_LONG).show();
                    ClearEditText();
                } else if (status == 900) {
                    Toast.makeText(getApplicationContext(), resultdata, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), resultdata, Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void ClearEditText(){
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtContraseña.setText("");
        txtRepeContraseña.setText("");

    }



    private void ProgresDialog() {
        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Procesando..");
        progressDialog.setMessage("Un momento..");
        progressDialog.show();
    }

    public void onPostExecuteLogin() {
        progressDialog.dismiss();
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Registro.this);
        dialogBuilder.setMessage("Las credenciales son incorrectas ");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void showMessage(final String message) {
        AlertDialog.Builder dialogBuilderLogin = new AlertDialog.Builder(Registro.this);
        dialogBuilderLogin.setMessage(message);
        dialogBuilderLogin.setPositiveButton("OK", null);
        dialogBuilderLogin.show();
    }


}