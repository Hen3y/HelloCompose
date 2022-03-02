package com.example.hellocompose

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.PlusOne
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hellocompose.ui.theme.HelloComposeTheme

private val innerPadding = 16.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyScaffold() {
    val context = LocalContext.current
    val navController = localNavController.current
    var counter by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "ScaffoldDisplayActivity")
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "I Love This App!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation() {
                BottomNavigationItem(
                    selected = false,
                    onClick = { navController.navigate(Screens.HOME.screenName) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null
                        )
                    })
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { counter++ }) {
                Icon(
                    imageVector = Icons.Rounded.PlusOne,
                    contentDescription = null
                )
            }
        }
    ) {
        BodyContent(Modifier.padding(horizontal = innerPadding), counter)
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier, number: Number) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hi there!")
        Text(
            text = "Scaffold allows you to implement a UI with the basic Material Design layout structure.",
            textAlign = TextAlign.Center
        )
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyScaffoldPreview() {
    HelloComposeTheme {
        MyScaffold()
    }
}
