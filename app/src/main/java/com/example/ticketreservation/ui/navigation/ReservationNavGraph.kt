package com.example.ticketreservation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ticketreservation.ui.screens.HomeDestination
import com.example.ticketreservation.ui.screens.HomeScreen
import com.example.ticketreservation.ui.screens.PickCityScreen
import com.example.ticketreservation.ui.screens.PickDesCityDestination
import com.example.ticketreservation.ui.screens.PickOrgCityDestination
import com.example.ticketreservation.ui.screens.PickTicketDestination
import com.example.ticketreservation.ui.screens.PickTicketScreen
import com.example.ticketreservation.ui.screens.TicketDestination
import com.example.ticketreservation.ui.screens.TicketScreen
import com.example.ticketreservation.ui.viewmodel.ReservationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationNavHost(
    viewModel: ReservationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            val uiState by viewModel.uiState.collectAsState()
            HomeScreen(
                origin = uiState.origin,
                destination = uiState.destination,
                departure = uiState.departure,
                setDeparture = { viewModel.setDeparture(it) },
                navigateToPickOrgCity = { navController.navigate(PickOrgCityDestination.route) },
                navigateToPickDesCity = { navController.navigate(PickDesCityDestination.route) },
                navigateToPickTicket = { navController.navigate(PickTicketDestination.route) }
            )
        }
        composable(route = PickOrgCityDestination.route) {
            PickCityScreen(
                setCity = { viewModel.setOrigin(it) },
                navigateUp = { navController.popBackStack() }
            )
        }
        composable(route = PickDesCityDestination.route) {
            PickCityScreen(
                setCity = { viewModel.setDestination(it) },
                navigateUp = { navController.popBackStack() }
            )
        }
        composable(route = PickTicketDestination.route) {
            PickTicketScreen(
                navigateToTicket = { navController.navigate("${TicketDestination.route}/$it") },
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = TicketDestination.routeWithArgs,
            arguments = listOf(navArgument(TicketDestination.ticketIdArg) {
                type = NavType.IntType
            })
        ) {
            TicketScreen(
                navigateToHome = { navController.popBackStack(route = HomeDestination.route, inclusive = false) },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}