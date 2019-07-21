package com.th3pl4gu3.lifestyle.ui.home.section_goal

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class GoalViewModel(
    val db: LifestyleDatabase,
    application: Application
) : LifestyleOpsViewModel(database = db, application = application) {


    /**
     * Private Global Variables
     **/
    private val _activeGoalsSize = MutableLiveData<Int>()
    private val _completedGoalsSize = MutableLiveData<Int>()


    /**
     * Public Global Variables
     **/
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE //Current state of the toggle button (Current button checked)
    val goalsMediatorLiveData = MediatorLiveData<List<Goal>>() //Fetch all goals from database
    var sort: Sort<Goal> = Sort()
        set(value) {
            field = value
            filterGoals()
        }

    val currentActiveButtonText: LiveData<String> = Transformations.map(_activeGoalsSize) { size ->
        if (size == null) {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Active)
        } else {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Active_WithSize, size.toString())
        }
    }

    val currentCompletedButtonText: LiveData<String> = Transformations.map(_completedGoalsSize) { size ->
        if (size == null) {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Completed)
        } else {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Completed_WithSize, size.toString())
        }
    }

    init {
        _activeGoalsSize.value = 0
        _completedGoalsSize.value = 0
    }

    /**
     * Public functions that are accessible from the outside
     **/

    fun updateList(toggleButton: ToggleButtonStates) {

        goalsMediatorLiveData.removeSource(goals)

        when (toggleButton) {
            ToggleButtonStates.BUTTON_ALL -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_ALL

                goalsMediatorLiveData.addSource(goals) {
                    addAllGoalsToSource(it)
                }
            }

            ToggleButtonStates.BUTTON_ACTIVE -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

                goalsMediatorLiveData.addSource(goals) {
                    addActiveGoalsToSource(it)
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                goalsMediatorLiveData.addSource(goals) {
                    addCompletedGoalsToSource(it)
                }
            }
        }
    }


    /**
     * Private functions for internal use ONLY
     **/
    private fun filterGoals() {

        goalsMediatorLiveData.removeSource(goals)

        goalsMediatorLiveData.addSource(goals) {

            when(currentToggleButtonState){
                ToggleButtonStates.BUTTON_ALL -> {
                    addAllGoalsToSource(it)
                }

                ToggleButtonStates.BUTTON_ACTIVE -> {
                    addActiveGoalsToSource(it)
                }

                ToggleButtonStates.BUTTON_COMPLETE -> {
                    addCompletedGoalsToSource(it)
                }
            }


        }
    }

    private fun addActiveGoalsToSource(goals: List<Goal>){
        sort.list = FilterOperations<Goal>(goals).getActive()
        goalsMediatorLiveData.value = sort.getSortedList()

        _activeGoalsSize.value = sort.list.size
        _completedGoalsSize.value = null
    }

    private fun addCompletedGoalsToSource(goals: List<Goal>){
        sort.list = FilterOperations<Goal>(goals).getCompleted()
        goalsMediatorLiveData.value = sort.getSortedList()

        _activeGoalsSize.value = null
        _completedGoalsSize.value = sort.list.size
    }

    private fun addAllGoalsToSource(goals: List<Goal>){
        sort.list = goals
        goalsMediatorLiveData.value = sort.getSortedList()
    }
}