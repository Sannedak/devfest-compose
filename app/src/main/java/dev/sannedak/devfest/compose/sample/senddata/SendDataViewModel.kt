package dev.sannedak.devfest.compose.sample.senddata

import dev.sannedak.devfest.compose.sample.core.BaseViewModel
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Effect
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Effect.Navigation.ToTicket
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Event
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Event.OnNameChanged
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Event.OnSurnameChanged
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Event.OnTakeSeatClicked
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.State

class SendDataViewModel : BaseViewModel<Event, State, Effect>() {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when(event) {
            is OnNameChanged -> setState { copy(nameText = event.name) }
            is OnSurnameChanged -> setState { copy(surnameText = event.surname) }
            is OnTakeSeatClicked -> setEffect { ToTicket(event.name, event.surname) }
        }
    }
}
