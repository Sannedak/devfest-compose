package dev.sannedak.devfest.compose.sample.receivedata

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.sannedak.devfest.compose.sample.core.LAUNCH_LISTEN_FOR_EFFECTS
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataContract.Effect.Navigation.Back
import dev.sannedak.devfest.compose.sample.receivedata.ReceiveDataContract.Event.OnBackClicked
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun ReceiveDataScreen(
    viewModel: ReceiveDataViewModel = viewModel(),
    name: String,
    surname: String,
    onBack: () -> Unit
) {
    val state = viewModel.screenState.value

    ReceiveDataScreenInner(
        name = name,
        surname = surname,
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                Back -> onBack()
            }
        }
    )
}

@Composable
private fun ReceiveDataScreenInner(
    name: String,
    surname: String,
    state: ReceiveDataContract.State,
    effectFlow: Flow<ReceiveDataContract.Effect>?,
    onEventSent: (event: ReceiveDataContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: ReceiveDataContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                Back -> onNavigationRequested(Back)
            }
        }?.collect()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Ticket") },
            navigationIcon = {
                IconButton(onClick = { onEventSent(OnBackClicked) }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button")
                }
            }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Hello, $name $surname!")
                    Text(text = "Your seat number: ${state.seatNumber}")
                }
            }
        }

    }
}
