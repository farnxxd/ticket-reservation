package com.example.ticketreservation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ticketreservation.ui.screens.HomeDestination
import com.example.ticketreservation.ui.screens.HomeScreen
import com.example.ticketreservation.ui.screens.PickCityScreen
import com.example.ticketreservation.ui.screens.PickCityDestination
import com.example.ticketreservation.ui.screens.PickTicketDestination
import com.example.ticketreservation.ui.screens.PickTicketScreen
import com.example.ticketreservation.ui.screens.TicketDestination
import com.example.ticketreservation.ui.screens.TicketScreen

@Composable
fun ReservationNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToPickCity = { navController.navigate(PickCityDestination.route) },
                navigateToPickTicket = { navController.navigate(PickTicketDestination.route) }
            )
        }
        composable(route = PickCityDestination.route) {
            PickCityScreen(
                navigateUp = { navController.navigateUp() }
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
                navigateToHome = { navController.popBackStack() },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}