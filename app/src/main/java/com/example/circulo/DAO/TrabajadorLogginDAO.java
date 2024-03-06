package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;

import java.util.List;

@Dao
public interface TrabajadorLogginDAO {

    @Query("SELECT username FROM trabajadorLoggin")
    List<TrabajadorLoggin> getTrabajadoreLoggin();

    @Insert
    public void addTrabajadorLoggin(TrabajadorLoggin trabajadoreloggin);

    @Query("DELETE FROM trabajadorLoggin")
    public void borrarTrabajadorLoggin();

}
