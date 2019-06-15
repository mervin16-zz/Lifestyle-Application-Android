package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.*
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.operations.ToDoOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import kotlinx.coroutines.*

class ToDoViewModel(
    val database: LifestyleDatabase,
    application: Application) : AndroidViewModel(application) {

    private var _viewModelJob = Job()

    private val _uiScope = CoroutineScope(Dispatchers.Main + _viewModelJob)

    //Current state of the toggle button (Current button checked)
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

    //Fetch all to dos from database
    private var _toDos = ToDoOperations.getAllOffline(database)
    val toDosMediatorLiveData = MediatorLiveData<List<ToDo>>()

    init {
        //Update the list of the recyclerview on INIT
        updateList(currentToggleButtonState)
    }


    /**
     * Public functions that are accessible from the outside
     **/

    fun updateList(toggleButton: ToggleButtonStates) {
        toDosMediatorLiveData.removeSource(_toDos)

        when(toggleButton){
            ToggleButtonStates.BUTTON_ALL ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ALL

                toDosMediatorLiveData.addSource(_toDos){
                    toDosMediatorLiveData.value = it
                }
            }

            ToggleButtonStates.BUTTON_ACTIVE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

                toDosMediatorLiveData.addSource(_toDos){
                    toDosMediatorLiveData.value = FilterOperations<ToDo>(it).getActive()
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                toDosMediatorLiveData.addSource(_toDos){
                    toDosMediatorLiveData.value = FilterOperations<ToDo>(it).getCompleted()
                }
            }
        }
    }

    fun insertItem(toDo: ToDo) {
        _uiScope.launch {
            insert(toDo)
        }
    }

    fun markAsDeleted(toDo: ToDo) {
        _uiScope.launch {
            remove(toDo)
        }
    }

    fun markItem(toDo: ToDo){
        if(toDo.dateCompleted == null){
            markAsCompleted(toDo)
        }else{
            markAsIncomplete(toDo)
        }
    }


    /**
     * Private functions for internal use ONLY
     **/

    private fun markAsCompleted(newToDo: ToDo) {
        _uiScope.launch {
            newToDo.markAsComplete()
            update(newToDo)
        }
    }

    private fun markAsIncomplete(newToDo: ToDo) {
        _uiScope.launch {
            newToDo.markAsIncomplete()
            update(newToDo)
        }
    }

    private suspend fun insert(toDo: ToDo) {
        withContext(Dispatchers.IO) {
            toDo.add(database)
        }
    }

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