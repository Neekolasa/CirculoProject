package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "credito")
public class Credito {

    @PrimaryKey
    @NonNull
    private String no_credito;

    @ColumnInfo(name = "no_cobros")
    private String no_cobros;

    @ColumnInfo(name = "no_cliente")
    private String no_cliente;

    @ColumnInfo(name = "tipo_cobro")
    private  String tipo_cobro;

    @ColumnInfo(name = "monto_prestamo")
    private  String monto_credito;

    @ColumnInfo(name = "no_colaborador")
    private String no_colaborador;

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    @ColumnInfo(name = "abono")
    private String abono;

    public String getNo_credito() {
        return no_credito;
    }

    public void setNo_credito(String no_credito) {
        this.no_credito = no_credito;
    }

    public String getNo_cliente() {
        return no_cliente;
    }

    public void setNo_cliente(String no_cliente) {
        this.no_cliente = no_cliente;
    }

    public String getNo_cobros() {
        return no_cobros;
    }

    public void setNo_cobros(String no_cobros) {
        this.no_cobros = no_cobros;
    }

    public String getTipo_cobro() {
        return tipo_cobro;
    }

    public void setTipo_cobro(String tipo_cobro) {
        this.tipo_cobro = tipo_cobro;
    }

    public String getMonto_credito() {
        return monto_credito;
    }

    public void setMonto_credito(String monto_credito) {
        this.monto_credito = monto_credito;
    }

    public String getNo_colaborador() {
        return no_colaborador;
    }

    public void setNo_colaborador(String no_colaborador) {
        this.no_colaborador = no_colaborador;
    }

    /*,
        foreignKeys = @ForeignKey(entity = Clientes.class,
        parentColumns = "no_cliente",
        childColumns = "no_cliente",
                onDelete=CASCADE),
        indices=@Index(value="no_cliente")*/
}
