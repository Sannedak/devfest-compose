package dev.sannedak.devfest.compose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.sannedak.devfest.compose.sample.navigation.AppBottomNavigation
import dev.sannedak.devfest.compose.sample.navigation.AppNavigation
import dev.sannedak.devfest.compose.sample.navigation.currentBottomItemAsState
import dev.sannedak.devfest.compose.sample.ui.theme.DevFestComposeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevFestComposeSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AppHost()
                }
            }
        }
    }
}

@Composable
fun AppHost() {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        val currentSelectedItem by navController.currentBottomItemAsState()
        AppBottomNavigation(
            selectedItem = currentSelectedItem,
            onNavigationItemSelected = { selectedItem ->
                navController.navigate(selectedItem.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }) { innerPadding ->
        AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
