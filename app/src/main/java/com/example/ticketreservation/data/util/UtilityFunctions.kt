package com.example.ticketreservation.data.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("MM-dd", Locale.US)
    return format.format(date)
}

fun convertIntToPriceString(price: Int, withCurrency: Boolean = true): String {
    val reversed = price.toString().reversed()
    val formatted = reversed.chunked(3).joinToString(",")
    return if (withCurrency) formatted.reversed() + " تومان" else formatted.reversed()
}