package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "abono_moratorio")
public class Abono_moratorio {

    @PrimaryKey
    @ColumnInfo(name = "no_abono")
    @NonNull
    private String no_abono;

    @ColumnInfo(name = "pago_interes")
    private String pago_interes;

    @ColumnInfo(name = "no_cobrador")
    private String no_cobrador;

    public String getNo_cobrador() {
        return no_cobrador;
    }

    public void setNo_cobrador(String no_cobrador) {
        this.no_cobrador = no_cobrador;
    }

    public String getNo_abono() {
        return no_abono;
    }

    public void setNo_abono(String no_abono) {
        this.no_abono = no_abono;
    }

    public String getPago_interes() {
        return pago_interes;
    }

    public void setPago_interes(String pago_interes) {
        this.pago_interes = pago_interes;
    }


    /*
    * ,
        foreignKeys = @ForeignKey(entity = Abonos.class,
                parentColumns = "no_abono",
                childColumns = "no_abono",
                onDelete = CASCADE)*/
}

