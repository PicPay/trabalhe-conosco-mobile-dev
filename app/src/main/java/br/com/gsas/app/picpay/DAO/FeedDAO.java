package br.com.gsas.app.picpay.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.gsas.app.picpay.Domain.Feed;

@Dao
public interface FeedDAO {

    @Query("SELECT * FROM feed ORDER BY id DESC")
    List<Feed> getAll();

    @Query("SELECT * FROM feed WHERE id = :id")
    Feed findById(int id);

    @Insert
    void insert(Feed feed);

    @Update
    void update(Feed feed);

    @Delete
    void delete(Feed feed);

}
