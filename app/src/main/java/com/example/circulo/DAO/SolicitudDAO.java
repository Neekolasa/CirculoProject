package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.Abono_moratorio;
import com.example.circulo.Entitites.Solicitud;

import java.util.List;

@Dao
public interface SolicitudDAO {

    @Insert
    public void addSolicitud(Solicitud solicitud);

    @Query("DELETE FROM solicitud")
    public void borrarSolicitud();

    @Query("SELECT no_solicitud,count(no_solicitud) FROM solicitud")
    List<Solicitud> getSolicitudesNum();

    @Query("DELETE FROM solicitud WHERE no_solicitud=:noSolicitud")
    void borrarSolicitudByID(String noSolicitud);

}
