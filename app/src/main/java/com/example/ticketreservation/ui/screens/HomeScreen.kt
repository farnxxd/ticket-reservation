package com.example.ticketreservation.ui.screens

import android.os.Build
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ticketreservation.R
import com.example.ticketreservation.ReservationNavigationBar
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.local.LocalTicketData
import com.example.ticketreservation.data.ticket.Ticket
import com.example.ticketreservation.data.util.convertLongToTime
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.navigation.NavigationItem
import com.example.ticketreservation.ui.theme.TicketReservationTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    origin: String,
    destination: String,
    departure: String,
    ticketList: List<Ticket>,
    swapSides: () -> Unit,
    setDeparture: (String) -> Unit,
    navigateToPickOrgCity: () -> Unit,
    navigateToPickDesCity: () -> Unit,
    navigateToPickTicket: () -> Unit,
    onRefundClick: (Ticket) -> Unit,
    modifier: Modifier = Modifier
) {
    var screen by rememberSaveable { mutableStateOf(NavigationItem.Home) }
    Scaffold(
        topBar = { ReservationTopAppBar(canNavigateBack = false) },
        bottomBar = {
            ReservationNavigationBar(
                screen = screen,
                onClick = { screen = it }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        when (screen) {
            NavigationItem.Home ->
                TicketSearchScreen(
                    origin = origin,
                    destination = destination,
                    departure = departure,
                    swapSides = swapSides,
                    setDeparture = setDeparture,
                    navigateToPickOrgCity = navigateToPickOrgCity,
                    navigateToPickDesCity = navigateToPickDesCity,
                    navigateToPickTicket = navigateToPickTicket,
                    modifier = Modifier.padding(paddingValues)
                )

            NavigationItem.Ticket ->
                UserTicketScreen(
                    ticketList = ticketList,
                    onRefundClick = { onRefundClick(it) },
                    modifier = Modifier.padding(paddingValues)
                )
        }

    }
}

@Composable
fun TicketSearchScreen(
    origin: String,
    destination: String,
    departure: String,
    swapSides: () -> Unit,
    setDeparture: (String) -> Unit,
    navigateToPickOrgCity: () -> Unit,
    navigateToPickDesCity: () -> Unit,
    navigateToPickTicket: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val enabled = origin.isNotEmpty() && destination.isNotEmpty() && departure.isNotEmpty()
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier.fillMaxSize()) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Card(modifier = Modifier.align(Alignment.Center)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentWidth()
                        .width(IntrinsicSize.Min)
                        .padding(16.dp)
                ) {
                    Box {
                        Column {
                            HomeTextField(
                                value = origin,
                                label = "مبدأ",
                                painterRes = R.drawable.origin_24dp,
                                onClick = navigateToPickOrgCity
                            )
                            HomeTextField(
                                value = destination,
                                label = "مقصد",
                                painterRes = R.drawable.destination_24dp,
                                onClick = navigateToPickDesCity
                            )
                        }
                        if (origin.isNotEmpty() || destination.isNotEmpty()) {
                            FilledIconButton(
                                onClick = swapSides,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 32.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.swap_24dp),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    HomeTextField(
                        value = departure,
                        label = "زمان حرکت",
                        painterRes = R.drawable.calendar_24dp,
                        onClick = { isDatePickerVisible = true }
                    )
                    if (isDatePickerVisible) {
                        CustomDatePicker(
                            setDeparture = setDeparture,
                            onDismissRequest = { isDatePickerVisible = false },
                            clearFocus = { focusManager.clearFocus() })
                    }
                    Button(
                        onClick = navigateToPickTicket,
                        enabled = enabled,
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

@Composable
fun UserTicketScreen(
    ticketList: List<Ticket>,
    onRefundClick: (Ticket) -> Unit,
    modifier: Modifier = Modifier
) {
    val emptyTicket = LocalTicketData.emptyTicket
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedTicket by remember { mutableStateOf(emptyTicket) }

    val openDialog = { isDialogVisible = true }
    val closeDialog = {
        isDialogVisible = false
        selectedTicket = emptyTicket
    }

    val list = ticketList.sortedBy {
        it.departureDate
        it.departureTime
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(items = list, key = { it.id }) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                InfoCard(
                    ticket = it,
                    onRefundClick = {
                        selectedTicket = it
                        openDialog()
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
    if (isDialogVisible) {
        AlertDialog(
            title = {
                Text(text = "هشدار")
            },
            text = {
                Text(text = "آیا مایل به استرداد بلیط خریداری شده هستید؟")
            },
            onDismissRequest = closeDialog,
            confirmButton = {
                TextButton(onClick = {
                    onRefundClick(selectedTicket)
                    closeDialog()
                }) {
                    Text(text = "تأیید")
                }
            },
            dismissButton = {
                TextButton(onClick = closeDialog) {
                    Text(text = "لغو")
                }
            }
        )
    }
}

@Composable
fun HomeTextField(
    value: String,
    label: String,
    @DrawableRes painterRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        label = { Text(text = label) },
        leadingIcon = { Icon(painter = painterResource(id = painterRes), contentDescription = "") },
        trailingIcon = {
            if (value != "") Icon(
                imageVector = Icons.Default.Done,
                contentDescription = ""
            )
        },
        modifier = modifier.onFocusChanged {
            if (it.hasFocus) onClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    setDeparture: (String) -> Unit,
    onDismissRequest: () -> Unit,
    clearFocus: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(
        yearRange = (2024..2024)
    )
    val confirmEnabled =
        remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        DatePickerDialog(
            onDismissRequest = {
                clearFocus()
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        clearFocus()
                        onDismissRequest()
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
            },
            modifier = modifier
        ) {
            DatePicker(state = datePickerState)
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
                ticketList = emptyList(),
                swapSides = {},
                setDeparture = {},
                navigateToPickOrgCity = {},
                navigateToPickDesCity = {},
                navigateToPickTicket = {},
                onRefundClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}