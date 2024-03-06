package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.circulo.Entitites.Abono_moratorio;

import java.util.List;

@Dao
public interface Abono_moratorioDAO {

    @Insert
    public void addAbono_moratorio(Abono_moratorio abono_moratorio);

    @Query("DELETE FROM abono_moratorio")
    public void borrarAbonoMoratorio();

    @Query("SELECT * FROM abono_moratorio WHERE no_abono=:abono AND no_cobrador=:noCobrador")
    List<Abono_moratorio> getAbonoMoratorio(String abono, String noCobrador);

    @Query("UPDATE abono_moratorio SET pago_interes=:interes WHERE no_abono=:noAbono")
    public void AbonoMoratorioUpdate(String interes, String noAbono);

    @Query("SELECT * FROM abono_moratorio WHERE no_cobrador=:noCobrador")
    List<Abono_moratorio> getAbonosMoratorios(String noCobrador);

}
