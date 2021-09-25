package dev.sannedak.devfest.compose.sample.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : ScreenEvent, UiState : ScreenState, Effect : ScreenSideEffect>
    : ViewModel() {

    private val initialState: UiState by lazy { setInitialState() }

    private val _screenState: MutableState<UiState> = mutableStateOf(initialState)
    val screenState: State<UiState> = _screenState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    abstract fun setInitialState(): UiState

    abstract fun handleEvents(event: Event)

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = _screenState.value.reducer()
        _screenState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }
}
