package com.example.ticketreservation.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ticketreservation.R
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.local.LocalTicketData
import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.data.util.convertIntToPriceString
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme
import kotlinx.coroutines.launch

object PickTicketDestination : NavigationDestination {
    override val route = "pick_ticket"
    override val titleRes = R.string.app_name_fa
}

enum class SortList(
    val onClick: (List<Ticket>) -> List<Ticket>,
    @StringRes val label: Int
) {
    Time(onClick = { list -> list.sortedBy { it.departureTime } }, label = R.string.departure_date),
    Price(onClick = { list -> list.sortedBy { it.price } }, label = R.string.price)
}

@Composable
fun PickTicketScreen(
    ticketsList: List<Ticket>,
    navigateToTicket: (Ticket) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var sort by rememberSaveable { mutableStateOf(SortList.Time) }
    var sortedList by rememberSaveable { mutableStateOf(SortList.Time.onClick(ticketsList)) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ReservationTopAppBar(
                titleRes = PickTicketDestination.titleRes,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { paddingValue ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    for (sortItem in SortList.entries) {
                        FilterChip(
                            selected = sort == sortItem,
                            onClick = {
                                sort = sortItem
                                sortedList = sortItem.onClick(sortedList)
                                coroutineScope.launch {
                                    listState.scrollToItem(0)
                                }
                            },
                            label = {
                                Text(
                                    text = stringResource(id = sortItem.label),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = listState
                ) {
                    items(items = sortedList, key = { it.id }) {
                        Ticket(
                            ticket = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .clickable { navigateToTicket(it) }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun Ticket(
    ticket: Ticket,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        ListItem(
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.bus_24dp),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            },
            overlineContent = {
                Text(
                    text = buildAnnotatedString {
                        append(ticket.origin)
                        append(stringResource(R.string.to))
                        append(ticket.destination)
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            },
            headlineContent = {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.departure_date))
                        append(": ")
                        append(ticket.departureTime)
                    },
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal
                )
            },
            supportingContent = {
                Text(
                    text = ticket.company,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal
                )
            },
            trailingContent = {
                Text(
                    text = convertIntToPriceString(ticket.price),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            },
            colors = ListItemDefaults.colors(Color.Transparent)
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
                ticket = LocalTicketData.sampleTicket,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}