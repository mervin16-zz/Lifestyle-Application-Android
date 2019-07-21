package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.*
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class ToDoViewModel(
    val db: LifestyleDatabase,
    application: Application) : LifestyleOpsViewModel(database = db, application = application) {

    /**
     * Private Global Variables
     **/
    private val _activeToDoSize = MutableLiveData<Int>()
    private val _completedToDoSize = MutableLiveData<Int>()


    /**
     * Public Global Variables
     **/
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE //Current state of the toggle button (Current button checked)
    val toDosMediatorLiveData = MediatorLiveData<List<ToDo>>() //Fetch all todos from database
    var sort: Sort<ToDo> = Sort()
        set(value) {
            field = value
            filterToDos()
        }

    val currentActiveButtonText: LiveData<String> = Transformations.map(_activeToDoSize) { size ->
        if (size == null) {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Active)
        } else {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Active_WithSize, size.toString())
        }
    }

    val currentCompletedButtonText: LiveData<String> = Transformations.map(_completedToDoSize) { size ->
        if (size == null) {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Completed)
        } else {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Completed_WithSize, size.toString())
        }
    }

    init {
        _activeToDoSize.value = 0
        _completedToDoSize.value = 0
    }

    /**
     * Public functions that are accessible from the outside
     **/

    fun updateList(toggleButton: ToggleButtonStates) {

        toDosMediatorLiveData.removeSource(toDos)

        when (toggleButton) {
            ToggleButtonStates.BUTTON_ALL -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_ALL

                toDosMediatorLiveData.addSource(toDos) {
                    addAllToDosToSource(it)
                }
            }

            ToggleButtonStates.BUTTON_ACTIVE -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

                toDosMediatorLiveData.addSource(toDos) {
                    addActiveToDosToSource(it)
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                toDosMediatorLiveData.addSource(toDos) {
                    addCompletedToDosToSource(it)
                }
            }
        }
    }


    /**
     * Private functions for internal use ONLY
     **/
    private fun filterToDos() {

        toDosMediatorLiveData.removeSource(toDos)

        toDosMediatorLiveData.addSource(toDos) {

            when(currentToggleButtonState){
                ToggleButtonStates.BUTTON_ALL -> {
                    addAllToDosToSource(it)
                }

                ToggleButtonStates.BUTTON_ACTIVE -> {
                    addActiveToDosToSource(it)
                }

                ToggleButtonStates.BUTTON_COMPLETE -> {
                    addCompletedToDosToSource(it)
                }
            }


        }
    }

    private fun addActiveToDosToSource(toDos: List<ToDo>){
        sort.list = FilterOperations<ToDo>(toDos).getActive()
        toDosMediatorLiveData.value = sort.getSortedList()

        _activeToDoSize.value = sort.list.size
        _completedToDoSize.value = null
    }

    private fun addCompletedToDosToSource(toDos: List<ToDo>){
        sort.list = FilterOperations<ToDo>(toDos).getCompleted()
        toDosMediatorLiveData.value = sort.getSortedList()

        _activeToDoSize.value = null
        _completedToDoSize.value = sort.list.size
    }

    private fun addAllToDosToSource(toDos: List<ToDo>){
        sort.list = toDos
        toDosMediatorLiveData.value = sort.getSortedList()
    }
}