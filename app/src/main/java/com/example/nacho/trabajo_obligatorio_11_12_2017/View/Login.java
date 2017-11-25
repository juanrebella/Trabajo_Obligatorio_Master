package com.example.nacho.trabajo_obligatorio_11_12_2017.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Config.URL_Rest;
import com.example.nacho.trabajo_obligatorio_11_12_2017.Model.VolleySingleton;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity{

    ActionBar actionBar;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView textView;

    private EditText email, contraseña;

    private Button btnLog;
    private ScrollView mScrollView;
    private LinearLayout ltsContainer;

    RequestQueue mRequestQueque;
    ProgressDialog progressDialog;

    private String url = URL_Rest.urlLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        ltsContainer=(LinearLayout)findViewById(R.id.ltsContainer);
        email = (EditText)findViewById(R.id.edtEmail);
        contraseña = (EditText)findViewById(R.id.edtPass);
        btnLog = (Button)findViewById(R.id.btnIngresar);



        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String userEmail = email.getText().toString();
               String password = contraseña.getText().toString();

                if(userEmail.equals("") || password.equals("")){
                    Log.d("Error", "Validacion de datos");
                }else{
                    authenticate(userEmail,password);
                }
            }
        });

    }

    private void authenticate(final String userEmail, final String password) {

        mRequestQueque = VolleySingleton.getInstance().getmRequestQueque();
        ProgressDialog();


        final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onPostExecuteLogin();
                try {
                    JSONObject mainObjet = new JSONObject(response);
                    String token = mainObjet.getString("token");
                    int userId = mainObjet.getInt("iduser");
                    String success = mainObjet.getString("success");
                    String name = mainObjet.getString("name");


                    boolean loggedInShare = true;

                    if (success.equals("success")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("2b507c0622169727e85e19cdc5dcea13", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("loggedIn", loggedInShare);
                        editor.putString("token", token);
                        editor.putInt("iduser", userId);
                        editor.putString("nameUser",name);
                        editor.commit();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        finish();
                        intent.putExtra("token", token);
                        intent.putExtra("userId", userId);
                        intent.putExtra("nameUser", name);
                        startActivity(intent);

                    } else {
                        String messag = "El usuario no se encuentra activado";
                        showMessage(messag);
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onPostExecuteLogin();
                showErrorMessage();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", userEmail);
                map.put("password", password);
                return map;
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(
                        5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                );
            }
        };

        mRequestQueque.add(request);

    }


    public void onPostExecuteLogin() {
        progressDialog.dismiss();
    }

    private void ProgressDialog() {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Procesando..");
        progressDialog.setMessage("Un momento..");
        progressDialog.show();
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Las credenciales son incorrectas ");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void showMessage(final String message) {
        AlertDialog.Builder dialogBuilderLogin = new AlertDialog.Builder(Login.this);
        dialogBuilderLogin.setMessage(message);
        dialogBuilderLogin.setPositiveButton("OK", null);
        dialogBuilderLogin.show();
    }


    /*-------- Scroll --------*/

    private void scrollToButton() {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.smoothScrollTo(0, ltsContainer.getBottom());
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
                Intent intent = new Intent(Login.this, Login.class);
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
