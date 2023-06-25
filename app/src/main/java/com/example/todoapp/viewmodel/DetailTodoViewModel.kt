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

class DetailTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val todoLD = MutableLiveData<Todo>()
    fun addTodo(list: List<Todo>) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }

    fun fetchTodo(uuid:Int){
        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }

    fun updateTodo(title:String, notes:String, priority:Int, uuid: Int, isDone: Int){
        launch {
            val db = buildDB(getApplication())
            db.todoDao().updateTodo(title, notes, priority, uuid, isDone)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}