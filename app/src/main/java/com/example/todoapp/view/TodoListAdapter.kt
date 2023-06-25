package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoItemLayoutBinding
import com.example.todoapp.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>,
                      val adapterOnClick : (Todo) -> Unit)
    : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>(), TodoItemLayoutInterface {
    class TodoListViewHolder(var view: TodoItemLayoutBinding): RecyclerView.ViewHolder(view.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TodoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = TodoItemLayoutBinding.inflate(inflater, parent, false)
        return TodoListViewHolder(view)
    }


    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int)
    {
        holder.view.todo = todoList[position]
        holder.view.checkListener = this
        holder.view.editListener = this
        holder.view.checkTask.isChecked = false
        /* var checktask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checktask.text = todoList[position].title
        checktask.setOnCheckedChangeListener { compoundButton, b ->
            adapterOnClick(todoList[position])
        }

        val imgEdit = holder.view.findViewById<ImageView>(R.id.imgEdit)
        imgEdit.setOnClickListener {
            val action = TodoListFragmentDirections.actionEditToDo(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        */
    }
    override fun getItemCount(): Int { return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if (isChecked){
            adapterOnClick(obj)
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditToDo(uuid)
        Navigation.findNavController(v).navigate(action)
    }

}