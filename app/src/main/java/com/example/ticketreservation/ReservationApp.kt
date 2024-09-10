package com.example.ticketreservation

import android.content.res.Resources.Theme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ticketreservation.ui.navigation.NavigationItem
import com.example.ticketreservation.ui.navigation.ReservationNavHost
import com.example.ticketreservation.ui.theme.TicketReservationTheme
import com.example.ticketreservation.ui.viewmodel.ReservationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationApp(
    viewModel: ReservationViewModel = viewModel(factory = ReservationViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    ReservationNavHost(viewModel = viewModel, navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationTopAppBar(
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = ""
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun ReservationNavigationBar(
    screen: NavigationItem,
    onClick: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        NavigationBar(modifier = modifier) {
            for (item in NavigationItem.entries) {
                NavigationBarItem(
                    selected = screen == item,
                    onClick = { onClick(item) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = ""
                        )
                    },
                    label = { Text(text = item.title) }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ReservationPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TicketReservationTheme {
            ReservationApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationTopAppBarPreview() {
    TicketReservationTheme {
        ReservationTopAppBar(canNavigateBack = true)
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationNavigationBarPreview() {
    TicketReservationTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ReservationNavigationBar(
                screen = NavigationItem.Home,
                onClick = {}
            )
        }
    }
}