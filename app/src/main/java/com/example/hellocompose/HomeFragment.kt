package com.example.hellocompose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.hellocompose.todolist.TodoFragment
import com.example.hellocompose.ui.theme.HelloComposeTheme

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HelloComposeTheme {
                    Home(requireActivity())
                }
            }
        }
    }
}

enum class Screens(val screenName: String, val destination: Fragment) {
    LONG_CARD_LIST("LongCardList", LongCardListFragment()),
    MY_SCAFFOLD("MyScaffold", MyScaffoldFragment()),
    PHOTOGRAPHER_CARD("PhotographerCard", PhotographCardFragment()),
    TODO_LIST("TodoList", TodoFragment())
}

@Composable
fun Home(activity: FragmentActivity) {
    Surface {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Screens.values().forEach { screen -> NavigateButton(activity, screen) }
        }
    }
}

@Composable
fun NavigateButton(activity: FragmentActivity, screen: Screens) {
    Button(
        modifier = Modifier
            .padding(8.dp)
            .widthIn(min = 200.dp),
        onClick = {
            val fragmentManager = activity.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.content, screen.destination)
            transaction.addToBackStack(null)
            transaction.commit()
        }) {
        Text(text = screen.screenName)
    }
}
