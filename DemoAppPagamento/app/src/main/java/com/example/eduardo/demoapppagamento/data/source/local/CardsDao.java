package com.example.eduardo.demoapppagamento.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.eduardo.demoapppagamento.data.Card;

import java.util.List;

/**
 * Data Access Object for the Cards table.
 */
@Dao
public interface CardsDao {

    @Query("SELECT * FROM Cards")
    List<Card> loadAllCards();

    @Insert
    void insertCard(Card card);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCard(Card card);

    @Delete
    void deleteCard(Card card);

}
