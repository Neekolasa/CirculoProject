package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class Clientes {

    @PrimaryKey
    @NonNull
    private String no_cliente;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "ape1")
    private String ape1;

    @ColumnInfo(name = "ape2")
    private String ape2;

    @ColumnInfo(name = "calle")
    private String calle;

    @ColumnInfo(name = "num_ext")
    private String num_ext;

    @ColumnInfo(name = "num_int")
    private String num_int;

    @ColumnInfo(name = "cp")
    private String cp;

    @ColumnInfo(name = "colonia")
    private String colonia;

    @ColumnInfo(name = "telefono")
    private String telefono;

    public String getNo_cliente() {
        return no_cliente;
    }

    public void setNo_cliente(String no_cliente) {
        this.no_cliente = no_cliente;
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

    public String getNum_int() {
        return num_int;
    }

    public void setNum_int(String num_int) {
        this.num_int = num_int;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
