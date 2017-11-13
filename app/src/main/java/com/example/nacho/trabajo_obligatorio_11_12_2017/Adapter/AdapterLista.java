package com.example.nacho.trabajo_obligatorio_11_12_2017.Adapter;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nacho.trabajo_obligatorio_11_12_2017.Properties.Listadatos_ws;
import com.example.nacho.trabajo_obligatorio_11_12_2017.R;

import java.util.List;

/**
 * Created by Nacho on 01/11/2017.
 */

public class AdapterLista extends BaseAdapter {

    private int layoutData;
    private List<Listadatos_ws> lista;
    private Activity activity;

    private String getId;


    public AdapterLista(Activity activity, List<Listadatos_ws> list, int layout){

        this.activity=activity;
        this.lista=list;
        this.layoutData=layout;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView=inflater.inflate(layoutData, null);

        TextView lblid = (TextView)convertView.findViewById(R.id.txtidTitle);
        TextView lblNombre = (TextView)convertView.findViewById(R.id.lblNombre);
        TextView lblprecio = (TextView)convertView.findViewById(R.id.lblPrecio);

        getId=Integer.toString(lista.get(position).getId());
        lblid.setText(getId);
        lblNombre.setText(lista.get(position).getNombre());
        lblprecio.setText(lista.get(position).getPrecio());
        return convertView;
    }
}
