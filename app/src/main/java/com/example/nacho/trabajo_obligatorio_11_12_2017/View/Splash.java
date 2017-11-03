package com.example.nacho.trabajo_obligatorio_11_12_2017.View;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.nacho.trabajo_obligatorio_11_12_2017.Controller.Login;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Thread timeThread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally{
                    Intent intent =new Intent(Splash.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        };
        timeThread.start();

    }
}
