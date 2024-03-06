package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.circulo.Entitites.Abonos;

import java.util.List;

@Dao
public interface AbonosDAO {

    @Insert
    public void addAbonos(Abonos abonos);

    @Update
    public void updateAbonos(Abonos abonos);

    @Query("DELETE FROM abonos")
    public void borrarAbonos();

    @Query("SELECT * FROM abonos JOIN credito ON abonos.no_credito=credito.no_credito WHERE credito.no_colaborador=:noColaborador")
    List<Abonos> getAbonosHoinkInfo(String noColaborador);
    /*@Query("SELECT abonos.no_abono,abonos.saldo_anterior,abonos.fecha_prox_pago, credito.abono FROM abonos JOIN credito ON abonos.no_credito=credito.no_credito WHERE abonos.fecha_prox_pago=date('now') OR abonos.estado=0 AND credito.no_colaborador=:noColaborador ORDER BY LOWER(fecha_prox_pago) ASC")
    List<Abonos> getAbonosHoyInfo(String noColaborador);*/
    /*@Query("SELECT abonos.no_abono,abonos.saldo_anterior,abonos.fecha_prox_pago, credito.abono FROM abonos JOIN credito ON abonos.no_credito=credito.no_credito WHERE abonos.fecha_prox_pago=date('now') OR abonos.estado=0 ORDER BY LOWER(fecha_prox_pago) ASC")
    List<Abonos> getAbonosHoyInfo();*/
    @Query("SELECT abonos.no_abono,abonos.saldo_anterior,abonos.fecha_prox_pago, credito.abono FROM abonos JOIN credito ON abonos.no_credito=credito.no_credito WHERE abonos.fecha_prox_pago=date('now') OR abonos.estado=0")
    List<Abonos> getAbonosHoyInfo();

    @Query("SELECT no_abono,no_credito FROM abonos WHERE no_credito=:id_abono")
    List<Abonos> getCantidadAbonos(int id_abono);

    @Query("UPDATE abonos SET estado=:estado,fecha_prox_pago=:pagoAnterior WHERE no_abono=:id_abono")
    public void cambiarEstadoAbono(String estado, String id_abono, String pagoAnterior);

    @Query("SELECT * FROM abonos JOIN historial ON abonos.no_abono=historial.no_abono")
    List<Abonos> getAbonosCoincidentes();

    @Query("DELETE FROM abonos WHERE no_abono=:noAbono")
    public void borrarAbonoByID(int noAbono);


}
