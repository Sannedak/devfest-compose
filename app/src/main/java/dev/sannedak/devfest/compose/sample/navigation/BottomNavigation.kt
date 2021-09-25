package dev.sannedak.devfest.compose.sample.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import dev.sannedak.devfest.compose.sample.doubletext.DoubleTextDestination
import dev.sannedak.devfest.compose.sample.senddata.SendDataDestination
import dev.sannedak.devfest.compose.sample.video.VideoScreenDestination

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Video : BottomNavItem(
        "videoRoot",
        "Video",
        Icons.Outlined.Videocam,
        Icons.Filled.Videocam
    )

    object DoubleText : BottomNavItem(
        "doubleTextRoot",
        "Double Text",
        Icons.Outlined.Title,
        Icons.Filled.Title
    )

    object SendData : BottomNavItem(
        "sendDataRoot",
        "Send Data",
        Icons.Outlined.Send,
        Icons.Filled.Send
    )
}

val bottomNavItems = listOf(BottomNavItem.Video, BottomNavItem.DoubleText, BottomNavItem.SendData)

@Composable
fun AppBottomNavigation(
    selectedItem: BottomNavItem,
    onNavigationItemSelected: (BottomNavItem) -> Unit
) {
    BottomNavigation {
        bottomNavItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    if (selectedItem == item)
                        Icon(
                            imageVector = item.selectedIcon,
                            contentDescription = item.label
                        )
                    else
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                },
                label = { Text(text = item.label) },
                selected = selectedItem == item,
                onClick = { onNavigationItemSelected(item) })
        }
    }
}

@Composable
fun NavController.currentBottomItemAsState(): State<BottomNavItem> {
    val selectedItem = remember { mutableStateOf<BottomNavItem>(BottomNavItem.SendData) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == BottomNavItem.Video.route } -> {
                    selectedItem.value = BottomNavItem.Video
                }
                destination.hierarchy.any { it.route == BottomNavItem.DoubleText.route } -> {
                    selectedItem.value = BottomNavItem.DoubleText
                }
                destination.hierarchy.any { it.route == BottomNavItem.SendData.route } -> {
                    selectedItem.value = BottomNavItem.SendData
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose { removeOnDestinationChangedListener(listener) }
    }
    return selectedItem
}
