package com.example.ticketreservation.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ticketreservation.R
import com.example.ticketreservation.ReservationTopAppBar
import com.example.ticketreservation.data.local.LocalCityData
import com.example.ticketreservation.ui.navigation.NavigationDestination
import com.example.ticketreservation.ui.theme.TicketReservationTheme
import kotlinx.coroutines.launch

object PickOrgCityDestination : NavigationDestination {
    override val route = "pick_org_city"
    override val titleRes = R.string.pick_org_city
}

object PickDesCityDestination : NavigationDestination {
    override val route = "pick_des_city"
    override val titleRes = R.string.pick_des_city
}

@Composable
fun PickCityScreen(
    @StringRes titleRes: Int,
    selectedCity: String,
    otherCity: String,
    setCity: (String) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newSelection by remember { mutableStateOf(selectedCity) }
    var enabled = newSelection.isNotEmpty() && newSelection != otherCity

    Scaffold(
        topBar = { ReservationTopAppBar(titleRes = titleRes, canNavigateBack = true, navigateUp = navigateUp) },
        modifier = modifier
    ) { paddingValue ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            PickCity(
                selectedCity = selectedCity,
                onCitySelected = {
                    newSelection = it
                    enabled = (newSelection.isNotEmpty()) && (newSelection != otherCity)
                },
                onConfirmClick = {
                    setCity(newSelection)
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
    selectedCity: String,
    modifier: Modifier = Modifier,
    onCitySelected: (String) -> Unit = {},
    onConfirmClick: () -> Unit = {},
    enabled: Boolean = false
) {
    var newSelection by rememberSaveable { mutableStateOf(selectedCity) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var cities by rememberSaveable { mutableStateOf(LocalCityData.cities) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            state = listState,
            reverseLayout = searchQuery.isNotEmpty(),
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = cities,
                key = { it.first }
            ) {
                ListItem(
                    headlineContent = { Text(text = it.second) },
                    trailingContent = { if (newSelection == it.second)
                        Icon(imageVector = Icons.Default.Done, contentDescription = "")
                    },
                    modifier = Modifier.clickable {
                        newSelection = it.second
                        onCitySelected(it.second)
                    }
                )
            }
        }
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                cities = LocalCityData.cities.filter { city ->
                    city.second.startsWith(searchQuery, ignoreCase = true)
                }

                if (searchQuery.isEmpty()) {
                    coroutineScope.launch {
                        listState.scrollToItem(0,0)
                    }
                }
            },
            placeholder = {
                Text(text = stringResource(R.string.enter_city))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = onConfirmClick,
            enabled = enabled,
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PickCityScreenPreview() {
    TicketReservationTheme {
        PickCityScreen(
            titleRes = PickOrgCityDestination.titleRes,
            selectedCity = "",
            otherCity = "",
            setCity = {},
            navigateUp = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}