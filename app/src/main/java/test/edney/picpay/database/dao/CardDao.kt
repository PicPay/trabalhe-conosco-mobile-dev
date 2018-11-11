package test.edney.picpay.database.dao

import androidx.room.Dao

@Dao
interface CardDao {
    /*@Query("select * from user_session limit 1")
    fun getUser(): LiveData<UserModel>

    @Insert(onConflict = ROLLBACK)
    fun addUser(UserModel: UserModel)

    @Query("DELETE FROM user_session")
    fun deleteUser()

    @Query("select numberChannel from user_session where numberChannel == :number")
    fun checkIfExists(number: String): Boolean*/
}