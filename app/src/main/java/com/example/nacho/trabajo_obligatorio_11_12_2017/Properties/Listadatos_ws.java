package com.example.nacho.trabajo_obligatorio_11_12_2017.Properties;

/**
 * Created by Nacho on 01/11/2017.
 */

public class Listadatos_ws {

    private int id;
    private String nombre;
    private  String precio;
    private String image;



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
