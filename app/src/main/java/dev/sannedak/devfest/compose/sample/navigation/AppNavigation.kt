package dev.sannedak.devfest.compose.sample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.sannedak.devfest.compose.sample.doubletext.DoubleTextDestination
import dev.sannedak.devfest.compose.sample.doubletext.DoubleTextScreen
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataDestination
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataScreen
import dev.sannedak.devfest.compose.sample.senddata.SendDataDestination
import dev.sannedak.devfest.compose.sample.senddata.SendDataScreen
import dev.sannedak.devfest.compose.sample.video.VideoScreen
import dev.sannedak.devfest.compose.sample.video.VideoScreenDestination

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.SendData.route,
        modifier = modifier
    ) {
        addDoubleTextTopLevel()
        addSendDataTopLevel(navController)
        addVideoTopLevel()
    }
}

private fun NavGraphBuilder.addDoubleTextTopLevel() {
    navigation(
        route = BottomNavItem.DoubleText.route,
        startDestination = DoubleTextDestination.route
    ) {
        addDoubleTextScreen()
    }
}

private fun NavGraphBuilder.addSendDataTopLevel(navController: NavController) {
    navigation(
        route = BottomNavItem.SendData.route,
        startDestination = SendDataDestination.route
    ) {
        addSendDataScreen(navController)
        addReceiveDataScreen(navController)
    }
}

private fun NavGraphBuilder.addVideoTopLevel() {
    navigation(
        route = BottomNavItem.Video.route,
        startDestination = VideoScreenDestination.route
    ) {
        addVideoScreen()
    }
}

private fun NavGraphBuilder.addDoubleTextScreen() {
    composable(DoubleTextDestination.route) {
        DoubleTextScreen()
    }
}

private fun NavGraphBuilder.addSendDataScreen(navController: NavController) {
    composable(SendDataDestination.route) {
        SendDataScreen(toTicket = { name, surname ->
            navController.navigate(
                ReceiveDataDestination.createReceiveDataDestination(name, surname)
            )
        })
    }
}

private fun NavGraphBuilder.addReceiveDataScreen(navController: NavController) {
    composable(ReceiveDataDestination.route) { backStack ->
        val name = backStack.arguments?.getString(
            ReceiveDataDestination.NAME_STRING_PARAM
        ).orEmpty()
        val surname = backStack.arguments?.getString(
            ReceiveDataDestination.SURNAME_STRING_PARAM
        ).orEmpty()
        ReceiveDataScreen(
            name = name,
            surname = surname,
            onBack = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.addVideoScreen() {
    composable(VideoScreenDestination.route) {
        VideoScreen()
    }
}
