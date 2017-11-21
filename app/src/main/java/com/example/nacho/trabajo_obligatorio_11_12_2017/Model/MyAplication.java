package com.example.nacho.trabajo_obligatorio_11_12_2017.Model;

import android.app.Application;
import android.content.Context;

/**
 * Created by josefrola on 1/11/17.
 */

public class MyAplication extends Application {

    private static MyAplication miInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        miInstance = this;
        this.setAppContext(getApplicationContext());
    }

    public static MyAplication getInstance() {
        return miInstance;
    }
    public static Context getmAppContext() {
        return mAppContext;
    }
    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
}
