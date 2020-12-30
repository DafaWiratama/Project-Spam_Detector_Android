package tech.jayamakmurdigital.spamdetector.utils

import tech.jayamakmurdigital.spamdetector.model.Contact
import tech.jayamakmurdigital.spamdetector.model.SMS

class SMSHelper {

    fun groupMessage(messages: Array<SMS>): Array<Contact> {
        val contacts = ArrayList<Contact>()
        messages.forEach { message ->
            contacts.find { it.name == message.name }.let { contact ->
                if (contact != null) contact.messages.add(message)
                else contacts.add(Contact(message.name).apply { this.messages.add(message) })
            }
        }
        contacts.sortByDescending { it.getLastMessage.time }
        return contacts.toTypedArray()
    }
}