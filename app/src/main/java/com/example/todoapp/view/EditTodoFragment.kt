package com.example.todoapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTodoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var viewModel:DetailTodoViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetchTodo(uuid)
        observeViewModel()
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.text = "Save Edited Todo"
        btnAdd.setOnClickListener {
            val txtTitle = view.findViewById<EditText>(R.id.txtToDoTitle)
            val txtNotes = view.findViewById<EditText>(R.id.txtToDoNotes)
            val radPriority = view.findViewById<RadioGroup>(R.id.radioGPriority)
            val radio = view.findViewById<RadioButton>(radPriority.checkedRadioButtonId)
            var todo = Todo(txtTitle.text.toString(), txtNotes.text.toString(), radio.tag.toString().toInt(), 0)
            val list = listOf(todo)
            Log.d("hello", radio.tag.toString())
            viewModel.updateTodo(txtTitle.text.toString(), txtNotes.text.toString(), radio.tag.toString().toInt(), uuid, 0)
            Toast.makeText(view.context, "Data edited", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            var txtViewTitle = view?.findViewById<TextView>(R.id.txtViewToDoTitle)
            var txtTitle = view?.findViewById<TextInputEditText>(R.id.txtToDoTitle)
            var txtNotes = view?.findViewById<TextInputEditText>(R.id.txtToDoNotes)
            txtTitle?.setText(it.title)
            txtViewTitle?.text = it.title
            txtNotes?.setText(it.notes)
            when (it.priority){
                1-> view?.findViewById<RadioButton>(R.id.radioLow)?.isChecked = true
                2-> view?.findViewById<RadioButton>(R.id.radioMedium)?.isChecked = true
                else -> view?.findViewById<RadioButton>(R.id.radioHigh)?.isChecked = true
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditTodoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditTodoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}