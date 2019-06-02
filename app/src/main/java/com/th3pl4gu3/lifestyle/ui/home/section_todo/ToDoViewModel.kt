package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.th3pl4gu3.lifestyle.database.ToDoDao

class ToDoViewModel(
    val database: ToDoDao,
    application: Application) : AndroidViewModel(application) {

    val toDos = database.getAllToDos()
}