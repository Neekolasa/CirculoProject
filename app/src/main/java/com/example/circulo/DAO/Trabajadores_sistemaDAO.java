package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.Trabajadores_sistema;

import java.util.List;

@Dao
public interface Trabajadores_sistemaDAO {

    @Query("SELECT no_trabajador,usuario_nombre FROM trabajadores_sistema WHERE no_trabajador=:id_trabajador")
    List<Trabajadores_sistema> getTrabajadoresSistemaName(int id_trabajador);

    @Query("SELECT no_trabajador,usuario_nombre FROM trabajadores_sistema WHERE usuario_nombre=:user")
    List<Trabajadores_sistema> getTrabajadorID(String user);

    @Insert
    public void addTrabajadorSistema(Trabajadores_sistema trabajadores_sistema);

    @Query("DELETE FROM trabajadores_sistema")
    public void borrarTrabajadoresSistema();

}
