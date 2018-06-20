package br.com.gsas.app.picpay.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.gsas.app.picpay.Domain.Cartao;

@Dao
public interface CartaoDAO{

        @Query("SELECT * FROM cartao")
        List<Cartao> getAll();

        @Query("SELECT * FROM cartao where ativo = 1")
        Cartao findByAtivo();

        @Query("SELECT * FROM cartao where id = :id")
        Cartao findById(int id);

        @Insert
        void insert(Cartao cartao);

        @Update
        void update(Cartao cartao);

        @Delete
        void delete(Cartao cartao);

}
