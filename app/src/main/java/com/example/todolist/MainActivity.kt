package com.example.todolist

import Todo
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var todoList: MutableList<Todo>
    private lateinit var adapter: TodoAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("todo_prefs", MODE_PRIVATE)
        loadTodoList() // Load saved data

        adapter = TodoAdapter(todoList)

        val recyclerView = findViewById<RecyclerView>(R.id.todoRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val editText = findViewById<EditText>(R.id.todoEditText)
        val addButton = findViewById<Button>(R.id.addButton)

        addButton.setOnClickListener {
            val task = editText.text.toString()
            if (task.isNotEmpty()) {
                todoList.add(Todo(task))
                adapter.notifyItemInserted(todoList.size - 1)
                editText.text.clear()
                saveTodoList() // Save the updated list
            }
        }
    }

    private fun saveTodoList() {
        val editor = sharedPreferences.edit()
        val todoJsonList = todoList.map { it.toJson() }
        val gson = Gson()
        val json = gson.toJson(todoJsonList)
        editor.putString("todo_list", json)
        editor.apply()
    }

    private fun loadTodoList() {
        val json = sharedPreferences.getString("todo_list", null)
        if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<List<String>>() {}.type
            val todoJsonList: List<String> = gson.fromJson(json, type)
            todoList = todoJsonList.map { Todo.fromJson(it) }.toMutableList()
        } else {
            todoList = mutableListOf()
        }
    }

    override fun onPause() {
        super.onPause()
        saveTodoList()
    }

}