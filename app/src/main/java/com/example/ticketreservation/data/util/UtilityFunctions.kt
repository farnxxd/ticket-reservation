package com.example.ticketreservation.data.util

import android.icu.text.SimpleDateFormat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class FutureSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean =
        utcTimeMillis >= System.currentTimeMillis() - 86_400_000
}

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("MM/dd", Locale.US)
    return format.format(date)
}

fun convertIntToPriceString(price: Int, withCurrency: Boolean = true): String {
    val reversed = price.toString().reversed()
    val formatted = reversed.chunked(3).joinToString(",")
    return if (withCurrency) formatted.reversed() + " تومان" else formatted.reversed()
}