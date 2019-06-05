package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.databinding.CustomRecyclerviewListTodoBinding

class ToDoAdapter : ListAdapter<ToDo, ToDoAdapter.ViewHolder>(ToDoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDo = getItem(position)
        holder.bind(toDo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CustomRecyclerviewListTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(toDo: ToDo) {
            binding.myToDo = toDo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerviewListTodoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ToDoDiffCallback: DiffUtil.ItemCallback<ToDo>() {

    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem == newItem
    }

}

