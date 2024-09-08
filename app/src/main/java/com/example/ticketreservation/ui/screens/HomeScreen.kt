package com.example.ticketreservation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.util.convertLongToTime
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme
import com.example.ticketreservation.ui.viewmodel.ReservationViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navigateToPickCity: () -> Unit,
    navigateToPickTicket: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReservationViewModel = ReservationViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val isDatePickerVisible = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { ReservationTopAppBar(canNavigateBack = false) },
        modifier = modifier
    ) { paddingValues ->
        DetailsForm(
            origin = uiState.origin,
            destination = uiState.destination,
            departure = uiState.departure,
            setOrigin = { viewModel.setOrigin(it) },
            setDestination = { viewModel.setDestination(it) },
            setDeparture = { viewModel.setDeparture(it) },
            isDatePickerVisible = isDatePickerVisible.value,
            onCityPickerClick = navigateToPickCity,
            onDatePickerClick = { isDatePickerVisible.value = true },
            onDatePickerDismiss = { isDatePickerVisible.value = false },
            onSearchClick = navigateToPickTicket,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsForm(
    origin: String,
    destination: String,
    departure: String,
    setOrigin: (String) -> Unit,
    setDestination: (String) -> Unit,
    setDeparture: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDatePickerVisible: Boolean = false,
    onCityPickerClick: () -> Unit = {},
    onDatePickerClick: () -> Unit = {},
    onDatePickerDismiss: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    val newDate = remember {
        mutableStateOf("خالی")
    }
    Box(modifier = modifier) {
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
                    modifier = Modifier.onFocusEvent { if (it.hasFocus) onCityPickerClick() }
                )
                OutlinedTextField(
                    value = destination,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = {                     Text(text = "مقصد")
                    },
                    modifier = Modifier.onFocusEvent { if (it.hasFocus) onCityPickerClick() }
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
                    modifier = Modifier.onFocusEvent { if (it.hasFocus) onDatePickerClick() }
                )
                if(isDatePickerVisible) {
                    val datePickerState = rememberDatePickerState()
                    val confirmEnabled =
                        remember { derivedStateOf {datePickerState.selectedDateMillis != null } }

                    DatePickerDialog(
                        onDismissRequest = onDatePickerDismiss,
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    onDatePickerDismiss()
                                    var date = ""
                                    if (datePickerState.selectedDateMillis != null)
                                        date = convertLongToTime(datePickerState.selectedDateMillis!!)
                                    newDate.value = date
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
                    onClick = onSearchClick,
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TicketReservationTheme {
            HomeScreen(
                navigateToPickCity = {},
                navigateToPickTicket = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}