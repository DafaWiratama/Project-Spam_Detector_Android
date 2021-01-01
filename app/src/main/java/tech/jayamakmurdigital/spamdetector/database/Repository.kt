package tech.jayamakmurdigital.spamdetector.database

import android.content.Context
import androidx.room.Room

object Repository {
    lateinit var db: AppDatabase

    fun init(context: Context) {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "message_db").build()
//        CoroutineScope(Dispatchers.IO).launch { db.clearAllTables() }
    }

}
