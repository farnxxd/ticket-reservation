package com.example.ticketreservation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.data.ticket.TicketDao
import kotlin.concurrent.Volatile

@Database(entities = [Ticket::class], version = 1, exportSchema = false)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var instance: ReservationDatabase? = null

        fun getDatabase(context: Context): ReservationDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, ReservationDatabase::class.java, "ticket_database")
                    .build()
                    .also { instance = it }
            }
        }
    }
}