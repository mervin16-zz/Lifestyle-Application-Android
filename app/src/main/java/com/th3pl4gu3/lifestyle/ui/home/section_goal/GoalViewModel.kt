package com.th3pl4gu3.lifestyle.ui.home.section_goal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.operations.GoalOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import kotlinx.coroutines.*

class GoalViewModel(
    val database: LifestyleDatabase,
    application: Application
) : AndroidViewModel(application) {

    private var _viewModelJob = Job()

    private val _uiScope = CoroutineScope(Dispatchers.Main + _viewModelJob)

    //Current state of the toggle button (Current button checked)
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

    //Fetch all goals from database
    private var _goals = GoalOperations.getAllOffline(database)
    val goalsMediatorLiveData = MediatorLiveData<List<Goal>>()

    init {
        //Update the list of the recyclerview on INIT
        updateList(currentToggleButtonState)
    }


    /**
     * Public functions that are accessible from the outside
     **/
    fun updateList(toggleButton: ToggleButtonStates) {
        goalsMediatorLiveData.removeSource(_goals)

        when(toggleButton){
            ToggleButtonStates.BUTTON_ALL ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ALL

                goalsMediatorLiveData.addSource(_goals){
                    goalsMediatorLiveData.value = it
                }
            }

            ToggleButtonStates.BUTTON_ACTIVE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

                goalsMediatorLiveData.addSource(_goals){
                    goalsMediatorLiveData.value = FilterOperations<Goal>(it).getActive()
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                goalsMediatorLiveData.addSource(_goals){
                    goalsMediatorLiveData.value = FilterOperations<Goal>(it).getCompleted()
                }
            }
        }
    }

    fun insertItem(goal: Goal) {
        _uiScope.launch {
            insert(goal)
        }
    }

    fun markAsDeleted(goal: Goal) {
        _uiScope.launch {
            remove(goal)
        }
    }

    fun markItem(goal: Goal){
        if(goal.dateCompleted == null){
            markAsCompleted(goal)
        }else{
            markAsIncomplete(goal)
        }
    }


    /**
     * Private functions for internal use ONLY
     **/

    private fun markAsCompleted(newGoal: Goal) {
        _uiScope.launch {
            newGoal.markAsComplete()
            update(newGoal)
        }
    }

    private fun markAsIncomplete(newGoal: Goal) {
        _uiScope.launch {
            newGoal.markAsIncomplete()
            update(newGoal)
        }
    }

    private suspend fun insert(newGoal: Goal) {
        withContext(Dispatchers.IO) {
            newGoal.add(database)
        }
    }

    private suspend fun remove(goal: Goal) {
        withContext(Dispatchers.IO) {
            goal.delete(database)
        }
    }

    private suspend fun update(newGoal: Goal) {
        withContext(Dispatchers.IO) {
            newGoal.update(database)
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