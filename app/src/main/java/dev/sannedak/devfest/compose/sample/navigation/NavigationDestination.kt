package dev.sannedak.devfest.compose.sample.navigation

import androidx.navigation.NamedNavArgument

interface NavigationDestination {

    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()
}
