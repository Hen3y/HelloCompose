package com.example.hellocompose.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hellocompose.ui.theme.HelloComposeTheme

class TodoFragment : Fragment() {

    // 声明一ViewModel
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HelloComposeTheme {
                    TodoScreen(todoViewModel)
                }
            }
        }
    }

    // 在onCreateView之后运行
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    // 在onCreateView之前运行
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
    }
}

@Composable
fun TodoScreen(todoViewModel: TodoViewModel) {
    TodoList(
        items = todoViewModel.todoItems,
        onAddItem = { todoViewModel.addItem(it) },
        onRemoveItem = { todoViewModel.removeItem(it) })
}

@Composable
private fun TodoList(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Surface {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(items) { item ->
                    TodoRow(item) { onAddItem(item) }
                }
            }
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = { onAddItem(TodoItem("Test")) }) {
                Text(
                    fontSize = MaterialTheme.typography.body1.copy(fontSize = 24.sp).fontSize,
                    text = "Add An Item"
                )
            }
        }
    }
}

@Composable
private fun TodoRow(
    item: TodoItem,
    modifier: Modifier = Modifier,
    onItemClicked: (TodoItem) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onItemClicked(item) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            fontSize = MaterialTheme.typography.body1.copy(fontSize = 24.sp).fontSize,
            text = item.task
        )
        Icon(
            imageVector = item.icon.imageVector,
            contentDescription = stringResource(id = item.icon.contentDescription)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoScreenPreview() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoList(items, {}, {})
}
