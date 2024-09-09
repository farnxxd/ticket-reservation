package com.example.ticketreservation.data.userticket

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTicketDao {
    @Query("SELECT * FROM tickets")
    fun getAllTicketsStream(): Flow<List<UserTicket>>

    @Insert
    suspend fun addTicket(ticket: UserTicket)

    @Delete
    suspend fun deleteTicket(ticket: UserTicket)
}