package com.example.ticketreservation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.util.convertLongToTime
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    origin: String,
    destination: String,
    departure: String,
    setDeparture: (String) -> Unit,
    navigateToPickOrgCity: () -> Unit,
    navigateToPickDesCity: () -> Unit,
    navigateToPickTicket: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { ReservationTopAppBar(canNavigateBack = false) },
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = modifier.fillMaxSize().padding(paddingValues)) {
            Card(modifier = Modifier.align(Alignment.Center)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentWidth()
                        .width(IntrinsicSize.Min)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = origin,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        label = { Text(text = "مبدأ") },
                        modifier = Modifier
                            .onFocusChanged { if (it.hasFocus) navigateToPickOrgCity() }
                    )
                    OutlinedTextField(
                        value = destination,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        label = {
                            Text(text = "مقصد")
                        },
                        modifier = Modifier
                            .onFocusChanged { if (it.hasFocus) navigateToPickDesCity() }
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = departure,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        label = { Text(text = "زمان حرکت") },
                        modifier = Modifier.onFocusEvent {
                            if (it.hasFocus) isDatePickerVisible = true
                        }
                    )
                    if (isDatePickerVisible) {
                        val datePickerState = rememberDatePickerState()
                        val confirmEnabled =
                            remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

                        DatePickerDialog(
                            onDismissRequest = { isDatePickerVisible = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        isDatePickerVisible = false
                                        var date = ""
                                        if (datePickerState.selectedDateMillis != null)
                                            date =
                                                convertLongToTime(datePickerState.selectedDateMillis!!)
                                        setDeparture(date)
                                    },
                                    enabled = confirmEnabled.value
                                ) {
                                    Text(text = "تأیید")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }
                    Button(
                        onClick = navigateToPickTicket,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = "جستجو")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TicketReservationTheme {
            HomeScreen(
                origin = "",
                destination = "",
                departure = "",
                setDeparture = {},
                navigateToPickOrgCity = {},
                navigateToPickDesCity = {},
                navigateToPickTicket = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}