package com.example.ticketreservation.data.ticket

data class Ticket(
    var id: Int,
    val origin: String,
    val destination: String,
    val departureDate: String,
    val departureTime: String,
    val price: Int,
    val company: String
)
