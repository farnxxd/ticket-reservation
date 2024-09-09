package com.example.ticketreservation.data

import com.example.ticketreservation.data.userticket.UserTicket
import com.example.ticketreservation.data.userticket.UserTicketDao
import kotlinx.coroutines.flow.Flow

class ReservationRepository(private val userTicketDao: UserTicketDao) {
    fun getAllTicketsStream(): Flow<List<UserTicket>> = userTicketDao.getAllTicketsStream()

    suspend fun buyTicket(ticket: UserTicket) = userTicketDao.addTicket(ticket)

    suspend fun refundTicket(ticket: UserTicket) = userTicketDao.deleteTicket(ticket)
}