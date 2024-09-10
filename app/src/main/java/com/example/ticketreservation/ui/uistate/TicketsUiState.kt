package com.example.ticketreservation.ui.uistate

import com.example.ticketreservation.data.ticket.Ticket

data class TicketsUiState(
    val ownedTicket: List<Ticket> = emptyList(),
)
