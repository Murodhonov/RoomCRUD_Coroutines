package uz.umarxon.roomcrud_coroutines.repositories

import uz.umarxon.roomcrud_coroutines.db.entity.User
import uz.umarxon.roomcrud_coroutines.db.dao.UserDao

class UserRepository(private val dao: UserDao) {

    val users = dao.getAll()

    suspend fun insert(user: User):Long{
        return dao.insertUser(user)
    }

    suspend fun update(user: User):Int{
        return dao.updateUser(user)
    }

    suspend fun delete(user: User):Int{
        return dao.deleteUser(user)
    }

    suspend fun deleteAll():Int{
        return dao.deleteAll()
    }

}