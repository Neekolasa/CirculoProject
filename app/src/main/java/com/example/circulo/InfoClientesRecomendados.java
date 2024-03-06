package com.example.circulo;

public class InfoClientesRecomendados {
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String no_cliente;

    public InfoClientesRecomendados(String nombre, String apellido_paterno, String apellido_materno, String no_cliente) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.no_cliente = no_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getNo_cliente() {
        return no_cliente;
    }

    public void setNo_cliente(String no_cliente) {
        this.no_cliente = no_cliente;
    }
}
