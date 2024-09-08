package com.example.ticketreservation.ui.uistate

import androidx.navigation.ActivityNavigator

data class ReservationUiState(
    val origin: String = "",
    val destination: String = "",
    val departure: String = "",
)