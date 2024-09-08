package com.example.ticketreservation.data.ticket

data class Ticket(
    var id: Int = 0,
    val origin: String = "",
    val destination: String = "",
    val departureDate: String = "",
    val departureTime: String = "",
    val price: Int = 100000,
    val company: String = ""
)
