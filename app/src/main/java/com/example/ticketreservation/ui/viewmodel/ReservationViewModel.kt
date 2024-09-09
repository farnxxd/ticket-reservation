package com.example.ticketreservation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ticketreservation.data.local.LocalCompanyData
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

            ticketsList.add(
                Ticket(
                    id = it,
                    origin = _uiState.value.origin,
                    destination = _uiState.value.destination,
                    departureDate = _uiState.value.departure,
                    departureTime = "${if (randomHour<10) "0$randomHour" else randomHour}:" +
                            "${if (randomMinute<10) "0$randomMinute" else randomMinute}",
                    price = (200..400).random() * 1000,
                    company = LocalCompanyData.companies.random()
                )
            )
        }

        _uiState.update { it.copy(ticketsList = ticketsList) }
    }

    fun setPickedTicket(ticket: Ticket) { _uiState.update { it.copy(pickedTicket = ticket) }}

    fun setRandomPickedSeats(ticket: Ticket) {
        val pickedTicket = _uiState.value.pickedTicket

        if (ticket != pickedTicket) {
            val pickedSeats = List(24) { Pair(it, Random.nextBoolean()) }
            _uiState.update { it.copy(pickedTicketSeats = pickedSeats) }
        }
    }

    fun addSeat(seat: Int) {
        val seatsOwnByUser = _uiState.value.seatsOwnByUser.toMutableSet()

        if (seatsOwnByUser.contains(seat)) seatsOwnByUser.remove(seat)
        else seatsOwnByUser.add(seat)

        _uiState.update { it.copy(seatsOwnByUser = seatsOwnByUser) }
    }

    fun clearSeats() { _uiState.update { it.copy(seatsOwnByUser = emptySet()) } }
}