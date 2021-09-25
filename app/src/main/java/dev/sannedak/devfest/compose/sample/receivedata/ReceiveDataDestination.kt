package dev.sannedak.devfest.compose.sample.receivedata

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.sannedak.devfest.compose.sample.navigation.NavigationDestination

object ReceiveDataDestination : NavigationDestination {

    const val NAME_STRING_PARAM = "name"
    const val SURNAME_STRING_PARAM = "surname"

    private const val RECEIVE_DATA_DESTINATION = "receiveData"
    private const val RECEIVE_DATA_NAV_DESTINATION = "$RECEIVE_DATA_DESTINATION?" +
            "&$NAME_STRING_PARAM={$NAME_STRING_PARAM}" +
            "&$SURNAME_STRING_PARAM={$SURNAME_STRING_PARAM}"

    fun createReceiveDataDestination(name: String, surname: String) = "$RECEIVE_DATA_DESTINATION?" +
            "&$NAME_STRING_PARAM=$name" +
            "&$SURNAME_STRING_PARAM=$surname"

    override val route = RECEIVE_DATA_NAV_DESTINATION

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(NAME_STRING_PARAM) { type = NavType.StringType },
            navArgument(SURNAME_STRING_PARAM) { type = NavType.StringType }
        )
}
