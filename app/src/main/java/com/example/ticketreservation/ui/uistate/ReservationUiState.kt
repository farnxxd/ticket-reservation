package com.example.ticketreservation.ui.uistate

import androidx.navigation.ActivityNavigator
import com.example.ticketreservation.data.ticket.Ticket

data class ReservationUiState(
    val origin: String = "",
    val destination: String = "",
    val departure: String = "",
    val ticketsList: List<Ticket> = emptyList()
)