package com.example.ticketreservation.data

import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.data.ticket.TicketDao
import kotlinx.coroutines.flow.Flow

class ReservationRepository(private val ticketDao: TicketDao) {
    fun getAllTicketsStream(): Flow<List<Ticket>> = ticketDao.getAllTicketsStream()

    suspend fun buyTicket(ticket: Ticket) = ticketDao.addTicket(ticket)

    suspend fun refundTicket(ticket: Ticket) = ticketDao.deleteTicket(ticket)
}