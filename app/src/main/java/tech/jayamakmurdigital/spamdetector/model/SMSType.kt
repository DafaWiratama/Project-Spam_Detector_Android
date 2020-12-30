package tech.jayamakmurdigital.spamdetector.model

import androidx.room.TypeConverter

enum class SMSType(code: Int) {
    INBOX(0), SEND(1)
}

class SMSTypeConverter {

    @TypeConverter
    fun fromType(value: SMSType): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toType(value: Int): SMSType {
        return if (value == 0) SMSType.INBOX else SMSType.SEND
    }

}
