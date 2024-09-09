package com.example.ticketreservation.data.local

import com.example.ticketreservation.data.ticket.Ticket

object LocalTicketData {
    val emptyTicket = Ticket(
        id = 0,
        origin = "",
        destination = "",
        departureDate = "",
        departureTime = "",
        price = 0,
        company = ""
    )

    val sampleTicket = Ticket(
        id = 0,
        origin = "رشت",
        destination = "قزوین",
        departureDate = "04/22",
        departureTime = "12:00",
        price = 100000,
        company = "رویال سفر"
    )
}