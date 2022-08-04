package uz.umarxon.roomcrud_coroutines.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.umarxon.roomcrud_coroutines.db.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User):Long

    @Update
    suspend fun updateUser(user: User):Int

    @Delete
    suspend fun deleteUser(user: User):Int

    @Query(value = "delete from users_table")
    suspend fun deleteAll():Int

    @Query(value ="select * from users_table")
    fun getAll(): LiveData<List<User>>

}