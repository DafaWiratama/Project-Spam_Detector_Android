package tech.jayamakmurdigital.spamdetector.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.jayamakmurdigital.spamdetector.model.SMS


@Database(entities = [SMS::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): SMSDao
}