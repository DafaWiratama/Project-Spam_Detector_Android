package tech.jayamakmurdigital.spamdetector.database

import androidx.room.*
import tech.jayamakmurdigital.spamdetector.model.Contact
import tech.jayamakmurdigital.spamdetector.model.SMS

@Dao
interface SMSDao {
    val messages: Array<SMS>
        @Query("SELECT * FROM sms") get

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg users: SMS)

    @Delete
    fun delete(user: SMS)

    @Update
    fun update(vararg user: SMS)

    val contacts: Array<Contact>
        get() = ArrayList<Contact>().apply {
            messages.forEach { message ->
                find { it.name == message.name }.let { contact ->
                    if (contact != null) contact.messages.add(message)
                    else add(Contact(message.name).apply { this.messages.add(message) })
                }
            }
            sortByDescending { it.getLastMessage.time }
        }.toTypedArray()

    val unread
        get() = messages.filter { !it.read }.count()
}