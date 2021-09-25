package dev.sannedak.devfest.compose.sample.receivedata

import androidx.lifecycle.viewModelScope
import dev.sannedak.devfest.compose.sample.core.BaseViewModel
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataContract.Effect
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataContract.Effect.Navigation.Back
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataContract.Event
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataContract.Event.OnBackClicked
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataContract.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ReceiveDataViewModel : BaseViewModel<Event, State, Effect>() {

    init {
        viewModelScope.launch { getSeatNumber() }
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            OnBackClicked -> setEffect { Back }
        }
    }

    private suspend fun getSeatNumber() {
        setState { copy(isLoading = true) }
        val randomSeat = Random.nextInt(1, 30)
        delay(5000)
        setState { copy(isLoading = false, seatNumber = randomSeat) }
    }
}
