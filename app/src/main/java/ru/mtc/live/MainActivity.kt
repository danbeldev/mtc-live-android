package ru.mtc.live

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.mtc.live.data.network.createMainNetwork
import ru.mtc.live.ui.theme.MtcliveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainNetwork = createMainNetwork()

        setContent {
            MtcliveTheme {
                MainScreen(network = mainNetwork)
            }
        }
    }
}