package com.example.circulo.Entitites;

import android.text.Editable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "solicitud")
public class Solicitud {

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

    @ColumnInfo(name = "estatus")
    private String estatus;


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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }


}


