package com.example.ticketreservation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.local.LocalTicketData
import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.data.util.convertIntToPriceString
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme
import kotlin.random.Random

object TicketDestination : NavigationDestination {
    override val route = "ticket"
    const val ticketIdArg = "ticketId"
    val routeWithArgs = "$route/{$ticketIdArg}"
}

@Composable
fun TicketScreen(
    ticket: Ticket,
    pickedSeat: List<Pair<Int, Boolean>>,
    seatsOwnByUser: Set<Int>,
    addSeat: (Int) -> Unit,
    onSubmit: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { ReservationTopAppBar(canNavigateBack = true, navigateUp = navigateUp)},
        modifier = modifier
    ) { paddingValue ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(8.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                InfoCard(ticket = ticket)
            }
            PickSeat(
                pickedSeat = pickedSeat,
                seatsOwnByUser = seatsOwnByUser,
                onSeatClick = addSeat
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                TotalBill(price = ticket.price, seats = seatsOwnByUser.size)
            }
            Button(
                onClick = onSubmit,
                enabled = seatsOwnByUser.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) { Text(text = "خرید بلیط") }
        }
    }
}

@Composable
fun InfoCard(
    ticket: Ticket,
    modifier: Modifier = Modifier,
    onRefundClick: () -> Unit = {}
) {
    Card(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append(ticket.origin)
                    append(" به ")
                    append(ticket.destination)
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "تاریخ حرکت:", style = MaterialTheme.typography.labelMedium)
                    Text(text = ticket.departureDate, style = MaterialTheme.typography.titleMedium)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "زمان حرکت:", style = MaterialTheme.typography.labelMedium)
                    Text(text = ticket.departureTime, style = MaterialTheme.typography.titleMedium)
                }
            }
            Text(
                text = buildAnnotatedString {
                    append("شرکت مسافربری ")
                    append(ticket.company)
                },
                style = MaterialTheme.typography.labelLarge
            )
            if (ticket.seatNumber.isNotEmpty()) {
                val seatList = ticket.seatNumber.split(",")
                Text(
                    text = buildAnnotatedString {
                        append("شماره صندلی ")
                        append(ticket.seatNumber.dropLast(1))
                    },
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = convertIntToPriceString(ticket.price * seatList.size),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = onRefundClick, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "استرداد")
                }
            }
        }
    }
}

@Composable
fun PickSeat(
    pickedSeat: List<Pair<Int, Boolean>>,
    seatsOwnByUser: Set<Int>,
    onSeatClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    text = "صندلی خود را انتخاب کنید",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                userScrollEnabled = false,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 40.dp)
            ) {
                items(
                    items = pickedSeat,
                    span = {
                        if (it.first % 3 == 0) GridItemSpan(2)
                        else GridItemSpan(1)
                    }
                ) {
                    if (it.first % 3 == 0) {
                        Row {
                            FilterChip(
                                selected = seatsOwnByUser.contains(it.first),
                                onClick = { onSeatClick(it.first) },
                                label = { Text(text = (it.first + 1).toString()) },
                                enabled = it.second,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(vertical = 2.dp, horizontal = 2.dp)
                                    .weight(1f)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    } else {
                        FilterChip(
                            selected = seatsOwnByUser.contains(it.first),
                            onClick = { onSeatClick(it.first) },
                            label = { Text(text = (it.first + 1).toString()) },
                            enabled = it.second,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(vertical = 2.dp, horizontal = 2.dp)
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun TotalBill(
    price: Int,
    seats: Int,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(text = "هزینه نهایی")
        },
        supportingContent = {
            Text(text = if (seats != 0)  "$seats صندلی" else " ")
        },
        trailingContent = {
            Text(
                text = convertIntToPriceString(price = price * seats),
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = modifier.alpha(if (seats == 0) 0.5f else 1f)
    )
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    TicketReservationTheme {
        val list = List(24) { Pair(it, Random.nextBoolean()) }
        TicketScreen(
            ticket = LocalTicketData.sampleTicket,
            pickedSeat = list,
            seatsOwnByUser = setOf(list.filter { it.second }.map { it.first }.random()),
            addSeat = {},
            onSubmit = {},
            navigateUp = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}