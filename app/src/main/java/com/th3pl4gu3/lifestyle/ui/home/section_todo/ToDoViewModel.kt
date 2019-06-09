package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.ToDoOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.database.ToDoDao
import kotlinx.coroutines.*
import java.util.*

class ToDoViewModel(
    val database: LifestyleDatabase,
    application: Application) : AndroidViewModel(application) {


    private var _viewModelJob = Job()

    private val _uiScope = CoroutineScope(Dispatchers.Main + _viewModelJob)

    //Fetch all to dos from database
    var _toDos = ToDoOperations.getAllOffline(database)

    /**
     * Public functions that are accessible from the outside
     **/

    fun markAsDeleted(toDo: ToDo) {
        _uiScope.launch {
            remove(toDo)
        }
    }

    fun markAsCompleted(newToDo: ToDo) {
        _uiScope.launch {
            newToDo.markAsComplete()
            update(newToDo)
        }
    }

    fun markAsIncomplete(newToDo: ToDo) {
        _uiScope.launch {
            newToDo.markAsIncomplete()
            update(newToDo)
        }
    }

    /**
     * Private functions for internal use ONLY
     **/

    private suspend fun remove(toDo: ToDo) {
        withContext(Dispatchers.IO) {
            toDo.delete(database)
        }
    }

    private suspend fun update(newToDo: ToDo) {
        withContext(Dispatchers.IO) {
            newToDo.update(database)
        }
    }

    /**
     * Overridden functions
     **/
    override fun onCleared() {
        super.onCleared()

        //Clear the view model job when user leave
        _viewModelJob.cancel()
    }
}