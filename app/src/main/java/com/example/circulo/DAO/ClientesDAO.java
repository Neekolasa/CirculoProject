package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.Clientes;

import java.util.List;

@Dao
public interface ClientesDAO {

    @Query("SELECT clientes.no_cliente,clientes.nombre,clientes.ape1,clientes.ape2,clientes.calle," +
            "clientes.num_ext,clientes.num_int,clientes.cp,clientes.colonia,clientes.telefono FROM" +
            " clientes JOIN credito ON credito.no_credito=clientes.no_cliente WHERE no_credito=:creditoID")
    List<Clientes> getInfoClienteByCredito(int creditoID);

    @Query("SELECT clientes.no_cliente,clientes.nombre,clientes.ape1,clientes.ape2,clientes.calle," +
            "clientes.num_ext,clientes.num_int,clientes.cp,clientes.colonia,clientes.telefono FROM" +
            " clientes WHERE no_cliente=:clienteID")
    List<Clientes> getInfoClienteByID(int clienteID);

    @Query("SELECT * FROM clientes")
    List<Clientes> getInfoClientes();

    @Query("SELECT clientes.no_cliente,clientes.nombre,clientes.ape1,clientes.ape2,clientes.calle,clientes.num_ext,clientes.num_int,clientes.colonia,clientes.cp,clientes.telefono,credito.no_credito " +
            "FROM clientes JOIN credito ON clientes.no_cliente=credito.no_cliente JOIN abonos ON " +
            "credito.no_credito=abonos.no_credito WHERE abonos.fecha_prox_pago=date('now') OR abonos.estado=0")
    List<Clientes> getInfoCreditoClientes();

    @Insert
    public void addClientes(Clientes clientes);

    @Delete
    public void deleteClientes(Clientes clientes);

    @Query("DELETE FROM clientes")
    public void borrarClientes();

    @Query("SELECT clientes.no_cliente,clientes.nombre,clientes.ape1,clientes.ape2,clientes.calle,clientes.num_ext,clientes.num_int,clientes.cp,clientes.colonia,clientes.telefono FROM clientes LEFT JOIN credito ON credito.no_credito=clientes.no_cliente LEFT JOIN abonos ON abonos.no_credito=credito.no_credito WHERE clientes.no_cliente=:noo_cliente")
    List<Clientes> getClienteInfoByID(String noo_cliente);
}
