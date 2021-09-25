package dev.sannedak.devfest.compose.sample.senddata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.sannedak.devfest.compose.sample.core.LAUNCH_LISTEN_FOR_EFFECTS
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Effect.Navigation.ToTicket
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Event.OnNameChanged
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Event.OnSurnameChanged
import dev.sannedak.devfest.compose.sample.senddata.SendDataContract.Event.OnTakeSeatClicked
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SendDataScreen(
    viewModel: SendDataViewModel = viewModel(),
    toTicket: (name: String, surname: String) -> Unit
) {
    val state = viewModel.screenState.value

    SendDataScreenInner(
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is ToTicket -> toTicket(navigationEffect.name, navigationEffect.surname)
            }
        }
    )
}

@Composable
private fun SendDataScreenInner(
    state: SendDataContract.State,
    effectFlow: Flow<SendDataContract.Effect>?,
    onEventSent: (event: SendDataContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: SendDataContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is ToTicket -> onNavigationRequested(ToTicket(effect.name, effect.surname))
            }
        }?.collect()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        NameInput(
            name = state.nameText,
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            onNameChange = { name -> onEventSent(OnNameChanged(name)) }
        )
        SurnameInput(
            surname = state.surnameText,
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            onSurnameChange = { surname -> onEventSent(OnSurnameChanged(surname)) }
        )
        Button(
            onClick = { onEventSent(OnTakeSeatClicked(state.nameText, state.surnameText)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .align(CenterHorizontally)
        ) {
            Text(text = "Take seat")
        }
    }
}

@Composable
private fun NameInput(
    name: String,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = { onNameChange(it) },
        modifier = modifier,
        label = { Text(text = "Name") }
    )
}

@Composable
private fun SurnameInput(
    surname: String,
    modifier: Modifier = Modifier,
    onSurnameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = surname,
        onValueChange = { onSurnameChange(it) },
        modifier = modifier,
        label = { Text(text = "Surname") }
    )
}
