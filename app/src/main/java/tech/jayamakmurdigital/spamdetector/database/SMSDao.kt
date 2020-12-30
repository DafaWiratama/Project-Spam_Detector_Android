package tech.jayamakmurdigital.spamdetector.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import tech.jayamakmurdigital.spamdetector.model.SMS

@Dao
interface SMSDao {
    val messages: Array<SMS>
        @Query("SELECT * FROM sms") get

    @Insert
    fun insert(vararg users: SMS)

    @Delete
    fun delete(user: SMS)
}