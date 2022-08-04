package uz.umarxon.roomcrud_coroutines.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var id:Int,

    @ColumnInfo(name = "user_name")
    var name:String,

    @ColumnInfo(name = "user_email")
    var email:String
)
