package com.example.hellocompose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class HelloComposeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HelloComposeScreen()
            }
        }
    }
}

@Composable
fun HelloComposeScreen() {
    ModifierExample()
//    LayoutExample()
}

@Composable
fun ModifierExample() {
    Column {
        // Modifier.padding VS contentPadding
        Button(
            modifier = Modifier
                .background(Color.Gray)
                .padding(16.dp),
            contentPadding = PaddingValues(8.dp),
            onClick = { /* */ }
        ) {
            Text(text = "Button")
        }

        Spacer(modifier = Modifier.size(20.dp))

        // Modifier.weight
        Row() {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color.Black),
                text = "Long Long Long Text Long Long Long Text Long Long Long Text Long Long Long Text Long Long Long Text"
            )
            Text(
                modifier = Modifier
                    .weight(2f)
                    .border(1.dp, Color.Black),
                text = "Jetpack Compose Awesome"
            )
            FilledBlock()
        }

        Spacer(modifier = Modifier.size(20.dp))

        // matchParentSize in BoxScope
        Box {
            FilledBlock(color = Color.LightGray)
            Text(
                modifier = Modifier
//                    .fillMaxWidth(),
                    .matchParentSize(),
                text = "Long Long Long Text Long Long Long Text Long Long Long Text Long Long Long Text Long Long Long Text"
            )
        }
    }
}

@Composable
fun LayoutExample() {
    Text(text = "The Text will be covered The Text will be covered")
    // Column and Row -> LinearLayout
    Column() {
        FilledBlock()

        Spacer(modifier = Modifier.size(10.dp))

        // Box -> FrameLayout，允许重叠
        Box {
            FilledBlock(color = Color.Green)
            FilledBlock(color = Color.Blue.copy(alpha = 1f))
        }

        Spacer(modifier = Modifier.size(10.dp))

        // Column and Row 默认不可滚动，最后一个蓝色看不到
        Row {
            FilledBlock(color = Color.Cyan)
            FilledBlock(color = Color.Green)
            FilledBlock(color = Color.Yellow)
            FilledBlock(color = Color.Red)
            FilledBlock()
        }

        Spacer(modifier = Modifier.size(20.dp))

        // 水平 垂直对齐
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FilledBlock()
            FilledBlock(Modifier.size(80.dp), Color.Green)
            FilledBlock(Modifier.size(120.dp), Color.Red)
        }
    }
}

@Composable
fun FilledBlock(modifier: Modifier = Modifier, color: Color = Color.Blue) {
    Box(
        modifier = modifier
            .background(color)
            .size(100.dp)
    ) { }
}

@Preview(showBackground = true)
@Composable
fun HelloComposeFragmentPreview() {
    HelloComposeScreen()
}
