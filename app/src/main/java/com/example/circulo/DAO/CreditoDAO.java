package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.Credito;

import java.util.List;

@Dao
public interface CreditoDAO {

    @Query("SELECT no_credito, no_cliente, abono FROM credito WHERE credito.no_credito=:creditoID")
    List<Credito> getCreditoInfoByID(int creditoID);

    @Query("SELECT no_credito, no_cliente, abono FROM credito WHERE credito.no_cliente=:clienteID")
    List<Credito> getCreditoInfoByID_cliente(int clienteID);

    @Query("SELECT credito.no_credito,credito.abono FROM credito JOIN clientes ON credito.no_cliente=" +
            "clientes.no_cliente JOIN abonos ON credito.no_credito=abonos.no_credito WHERE abonos.fecha_prox_pago=date('now') OR abonos.estado=0")
    List<Credito> getClientesCredito();

    @Insert
    public void addCredito(Credito credito);

    @Query("DELETE FROM credito")
    public void borrarCreditos();

    @Query("SELECT credito.no_credito, no_cobros FROM credito WHERE no_credito=:num_credito")
    List<Credito> getNoCobros(String num_credito);

    @Query("SELECT credito.no_credito, tipo_cobro FROM credito JOIN abonos ON abonos.no_credito=credito.no_credito WHERE abonos.no_abono=:id_abono")
    List<Credito> getTipoCobro(String id_abono);

    @Query("SELECT * FROM credito JOIN historial ON credito.no_credito=historial.no_credito WHERE historial.no_abono=:id_abono")
    List<Credito> getMontoPrestamo(String id_abono);

    @Query("SELECT * FROM credito JOIN abncount ON credito.no_credito=abncount.no_credito WHERE abncount.no_abono=:id_abono")
    List<Credito> getMontoPrestamoFromAbn(String id_abono);

}
