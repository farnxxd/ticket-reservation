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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme

object TicketDestination : NavigationDestination {
    override val route = "ticket"
    const val ticketIdArg = "ticketId"
    val routeWithArgs = "$route/{$ticketIdArg}"
}

@Composable
fun TicketScreen(
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { ReservationTopAppBar(canNavigateBack = true, navigateUp = navigateUp)},
        modifier = modifier
    ) {
        TicketInfo(
            onSubmitClick = navigateToHome,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun TicketInfo(
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            InfoCard()
        }
        PickSeat()
        Button(
            onClick = onSubmitClick,
            modifier = Modifier.fillMaxWidth()
        ) { Text(text = "خرید بلیط") }
    }
}

@Composable
fun InfoCard(modifier: Modifier = Modifier) {
    Card {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "رشت")
                Text(text = "به")
                Text(text = "قزوین")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "تاریخ حرکت:")
                    Text(text = "22 مهر")
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "زمان حرکت:")
                    Text(text = "12:00")
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "شرکت مسافربری")
                Text(text = "رویال سفر")
            }
        }
    }
}

@Composable
fun PickSeat(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
                text = "صندلی خود را انتخاب کنید",
                modifier = Modifier.fillMaxWidth()
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 80.dp)
        ) {
            items(
                count = 24,
                span = { if (it % 3 == 0) GridItemSpan(2)
                    else GridItemSpan(1)}
            ) {
                if (it % 3 == 0) {
                    Row {
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = { Text(text = (it + 1).toString()) },
                            modifier = Modifier
                                .size(40.dp)
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                } else {
                    SuggestionChip(
                        onClick = { /*TODO*/ },
                        label = { Text(text = (it + 1).toString()) },
                        modifier = Modifier.size(40.dp)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    TicketReservationTheme {
        TicketScreen(
            navigateToHome = {},
            navigateUp = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}