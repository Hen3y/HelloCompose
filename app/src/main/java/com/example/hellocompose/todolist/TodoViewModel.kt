package com.example.hellocompose.todolist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    // MutableState<T> 与 MutableLiveData<T> 类似，但已与 Compose 运行时集成。
    // 由于它是可观察对象，所以每次更新时，它都会告知 Compose，因此 Compose 可以重组读取对象的所有可组合项。
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
    }
}
