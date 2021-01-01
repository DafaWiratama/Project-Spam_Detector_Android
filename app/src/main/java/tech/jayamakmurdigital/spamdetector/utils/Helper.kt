package tech.jayamakmurdigital.spamdetector.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


fun Long.toString(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(Date(this))

fun logD(msg: Any?) = Log.d("Eirene", msg.toString())