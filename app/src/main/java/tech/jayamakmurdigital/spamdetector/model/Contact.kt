package tech.jayamakmurdigital.spamdetector.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Contact(val name: String) : Parcelable {
    @IgnoredOnParcel
    val messages = ArrayList<SMS>()

    val getLastMessage
        get() = messages.maxByOrNull { it.time }!!

    val getUnreadMessages
        get() = messages.filter { !it.read }.count()
}