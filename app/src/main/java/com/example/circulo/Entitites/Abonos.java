package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "abonos")
public class Abonos {

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


    public String getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(String sincronizado) {
        this.sincronizado = sincronizado;
    }

    public String getNo_abono() {
        return no_abono;
    }

    public void setNo_abono(String no_abono) {
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

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    /*
    * ,
        foreignKeys = @ForeignKey(entity = Credito.class,
        parentColumns = "no_credito",
        childColumns = "no_credito",
                onDelete=CASCADE),
        indices=@Index(value="no_credito")*/
}