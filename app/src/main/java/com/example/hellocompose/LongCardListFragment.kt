package com.example.hellocompose

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.hellocompose.ui.theme.HelloComposeTheme
import kotlinx.coroutines.launch

private const val LIST_SIZE = 50

class LongCardListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HelloComposeTheme {
                    LongCardList()
                }
            }
        }
    }
}

@Composable
fun LongCardList() {
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
        Greetings(names = List(LIST_SIZE) { "$it" })
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardContent(name: String) {
    // 使用mutableStateOf()创建一个state
    // 用remember在多次recomposition间保存state的值

    // val expanded = remember(calculation = { -> mutableStateOf(false) })
    // line above could be simplified as below
    // val expanded = remember { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    /*
        Any animation created with animate*AsState is interruptible.
        This means that if the target value changes in the middle of the animation,
        animate*AsState restarts the animation and points to the new value.
     */
//    val extraPadding by animateDpAsState(
//        targetValue = if (expanded.value) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )

    Row(
        modifier = Modifier
            .padding(12.dp)
            // 允许父级modifier监听子modifier的size连续变化从而产生动画效果
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
                    contentDescription = "Android Logo",
                    modifier = Modifier.size(48.dp)
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = "Hello,")
                    // copy() allows to customize existing predefined styles
                    Text(
                        text = "Index: $name",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }
            if (expanded) {
                Text(text = "Congratulations! You learned the basics of Compose! ".repeat(3))
            }
        }
//        OutlinedButton(
//            onClick = {
//                expanded.value = !expanded.value
//            }) {
//            Text(text = if (expanded.value) "Show less" else "Show more")
//        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    // change Surface to Material Design Card
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name = name)
    }
}

@Composable
fun ScrollControlButton(scrollState: LazyListState, index: Int) {
    // We save the coroutine scope where our animated scroll will be executed
    // 为避免在滚动时阻止列表呈现，滚动 API 属于挂起函数。因此，我们需要在协程中调用它们
    val coroutineScope = rememberCoroutineScope()

    Button(
        modifier = Modifier.padding(8.dp),
        onClick = {
            coroutineScope.launch {
                // scroll to item by index with animation
                scrollState.animateScrollToItem(index)
            }
        }) {
        Text(text = if (index == 0) "Go to Top" else "Go to Bottom")
    }
}

@Composable
fun Greetings(names: List<String>) {
    // We save the scrolling position with this state that can also be used to programmatically scroll the list
    // 记录滚动位置
    val scrollState = rememberLazyListState()

    Column {
        // control buttons
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            ScrollControlButton(scrollState, 0)
            ScrollControlButton(scrollState, LIST_SIZE - 1)
        }

        // use LazyColumn/LazyRow for large size list or collection to guarantee app performance
        LazyColumn(state = scrollState) {
            items(items = names) { name ->
                Greeting(name = name)
            }
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

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultDarkPreview"
)
@Preview(showBackground = true, widthDp = 320, name = "DefaultLightPreview")
@Composable
fun DefaultPreview() {
    HelloComposeTheme {
        Greetings(names = List(LIST_SIZE) { "$it" })
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320, name = "OnBoarding Preview")
@Composable
fun OnBoardingScreenPreview() {
    HelloComposeTheme {
        OnBoardingScreen(onClickContinue = {})
    }
}
