package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.circulo.Entitites.Abonos;
import com.example.circulo.Entitites.Historial;

import java.util.List;

@Dao
public interface HistorialDAO {

    @Insert
    public void addHistorial(Historial historial);

    @Update
    public void updateHistorial(Historial historial);

    @Query("DELETE FROM historial")
    public void borrarHistorial();

    @Query("SELECT * FROM historial WHERE no_cobrador=:noCobrador ORDER BY LOWER(fecha_realizo_pago) DESC, LOWER(hora) DESC")
    List<Historial> getAllHistorial(String noCobrador);

    /*@Query("SELECT * FROM historial WHERE fecha_realizo_pago=:fechahoy AND sincronizado='no' ORDER BY LOWER(hora) DESC")
    List<Historial> getHistorialHoy(String fechahoy);*/

    @Query("SELECT * FROM historial WHERE fecha_realizo_pago=:fechahoy AND no_cobrador=:noCobrador ORDER BY LOWER(hora) DESC")
    List<Historial> getHistorialHoy(String fechahoy, String noCobrador);

    @Query("SELECT no_abono,abono_recibido FROM historial WHERE fecha_realizo_pago=:fechahoy AND no_cobrador=:noCobrador")
    List<Historial> getAbonosRecibidos(String fechahoy,String noCobrador);

    @Query("SELECT no_abono,count(no_abono) FROM historial WHERE no_abono=:id_abono")
    List<Historial> getCantidadAbonos(String id_abono);

    @Query("UPDATE historial SET abono_recibido=:abono_recibido, saldo_actual=:saldo_actual, tipo_pago=:tipo_pago, hora=:hora WHERE no_abono=:no_abono")
    public void HistorialUpdate(String abono_recibido,String saldo_actual,String tipo_pago,String no_abono,String hora);

    @Query("DELETE FROM historial WHERE no_abono=:no_abono")
    public void borrarHistorialElemento(String no_abono);

    @Query("SELECT no_abono,no_credito,no_cobrador,fecha_prox_pago,fecha_realizo_pago,hora,saldo_anterior,saldo_actual,abono_recibido,tipo_pago,estatus,estado FROM historial WHERE sincronizado='no' AND no_cobrador=:noCobrador")
    List<Historial> getHistorialNoSincronizado(String noCobrador);

    @Query("UPDATE historial SET sincronizado='si' WHERE no_abono=:no_abono")
    public  void sincronizarHistorial(String no_abono);

    @Query("UPDATE historial SET sincronizado='no' WHERE no_abono=:no_abono")
    public void noSincronizarHistorial(String no_abono);

    @Query("SELECT no_abono,fecha_anterior_pago FROM historial WHERE no_abono=:no_abono")
    List<Historial> getFechaAnterior(String no_abono);

    @Query("SELECT no_abono,count(no_credito) FROM historial WHERE no_credito=:id_abono")
    List<Historial> getCantidadAbonos(int id_abono);

    @Query("SELECT * FROM historial JOIN abonos ON historial.no_abono=abonos.no_abono")
    List<Historial> getCoincidentes();

    @Query("SELECT no_abono,sincronizado FROM historial WHERE no_abono=:noAbono")
    List<Historial> getSincronizacion(String noAbono);

    @Query("SELECT * FROM historial WHERE no_abono=:noAbono")
    List<Historial> getDatos(String noAbono);

    @Query("SELECT no_abono,contador_abono FROM historial WHERE no_abono=:noAbono")
    List<Historial> getCantidad(String noAbono);




    /*
    * $no_abono=$_REQUEST['no_abono'];
		$no_credito=$_REQUEST['no_credito'];
		$no_cobrador=$_REQUEST['no_cobrador'];
		$fecha_prox_pago=$_REQUEST['fecha_prox_pago'];
		$fecha_realizo_pago=$_REQUEST['fecha_realizo_pago'];
		$hora=$_REQUEST['hora'];
		$saldo_anterior=$_REQUEST['saldo_anterior'];
		$saldo_actual=$_REQUEST['saldo_actual'];
		$abono_recibido=$_REQUEST['abono_recibido'];
		$tipo_pago=$_REQUEST['tipo_pago'];
		$estatus=$_REQUEST['estatus'];
		$estado=$_REQUEST['estado'];*/


}
