package com.example.nacho.trabajo_obligatorio_11_12_2017.Controller;

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

public class Login extends AppCompatActivity implements Validator.ValidationListener{

    Validator validator;

    @NotEmpty (message="Campo Requerido")
    private EditText nombre, apellido, email, contraseña;

    private Button btnLog;
    private ScrollView mScrollView;
    private LinearLayout ltsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Validator validar = new Validator(this);
        validar.setValidationListener(this);

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        ltsContainer=(LinearLayout)findViewById(R.id.ltsContainer);
        nombre = (EditText)findViewById(R.id.edtNombre);
        apellido = (EditText)findViewById(R.id.edtApellido);
        email = (EditText)findViewById(R.id.edtEmail);
        contraseña = (EditText)findViewById(R.id.edtPass);
        btnLog = (Button)findViewById(R.id.btnIngresar);


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* SE ha logueado con éxito*/

                /* Traer los datos del webservice*/
            }
        });

    };


    /*----------- Validaciones -----------*/
    @Override
    public void onValidationSucceeded() {
        Log.d("Succeed","Los datos fueron ingresados correctamente");

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

    /*-------- Scroll --------*/

    private void scrollToButton() {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.smoothScrollTo(0, ltsContainer.getBottom());
            }
        });
    }
}
