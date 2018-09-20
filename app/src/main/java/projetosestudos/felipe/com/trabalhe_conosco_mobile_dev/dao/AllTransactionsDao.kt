package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions

@Dao
interface AllTransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(transaction: AllTransactions)

    @Query("SELECT * FROM all_transactions") fun getTransactions() : LiveData<List<AllTransactions>>

}