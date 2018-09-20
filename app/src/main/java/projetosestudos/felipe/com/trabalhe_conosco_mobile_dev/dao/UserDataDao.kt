package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserData

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(vararg userData: UserData)

    @Query("SELECT * FROM user_data") fun getSaldo() : LiveData<UserData>

    @Query("DELETE FROM user_data") fun deleteData()

    @Query("UPDATE user_data SET saldo = :valor WHERE id = 1 ") fun update(valor: Double)

}