package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards

@Dao
interface UserCardsDao {

    @Insert(onConflict = REPLACE) fun insert(userCard: UserCards)

    @Query("SELECT * FROM user_cards") fun getCards() : LiveData<List<UserCards>>

    @Query("DELETE FROM user_cards") fun deleteAll()

}