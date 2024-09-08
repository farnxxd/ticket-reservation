package com.example.ticketreservation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ticketreservation.ui.uistate.ReservationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReservationViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState = _uiState.asStateFlow()

    fun setOrigin(city: String) { _uiState.update { it.copy(origin = city) } }

    fun setDestination(city: String) { _uiState.update { it.copy(destination = city) } }

    fun setDeparture(date: String) { _uiState.update { it.copy(departure = date) } }

}