package tech.jayamakmurdigital.spamdetector.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

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

    @ColumnInfo(name = "spamScore")
    var spamScore: Int? = null
}
