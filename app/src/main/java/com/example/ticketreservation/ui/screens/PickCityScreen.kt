package com.example.ticketreservation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.local.LocalCityData
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme

object PickOrgCityDestination : NavigationDestination {
    override val route = "pick_org_city"
}

object PickDesCityDestination : NavigationDestination {
    override val route = "pick_des_city"
}

@Composable
fun PickCityScreen(
    setCity: (String) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var enabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { ReservationTopAppBar(canNavigateBack = true, navigateUp = navigateUp) },
        modifier = modifier
    ) { paddingValue ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            PickCity(
                onCitySelected = { enabled = true },
                onConfirmClick = {
                    setCity(it)
                    navigateUp()
                },
                enabled = enabled,
                modifier = Modifier.padding(paddingValue)
            )
        }

    }
}

@Composable
fun PickCity(
    modifier: Modifier = Modifier,
    onCitySelected: () -> Unit = {},
    onConfirmClick: (String) -> Unit = {},
    enabled: Boolean = false
) {
    var selectedCity by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(
                items = LocalCityData.cities,
                key = { it.first }
            ) {
                ListItem(
                    headlineContent = { Text(text = it.second) },
                    trailingContent = { if (selectedCity == it.second)
                        Icon(imageVector = Icons.Default.Done, contentDescription = "")
                    },
                    modifier = Modifier.clickable {
                        selectedCity = it.second
                        onCitySelected()
                    }
                )
            }
        }
        TextField(
            value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onConfirmClick(selectedCity) },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "تایید")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PickCityScreenPreview() {
    TicketReservationTheme {
        PickCityScreen(
            setCity = {},
            navigateUp = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}