package com.example.hellocompose.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    // use LiveData to store state
    private val _todoItems = MutableLiveData<List<TodoItem>>()
    var todoItems = _todoItems

    fun addItem(item: TodoItem) {

    }

    fun removeItem(item: TodoItem) {

    }
}