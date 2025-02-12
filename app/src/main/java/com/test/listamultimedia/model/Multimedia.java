package com.test.listamultimedia.model;

import java.io.Serializable;

public class Multimedia implements Serializable {

    private String nombre, descipcion, direccion;
    private int img, tipo;

    public static final int VIDEO = 1;
    public static final int AUDIO = 2;
    public static final int WEB = 3;

    public Multimedia(String nombre, String descipcion, String direccion, int img, int tipo) {
        this.nombre = nombre;
        this.descipcion = descipcion;
        this.direccion = direccion;
        this.img = img;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
