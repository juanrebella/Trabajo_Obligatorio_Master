package com.example.nacho.trabajo_obligatorio_11_12_2017.Model;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by josefrola on 1/11/17.
 */

public class VolleySingleton {

    private static VolleySingleton instance = null;
    private RequestQueue mRequestQueque;

    private VolleySingleton() {
        mRequestQueque = Volley.newRequestQueue(MyAplication.getmAppContext());
    }

    public static VolleySingleton getInstance() {
        if (instance == null) {
            instance = new VolleySingleton();
        }
        return instance;
    }

    public RequestQueue getmRequestQueque(){
        return mRequestQueque;
    }
}
