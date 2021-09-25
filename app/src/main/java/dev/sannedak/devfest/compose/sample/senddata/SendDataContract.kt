package dev.sannedak.devfest.compose.sample.senddata

import dev.sannedak.devfest.compose.sample.core.ScreenEvent
import dev.sannedak.devfest.compose.sample.core.ScreenSideEffect
import dev.sannedak.devfest.compose.sample.core.ScreenState

class SendDataContract {

    data class State(
        val nameText: String = "",
        val surnameText: String = ""
    ) : ScreenState

    sealed class Event : ScreenEvent {
        class OnNameChanged(val name: String) : Event()
        class OnSurnameChanged(val surname: String) : Event()
        class OnTakeSeatClicked(val name: String, val surname: String) : Event()
    }

    sealed class Effect : ScreenSideEffect {

        sealed class Navigation : Effect() {
            class ToTicket(val name: String, val surname: String) : Navigation()
        }
    }
}
