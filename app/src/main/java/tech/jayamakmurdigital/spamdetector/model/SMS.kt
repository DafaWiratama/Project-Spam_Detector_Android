package tech.jayamakmurdigital.spamdetector.model
import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import tech.jayamakmurdigital.spamdetector.utils.SpamDetector

@Entity
class SMS  {
    @PrimaryKey
    var id: String=""

    @ColumnInfo(name = "name")
    var name: String=""

    @ColumnInfo(name = "text")
    var text: String =""

    @ColumnInfo(name = "group")
    @TypeConverters(SMSTypeConverter::class)
    var type: SMSType? = null

    @ColumnInfo(name = "read")
    var read: Boolean = false

    @ColumnInfo(name = "time")
    var time: Long = 0

    fun getSpamScore(context: Context) = SpamDetector(context).checkMessage(text)
}
