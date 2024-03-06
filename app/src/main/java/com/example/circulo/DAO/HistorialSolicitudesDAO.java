package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.HistorialSolicitudes;
import com.example.circulo.Entitites.Solicitud;

import java.util.List;

@Dao
public interface HistorialSolicitudesDAO {

    @Insert
    public void addHistorial(HistorialSolicitudes historialSolicitudes);

    @Query("DELETE FROM historialsolicitudes")
    public void borrarSolicitud();

    /*@Query("SELECT *  FROM historialsolicitudes WHERE fechainsercion=:fechaHoy AND sincronizado='no' ORDER BY LOWER(hora) DESC")
    List<HistorialSolicitudes> getHistorialSolicitudesHoy(String fechaHoy);*/

    @Query("SELECT *  FROM historialsolicitudes WHERE fechainsercion=:fechaHoy AND no_cobrador=:noCobrador ORDER BY LOWER(hora) DESC")
    List<HistorialSolicitudes> getHistorialSolicitudesHoy(String fechaHoy, String noCobrador);

    @Query("SELECT no_solicitud,sincronizado FROM historialsolicitudes WHERE no_solicitud=:noSolicitud")
    List<HistorialSolicitudes> getSincronizacion(String noSolicitud);

    @Query("SELECT * FROM historialsolicitudes WHERE sincronizado='no' AND no_cobrador=:noCobrador")
    List<HistorialSolicitudes> getHistorialSinSincronizar(String noCobrador);

    @Query("SELECT no_solicitud FROM historialsolicitudes WHERE fechainsercion=:fechaHoy AND no_cobrador=:noCobrador")
    List<HistorialSolicitudes> getHistorialNum(String fechaHoy, String noCobrador);

    @Query("SELECT * FROM historialsolicitudes WHERE no_cobrador=:noCobrador ORDER BY LOWER(fechainsercion) DESC, LOWER(hora) DESC")
    List<HistorialSolicitudes> getAllHistorial(String noCobrador);

    @Query("SELECT * FROM historialsolicitudes WHERE no_solicitud=:noSolicitud")
    List<HistorialSolicitudes> getSolicitud(String noSolicitud);

    @Query("DELETE FROM historialsolicitudes WHERE no_solicitud=:noSolicitud")
    public void borrarSolicitudByID(String noSolicitud);

    @Query("UPDATE historialsolicitudes SET sincronizado='si' WHERE no_solicitud=:noSolicitud")
    void updateSincronizar(String noSolicitud);

    @Query("UPDATE historialsolicitudes SET nombre=:nombre,ape1=:ape_paterno,ape2=:ape_materno,sexo=:sexo,calle=:calle,num_ext=:num_ext,colonia=:colonia,cp=:cp,telefono=:telefono,celular=:celular,monto_prestamo=:monto_prestamo,ingreso_mensual=:ingreso_mensual,no_cliente_recomendado=:cliente_recomendado, email=:email,localidad=:localidad,municipio=:municipio, num_int=:numInt, comentarios=:comentarios WHERE no_solicitud=:noSolicitud")
    public void updateHistorial(String nombre, String ape_paterno, String ape_materno, String sexo, String calle, String num_ext, String colonia, String cp, String telefono, String celular, String monto_prestamo, String ingreso_mensual, String cliente_recomendado, String noSolicitud, String email, String localidad, String municipio, String numInt, String comentarios);


}
