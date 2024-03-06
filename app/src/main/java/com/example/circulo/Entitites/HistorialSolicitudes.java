package com.example.circulo.Entitites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "historialsolicitudes")
public class HistorialSolicitudes {
    @PrimaryKey(autoGenerate = true)
    private int no_solicitud;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "ape1")
    private String ape1;

    @ColumnInfo(name = "ape2")
    private String ape2;

    @ColumnInfo(name = "sexo")
    private String sexo;

    @ColumnInfo(name = "calle")
    private String calle;

    @ColumnInfo(name = "num_ext")
    private String num_ext;

    @ColumnInfo(name = "colonia")
    private String colonia;

    @ColumnInfo(name = "cp")
    private String cp;

    @ColumnInfo(name = "telefono")
    private String telefono;

    @ColumnInfo(name = "celular")
    private String celular;

    @ColumnInfo(name = "monto_prestamo")
    private String monto_prestamo;

    @ColumnInfo(name = "ingreso_mensual")
    private String ingreso_mensual;

    @ColumnInfo(name = "no_cliente_recomendado")
    private String no_cliente_recomendado;

    @ColumnInfo(name = "fechainsercion")
    private String fechaInsercion;

    @ColumnInfo(name = "hora")
    private String hora;

    @ColumnInfo(name = "sincronizado")
    private String sincronizado;

    @ColumnInfo(name = "num_int")
    private String num_int;

    @ColumnInfo(name = "localidad")
    private String localidad;

    @ColumnInfo(name = "municipio")
    private String municipio;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "comentarios")
    private String comentarios;

    @ColumnInfo(name = "no_cobrador")
    private String no_cobrador;

    public String getNo_cobrador() {
        return no_cobrador;
    }

    public void setNo_cobrador(String no_cobrador) {
        this.no_cobrador = no_cobrador;
    }

    public int getNo_solicitud() {
        return no_solicitud;
    }

    public void setNo_solicitud(int no_solicitud) {
        this.no_solicitud = no_solicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNum_ext() {
        return num_ext;
    }

    public void setNum_ext(String num_ext) {
        this.num_ext = num_ext;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMonto_prestamo() {
        return monto_prestamo;
    }

    public void setMonto_prestamo(String monto_prestamo) {
        this.monto_prestamo = monto_prestamo;
    }

    public String getIngreso_mensual() {
        return ingreso_mensual;
    }

    public void setIngreso_mensual(String ingreso_mensual) {
        this.ingreso_mensual = ingreso_mensual;
    }

    public String getNo_cliente_recomendado() {
        return no_cliente_recomendado;
    }

    public void setNo_cliente_recomendado(String no_cliente_recomendado) {
        this.no_cliente_recomendado = no_cliente_recomendado;
    }

    public String getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(String fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(String sincronizado) {
        this.sincronizado = sincronizado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNum_int() {
        return num_int;
    }

    public void setNum_int(String num_int) {
        this.num_int = num_int;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
