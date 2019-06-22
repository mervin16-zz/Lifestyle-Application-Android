package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.*
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.operations.ToDoOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class ToDoViewModel(
    val db: LifestyleDatabase,
    application: Application) : LifestyleOpsViewModel(database = db, application = application) {

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
}