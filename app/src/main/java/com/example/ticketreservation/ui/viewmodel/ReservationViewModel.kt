package com.example.ticketreservation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ticketreservation.ReservationApplication
import com.example.ticketreservation.data.ReservationRepository
import com.example.ticketreservation.data.local.LocalCompanyData
import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.ui.uistate.ReservationUiState
import com.example.ticketreservation.ui.uistate.TicketsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class ReservationViewModel(
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState = _uiState.asStateFlow()

    private val _ticketsState = reservationRepository.getAllTicketsStream().map {
        TicketsUiState(ownedTicket = it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TicketsUiState()
        )
    val ticketsState = _ticketsState

    fun setOrigin(city: String) {
        _uiState.update { it.copy(origin = city) }
    }

    fun setDestination(city: String) {
        _uiState.update { it.copy(destination = city) }
    }

    fun setDeparture(date: String) {
        _uiState.update { it.copy(departure = date) }
    }

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
                    departureTime = "${if (randomHour < 10) "0$randomHour" else randomHour}:" +
                            "${if (randomMinute < 10) "0$randomMinute" else randomMinute}",
                    price = (200..400).random() * 1000,
                    company = LocalCompanyData.companies.random()
                )
            )
        }

        _uiState.update { it.copy(ticketsList = ticketsList) }
    }

    fun setPickedTicket(ticket: Ticket) {
        _uiState.update { it.copy(pickedTicket = ticket) }
    }

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

    fun clearSeats() {
        _uiState.update { it.copy(seatsOwnByUser = emptySet()) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ReservationApplication)
                ReservationViewModel(application.container.reservationRepository)
            }
        }
    }
}