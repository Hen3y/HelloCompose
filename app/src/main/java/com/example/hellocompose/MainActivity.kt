package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hellocompose.ui.theme.HelloComposeTheme

val localNavController = compositionLocalOf<NavController> { error("Not Provide") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloComposeTheme {
                MyApp()
            }
        }
    }
}

enum class Screens(val screenName: String) {
    HOME("Home"),
    LONG_CARD_LIST("LongCardList"),
    MY_SCAFFOLD("MyScaffold"),
    PHOTOGRAPHER_CARD("PhotographerCard"),
}

@Composable
fun MyApp() {
    val navController = rememberNavController();
    CompositionLocalProvider(localNavController provides navController) {
        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") { Home() }
            composable("LongCardList") { LongCardList() }
            composable("MyScaffold") { MyScaffold() }
            composable("PhotographerCard") { PhotographerCard() }
        }
    }
}

@Composable
fun Home() {
    Surface {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Screens.values().filter { screen -> screen !== Screens.HOME }
                .forEach { screen -> NavigateButton(navKey = screen.screenName) }
        }
    }
}

@Composable
fun NavigateButton(navKey: String) {
    val navController = localNavController.current
    Button(
        modifier = Modifier
            .padding(8.dp)
            .widthIn(min = 200.dp),
        onClick = { navController.navigate(navKey) }) {
        Text(text = navKey)
    }
}

