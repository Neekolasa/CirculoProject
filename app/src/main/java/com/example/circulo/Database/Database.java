package com.example.circulo.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.circulo.DAO.AbncountDAO;
import com.example.circulo.DAO.Abono_moratorioDAO;
import com.example.circulo.DAO.AbonosDAO;
import com.example.circulo.DAO.ClientesDAO;
import com.example.circulo.DAO.CobradorDAO;
import com.example.circulo.DAO.CreditoDAO;
import com.example.circulo.DAO.HistorialDAO;
import com.example.circulo.DAO.HistorialSolicitudesDAO;
import com.example.circulo.DAO.SolicitudDAO;
import com.example.circulo.DAO.TrabajadorLogginDAO;
import com.example.circulo.DAO.TrabajadoresDAO;
import com.example.circulo.DAO.Trabajadores_sistemaDAO;
import com.example.circulo.Entitites.AbnCount;
import com.example.circulo.Entitites.Abono_moratorio;
import com.example.circulo.Entitites.Abonos;
import com.example.circulo.Entitites.Clientes;
import com.example.circulo.Entitites.Cobrador;
import com.example.circulo.Entitites.Credito;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.HistorialSolicitudes;
import com.example.circulo.Entitites.Solicitud;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores;
import com.example.circulo.Entitites.Trabajadores_sistema;

@androidx.room.Database(entities = {Abono_moratorio.class, Clientes.class, Credito.class, Trabajadores.class, Trabajadores_sistema.class, Abonos.class, Solicitud.class, Historial.class, Cobrador.class, TrabajadorLoggin.class, HistorialSolicitudes.class, AbnCount.class},version = 41)
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME="circulo";

    public abstract Abono_moratorioDAO abono_moratorioDAO();
    public abstract ClientesDAO clientesDAO();
    public abstract Trabajadores_sistemaDAO trabajadores_sistemaDAO();
    public abstract TrabajadoresDAO trabajadoresDAO();
    public abstract CreditoDAO creditoDAO();
    public abstract AbonosDAO abonosDAO();
    public abstract SolicitudDAO solicitudDAO();
    public abstract HistorialDAO historialDAO();
    public abstract CobradorDAO cobradorDAO();
    public abstract TrabajadorLogginDAO trabajadorLogginDAO();
    public abstract HistorialSolicitudesDAO historialSolicitudesDAO();
    public abstract AbncountDAO abncountDAO();

    private static Database mInstance;
    public static Database getInstance(Context context){
        if (mInstance==null)
        {
            mInstance= Room.databaseBuilder(context, Database.class, DATABASE_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

}
