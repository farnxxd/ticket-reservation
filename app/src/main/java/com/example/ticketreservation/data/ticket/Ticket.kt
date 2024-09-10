package com.example.ticketreservation.data.ticket

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
@Immutable
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val origin: String,
    val destination: String,
    @ColumnInfo(name = "departure_date") val departureDate: String,
    @ColumnInfo(name = "departure_time") val departureTime: String,
    val price: Int,
    val company: String,
    @ColumnInfo(name = "seat_number")val seatNumber: String = ""
)
