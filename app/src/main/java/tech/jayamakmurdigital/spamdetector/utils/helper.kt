package tech.jayamakmurdigital.spamdetector.utils

import java.text.SimpleDateFormat
import java.util.*


fun Long.toString(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(Date(this))
