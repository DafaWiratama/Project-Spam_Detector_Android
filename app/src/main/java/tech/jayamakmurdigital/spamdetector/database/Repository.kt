package tech.jayamakmurdigital.spamdetector.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Repository {
    lateinit var db: AppDatabase

    fun init(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            db = Room.databaseBuilder(context, AppDatabase::class.java, "message_db").fallbackToDestructiveMigration().build()
            db.clearAllTables()
        }
    }

    fun getAll(){
        
    }
}
