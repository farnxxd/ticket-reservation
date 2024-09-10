package com.example.ticketreservation.data.ticket

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets")
    fun getAllTicketsStream(): Flow<List<Ticket>>

    @Insert
    suspend fun addTicket(ticket: Ticket)

    @Delete
    suspend fun deleteTicket(ticket: Ticket)
}