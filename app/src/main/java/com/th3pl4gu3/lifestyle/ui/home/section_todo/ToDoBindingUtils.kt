package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.Utils


@BindingAdapter("toDoTitle")
fun TextView.setToDoTitle(toDo: ToDo){
    toDo.let {
        text = toDo.title
    }
}

@BindingAdapter("toDoCategory")
fun TextView.setToDoCategory(toDo: ToDo){
    toDo.let {
        text = toDo.category
    }
}

@BindingAdapter("toDoDateAdded")
fun TextView.setToDoDateAdded(toDo: ToDo){
    toDo.let {
        text = Utils.dateToFormattedString(toDo.dateAdded)
    }
}

@BindingAdapter("toDoPriority")
fun ImageView.setToDoPriority(toDo: ToDo) {
    setImageResource(when (toDo.priority) {
        Priority.P1 -> R.drawable.ic_priority_1
        Priority.P2 -> R.drawable.ic_priority_2
        Priority.P3 -> R.drawable.ic_priority_3
        Priority.P4 -> R.drawable.ic_priority_4
    })
}