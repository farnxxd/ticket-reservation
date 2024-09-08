package com.example.ticketreservation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ticketreservation.R
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme

object PickTicketDestination : NavigationDestination {
    override val route = "pick_ticket"
}

@Composable
fun PickTicketScreen(
    ticketsList: List<Ticket>,
    navigateToTicket: (Int) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { ReservationTopAppBar(canNavigateBack = true, navigateUp = navigateUp) }
    ) { paddingValue ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LazyColumn(modifier = modifier.padding(paddingValue)) {
                items(items = ticketsList, key = { it.id } ) {
                    Ticket(
                        ticket = it,
                        onTicketClick = { navigateToTicket(it.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigateToTicket(it.id) }
                    )
                }
            }
        }

    }
}

@Composable
fun Ticket(
    ticket: Ticket,
    modifier: Modifier = Modifier,
    onTicketClick: () -> Unit = {}
) {
    Card(modifier = modifier) {
        ListItem(
            overlineContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = ticket.origin)
                    Text(text = "به")
                    Text(text = ticket.destination)
                }
            },
            headlineContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "زمان حرکت:")
                    Text(text = ticket.departureTime)
                }
            },
            trailingContent = {
                IconButton(onClick = onTicketClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ticket_24dp),
                        contentDescription = ""
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PickTicketScreenPreview() {
    TicketReservationTheme {
        PickTicketScreen(
            ticketsList = emptyList(),
            navigateToTicket = {},
            navigateUp = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TicketPreview() {
    TicketReservationTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Ticket(
                ticket = Ticket(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}