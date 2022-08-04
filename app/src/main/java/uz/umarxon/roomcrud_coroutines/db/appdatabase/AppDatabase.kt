package uz.umarxon.roomcrud_coroutines.db.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.umarxon.roomcrud_coroutines.db.dao.UserDao
import uz.umarxon.roomcrud_coroutines.db.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "my_database"
                    ).build()
                }
                return instance
            }
        }
    }
}