package com.example.nacho.trabajo_obligatorio_11_12_2017.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nacho.trabajo_obligatorio_11_12_2017.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class Registro extends AppCompatActivity implements Validator.ValidationListener {

    Validator validator;

    @NotEmpty(message = "Campo Requerido")
    EditText nombre, apellido, email, contraseña;
    TextView yaEstaLog;

    Button btnLog;
    LinearLayout ltsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Validator validar = new Validator(this);
        validar.setValidationListener(this);

        ltsContainer = (LinearLayout) findViewById(R.id.ltsContainer);
        nombre = (EditText) findViewById(R.id.edtNombre);
        apellido = (EditText) findViewById(R.id.edtApellido);
        email = (EditText) findViewById(R.id.edtEmail);
        contraseña = (EditText) findViewById(R.id.edtPass);
        btnLog = (Button) findViewById(R.id.btnIngresar);
        yaEstaLog = (TextView)findViewById(R.id.iniSesionReg);


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* SE ha logueado con éxito*/

                /* Traer los datos del webservice*/
            }
        });

        yaEstaLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Registro.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

    }




    /*----------- Validaciones -----------*/
    @Override
    public void onValidationSucceeded() {
        Log.d("Succeed", "Los datos fueron ingresados correctamente");

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}