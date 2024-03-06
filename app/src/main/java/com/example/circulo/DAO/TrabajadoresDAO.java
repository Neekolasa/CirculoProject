package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.Trabajadores;

import java.util.List;

@Dao
public interface TrabajadoresDAO {

    /*@Query("SELECT no_trabajador as id_trabajador, nombre FROM trabajadores JOIN trabajadores_sistema ON trabajadores.no_trabajador=trabajadores_sistema.no_trabajador WHERE trabajadores_sistema.usuario_nombre=:trabajadorUser")
    List<Trabajadores> getTrabajadorNombreByUsername(String trabajadorUser);*/

    @Insert
    public void addTrabajadores(Trabajadores trabajadores);

    @Query("DELETE FROM trabajadores")
    public void borrarTrabajadores();

}
