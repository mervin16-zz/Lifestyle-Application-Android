package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.*
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class ToDoViewModel(
    val db: LifestyleDatabase,
    application: Application) : LifestyleOpsViewModel(database = db, application = application) {

    //Application
    val app = getApplication<Application>()

    //Current state of the toggle button (Current button checked)
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

    //Fetch all to dos from database
    val toDosMediatorLiveData = MediatorLiveData<List<ToDo>>()

    private val _activeToDoSize = MutableLiveData<Int>()

    val currentActiveButtonText: LiveData<String> = Transformations.map(_activeToDoSize) { size ->
        if(size == null){
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Active)
        }else{
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Active_WithSize, size.toString())
        }
    }

    private val _completedToDoSize = MutableLiveData<Int>()

    val currentCompletedButtonText: LiveData<String> = Transformations.map(_completedToDoSize) { size ->
        if(size == null){
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Completed)
        }else{
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Completed_WithSize, size.toString())
        }
    }

    init {
        _activeToDoSize.value = 0
        _completedToDoSize.value = 0
        //Update the list of the recyclerview on INIT
        updateList(currentToggleButtonState)
    }


    /**
     * Public functions that are accessible from the outside
     **/

    fun updateList(toggleButton: ToggleButtonStates) {
        toDosMediatorLiveData.removeSource(toDos)

        when(toggleButton){
            ToggleButtonStates.BUTTON_ALL ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ALL

                toDosMediatorLiveData.addSource(toDos){
                    toDosMediatorLiveData.value = it
                }
            }

            ToggleButtonStates.BUTTON_ACTIVE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

                toDosMediatorLiveData.addSource(toDos){
                    val activeToDo = FilterOperations<ToDo>(it).getActive()
                    toDosMediatorLiveData.value = activeToDo
                    _activeToDoSize.value = activeToDo.size
                    _completedToDoSize.value = null
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                toDosMediatorLiveData.addSource(toDos){
                    val completedToDo = FilterOperations<ToDo>(it).getCompleted()
                    toDosMediatorLiveData.value = completedToDo
                    _completedToDoSize.value = completedToDo.size
                    _activeToDoSize.value = null
                }
            }
        }
    }
}