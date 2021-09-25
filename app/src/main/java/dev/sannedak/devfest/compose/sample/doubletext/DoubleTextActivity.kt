package dev.sannedak.devfest.compose.sample.doubletext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import dev.sannedak.devfest.compose.sample.databinding.ActivityDoubleTextBinding
import dev.sannedak.devfest.compose.sample.ui.theme.DevFestComposeSampleTheme

class DoubleTextActivity : ComponentActivity() {

    private lateinit var binding: ActivityDoubleTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoubleTextBinding.inflate(layoutInflater)
        binding.textView.text = "Hello from View!"
        setComposeText()
        setContentView(binding.root)
    }

    private fun setComposeText() {
        binding.textCompose.setContent {
            DevFestComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Hello from Compose!",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
