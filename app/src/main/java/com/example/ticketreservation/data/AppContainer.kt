package com.example.ticketreservation.data

import android.content.Context

interface AppContainer {
    val reservationRepository: ReservationRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val reservationRepository: ReservationRepository by lazy {
        ReservationRepository(
            ticketDao = ReservationDatabase.getDatabase(context).ticketDao()
        )
    }
}