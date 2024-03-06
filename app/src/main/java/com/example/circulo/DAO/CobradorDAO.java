package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.Cobrador;
import com.example.circulo.Entitites.Credito;

import java.util.List;

@Dao
public interface CobradorDAO {

   @Insert
    public void addCobrador(Cobrador cobrador);

   @Query("DELETE FROM cobrador")
   public void deleteCobrador();

   @Query("SELECT * FROM cobrador")
   List<Cobrador> getCobrador();


}
