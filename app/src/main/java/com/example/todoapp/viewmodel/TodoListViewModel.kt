package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import com.example.todoapp.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TodoListViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectAllTodoExceptIsDone())
        }
    }

    fun clearTask(todo: Todo) {
        launch {
            val db = buildDB(getApplication())
//            db.todoDao().deleteTodo(todo)
            db.todoDao().updateTodo(todo.title, todo.notes, todo.priority, 1, todo.uuid)
            todoLD.postValue(db.todoDao().selectAllTodoExceptIsDone())
        }
    }
}