package com.example.cleancity.api;

public class Parametro {
    private String nombre;
    private String valor;

    public Parametro(String nombre, Object valor) {
        this.nombre = nombre;
        this.valor = valor.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }
}
