package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.database.ToDoDao
import kotlinx.coroutines.*
import java.util.*

class ToDoViewModel(
    val database: ToDoDao,
    application: Application) : AndroidViewModel(application) {


    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val toDos = database.getAllToDos()

    fun markAsDeleted(id: String) {
        uiScope.launch {
            remove(id)
        }
    }

    fun markAsCompleted(newToDo: ToDo) {
        uiScope.launch {
            newToDo.dateCompleted = Calendar.getInstance()
            update(newToDo)
        }
    }

    //Private methods
    private suspend fun remove(id: String) {
        withContext(Dispatchers.IO) {
            database.remove(id)
        }
    }

    private suspend fun update(newToDo: ToDo) {
        withContext(Dispatchers.IO) {
            database.update(newToDo)
        }
    }


    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}