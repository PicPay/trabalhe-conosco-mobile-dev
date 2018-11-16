package project.picpay.test._db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import project.picpay.test.creditcard.model.CreditCardModel;

import static android.arch.persistence.room.OnConflictStrategy.ROLLBACK;

/**
 * Created by Rodrigo Oliveira on 15/11/2018 for app.
 * Contact us rodrigooliveira.tecinfo@gmail.com
 */
@Dao
public interface CreditCardDao {

    @Query("select * from credit_card")
    LiveData<List<CreditCardModel>> getAllCreditCards();

    @Insert(onConflict = ROLLBACK)
    public void newCreditCard(CreditCardModel creditCardModel);

}