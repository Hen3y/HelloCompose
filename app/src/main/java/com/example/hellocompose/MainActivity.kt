package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hellocompose.ui.theme.HelloComposeTheme

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

@Composable
fun MyApp() {
    // by - property delegate 属性委托
    // var shouldShowOnBoarding by remember { mutableStateOf(true) }

    // The remember function works only as long as the composable is kept in the Composition
    // Configuration changes会导致app restart，从而导致之前remember的state丢失
    // 应用rememberSaveable储存的值在activity或process recreation时保留
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnBoarding) {
        // 如果要在子组件修改state，不应该把state直接传递下去，而是传递一个修改state的callback function
        OnBoardingScreen(onClickContinue = { shouldShowOnBoarding = false })
    } else {
        Greetings(names = List(1000) { "$it" })
    }
}

@Composable
fun Greeting(name: String) {
    // 使用mutableStateOf()创建一个state
    // 用remember在多次recomposition间保存state的值

    // val expanded = remember(calculation = { -> mutableStateOf(false) })
    // line above could be simplified as below
    // val expanded = remember { mutableStateOf(false) }
    val expanded = rememberSaveable { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = "$name!")
            }
            OutlinedButton(
                onClick = {
                    expanded.value = !expanded.value
                }) {
                Text(text = if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun Greetings(names: List<String>) {
    // use LazyColumn/LazyRow for large size list or collection to guarantee app performance
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun OnBoardingScreen(onClickContinue: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello Compose")
            Button(
                // modifier控制Button的外边距，contentPadding用于调整内边距
                modifier = Modifier.padding(vertical = 24.dp),
                // 将callback传递给onClick事件
                onClick = onClickContinue
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, name = "Text Preview")
@Composable
fun DefaultPreview() {
    HelloComposeTheme {
        MyApp()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320, name = "OnBoarding Preview")
@Composable
fun OnBoardingScreenPreview() {
    HelloComposeTheme {
        OnBoardingScreen(onClickContinue = {})
    }
}
