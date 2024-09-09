package com.example.ticketreservation.ui.uistate

import androidx.navigation.ActivityNavigator
import com.example.ticketreservation.data.local.LocalTicketData
import com.example.ticketreservation.data.ticket.Ticket

data class ReservationUiState(
    val origin: String = "",
    val destination: String = "",
    val departure: String = "",
    val ticketsList: List<Ticket> = emptyList(),
    val pickedTicket: Ticket = LocalTicketData.emptyTicket,
    val pickedTicketSeats: List<Pair<Int, Boolean>> = emptyList(),
    val seatsOwnByUser: Set<Int> = emptySet()
)