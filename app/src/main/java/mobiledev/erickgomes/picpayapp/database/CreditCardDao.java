package mobiledev.erickgomes.picpayapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import mobiledev.erickgomes.picpayapp.models.CreditCard;

/**
 * Created by erickgomes on 24/03/2018.
 */

@Dao
public interface CreditCardDao {

    @Query("SELECT * FROM creditcards")
    List<CreditCard> getAllCreditCards();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCreditCard(CreditCard creditCard);
}
