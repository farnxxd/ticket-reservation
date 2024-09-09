package com.example.ticketreservation

import android.app.Application
import com.example.ticketreservation.data.AppContainer
import com.example.ticketreservation.data.AppDataContainer

class ReservationApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}