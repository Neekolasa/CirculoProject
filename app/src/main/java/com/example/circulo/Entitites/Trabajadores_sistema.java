package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "trabajadores_sistema")
public class Trabajadores_sistema {

    @ColumnInfo(name = "no_trabajador")
    private String no_trabajador;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "usuario_nombre")
    private String usuario_nombre;

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getNo_trabajador() {
        return no_trabajador;
    }

    public void setNo_trabajador(String no_trabajador) {
        this.no_trabajador = no_trabajador;
    }


    /*,
        foreignKeys = @ForeignKey(entity = Trabajadores.class,
                parentColumns = "no_trabajador",
                childColumns = "id_trabajador",
                onDelete=CASCADE)*/


}
