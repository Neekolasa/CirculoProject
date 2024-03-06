package com.example.circulo.Entitites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trabajadorLoggin")
public class TrabajadorLoggin {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String username;


    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }
}
