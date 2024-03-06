package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "historial")
public class Historial {
    @PrimaryKey
    @NonNull
    private String no_abono;

    @ColumnInfo(name = "no_credito")
    private String no_credito;

    @ColumnInfo(name = "no_cobrador")
    private String no_cobrador;

    @ColumnInfo(name = "fecha_prox_pago")
    private String fecha_prox_pago;

    @ColumnInfo(name = "fecha_realizo_pago")
    private String fecha_realizo_pago;

    @ColumnInfo(name= "fecha_anterior_pago")
    private String fecha_anterior_pago;

    @ColumnInfo(name = "hora")
    private String hora;

    @ColumnInfo(name = "saldo_anterior")
    private String saldo_anterior;

    @ColumnInfo(name = "saldo_actual")
    private String saldo_actual;

    @ColumnInfo(name = "abono_recibido")
    private String abono_recibido;

    @ColumnInfo(name = "tipo_pago")
    private String tipo_pago;

    @ColumnInfo(name = "estatus")
    private String estatus;

    @ColumnInfo(name = "estado")
    private String estado;

    @ColumnInfo(name = "sincronizado")
    private String sincronizado;

    @ColumnInfo(name = "no_cliente")
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

    @ColumnInfo(name= "contador_abono")
    private String contador_abono;


    @NonNull
    public String getNo_abono() {
        return no_abono;
    }

    public void setNo_abono(@NonNull String no_abono) {
        this.no_abono = no_abono;
    }

    public String getNo_credito() {
        return no_credito;
    }

    public void setNo_credito(String no_credito) {
        this.no_credito = no_credito;
    }

    public String getNo_cobrador() {
        return no_cobrador;
    }

    public void setNo_cobrador(String no_cobrador) {
        this.no_cobrador = no_cobrador;
    }

    public String getFecha_prox_pago() {
        return fecha_prox_pago;
    }

    public void setFecha_prox_pago(String fecha_prox_pago) {
        this.fecha_prox_pago = fecha_prox_pago;
    }

    public String getFecha_realizo_pago() {
        return fecha_realizo_pago;
    }

    public void setFecha_realizo_pago(String fecha_realizo_pago) {
        this.fecha_realizo_pago = fecha_realizo_pago;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(String saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public String getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(String saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    public String getAbono_recibido() {
        return abono_recibido;
    }

    public void setAbono_recibido(String abono_recibido) {
        this.abono_recibido = abono_recibido;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String status) {
        this.estatus = status;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(String sincronizado) {
        this.sincronizado = sincronizado;
    }

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

    public String getFecha_anterior_pago() {
        return fecha_anterior_pago;
    }

    public void setFecha_anterior_pago(String fecha_anterior_pago) {
        this.fecha_anterior_pago = fecha_anterior_pago;
    }

    public String getContador_abono() {
        return contador_abono;
    }

    public void setContador_abono(String contador_abono) {
        this.contador_abono = contador_abono;
    }
}
