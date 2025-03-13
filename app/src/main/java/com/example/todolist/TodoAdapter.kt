package com.example.todolist

import Todo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TodoAdapter(private val todoList: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoTextView: TextView = itemView.findViewById(R.id.todoTextView)
        val tickImageView: ImageView = itemView.findViewById(R.id.tickImageView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.todoTextView.text = currentItem.title

        if (currentItem.checked) {
            holder.tickImageView.setImageResource(R.drawable.ic_checked)
        } else {
            holder.tickImageView.setImageResource(R.drawable.ic_unchecked)
        }

        holder.tickImageView.setOnClickListener {
            currentItem.checked = !currentItem.checked
            notifyItemChanged(position)
        }

        holder.deleteButton.setOnClickListener {
            todoList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = todoList.size
}