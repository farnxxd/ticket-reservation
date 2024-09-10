package com.example.ticketreservation.ui.navigation

import androidx.annotation.DrawableRes
import com.example.ticketreservation.R

enum class NavigationItem(val title: String, @DrawableRes val iconRes: Int) {
    Home(title = "خانه", iconRes = R.drawable.home_24dp),
    Ticket(title = "بلیط\u200Cهای من", iconRes = R.drawable.ticket_24dp)
}