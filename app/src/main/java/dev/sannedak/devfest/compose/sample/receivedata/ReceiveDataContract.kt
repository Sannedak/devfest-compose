package dev.sannedak.devfest.compose.sample.receivedata

import dev.sannedak.devfest.compose.sample.core.ScreenEvent
import dev.sannedak.devfest.compose.sample.core.ScreenSideEffect
import dev.sannedak.devfest.compose.sample.core.ScreenState

class ReceiveDataContract {

    data class State(
        val isLoading: Boolean = true,
        val seatNumber: Int = 0
    ) : ScreenState

    sealed class Event : ScreenEvent {
        object OnBackClicked : Event()
    }

    sealed class Effect : ScreenSideEffect {

        sealed class Navigation : Effect() {
            object Back : Navigation()
        }
    }
}
