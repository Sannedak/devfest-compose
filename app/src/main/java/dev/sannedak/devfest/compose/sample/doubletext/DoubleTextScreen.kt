package dev.sannedak.devfest.compose.sample.doubletext

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun DoubleTextScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                context.startActivity(Intent(context, DoubleTextActivity::class.java))
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Open activity")
        }
    }
}
