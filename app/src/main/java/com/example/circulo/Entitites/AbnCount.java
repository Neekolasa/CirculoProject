package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "abncount")
public class AbnCount {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "no_abono")
    private String no_abono;

    @ColumnInfo(name = "no_credito")
    private String no_credito;

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
}
