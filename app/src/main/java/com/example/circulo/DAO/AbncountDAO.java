package com.example.circulo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.circulo.Entitites.AbnCount;

import java.util.List;

@Dao
public interface AbncountDAO {

    @Insert
    public void addAbncount(AbnCount abnCount);

    @Query("SELECT * FROM abncount WHERE no_credito=:noCredito")
    List<AbnCount> getAbonosByCredito(String noCredito);

    @Query("DELETE FROM abncount")
    public void deleteAbncount();
    //SELECT *,row_number() OVER(Order by no_abono) as num FROM abncount WHERE no_credito=:noCredito
    /*@Query("SELECT row_number() OVER(Order by no_abono) AS num FROM abncount WHERE no_credito=:noCredito")
    List<AbnCount> getNum(String noCredito);*/
}
