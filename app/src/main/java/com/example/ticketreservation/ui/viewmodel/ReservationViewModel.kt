package com.example.ticketreservation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.ui.uistate.ReservationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class ReservationViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState = _uiState.asStateFlow()

    fun setOrigin(city: String) { _uiState.update { it.copy(origin = city) } }

    fun setDestination(city: String) { _uiState.update { it.copy(destination = city) } }

    fun setDeparture(date: String) { _uiState.update { it.copy(departure = date) } }

    fun createRandomTicketList() {
        val randomNumber = (1..10).random()
        val ticketsList = mutableListOf<Ticket>()

        repeat(randomNumber) {
            val randomHour = (0..23).random()
            val randomMinute = (0..5).random() * 10
            val randomPrice = (200..400).random() * 1000
            ticketsList.add(
                Ticket(
                    id = it,
                    origin = _uiState.value.origin,
                    destination = _uiState.value.destination,
                    departureDate = _uiState.value.departure,
                    departureTime = "$randomHour:$randomMinute",
                    price = randomPrice,
                    company = ""
                )
            )
        }

        _uiState.update { it.copy(ticketsList = ticketsList) }
    }
}