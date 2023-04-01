package com.miniweam.quickread.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
@RequiresApi(Build.VERSION_CODES.O)
fun getDateFormatAsPeriod(date: String): String {
    val format =
        DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)
    val localDate = LocalDateTime.parse(date, format)
    val currentTime = LocalDateTime.now()

    val day = currentTime.dayOfYear - localDate.dayOfYear
    if (day > 7) {
        return "Some time ago"
    }
    if (day == 7) {
        return "a week ago"
    }

    return (currentTime.hour - localDate.hour).toString() + "hrs ago" //localDate.format(dateFormatter)
}