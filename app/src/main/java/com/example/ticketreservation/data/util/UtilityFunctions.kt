package com.example.ticketreservation.data.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("MM-dd", Locale.US)
    return format.format(date)
}