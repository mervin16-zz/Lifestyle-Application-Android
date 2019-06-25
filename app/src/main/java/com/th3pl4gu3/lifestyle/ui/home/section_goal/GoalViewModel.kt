package com.th3pl4gu3.lifestyle.ui.home.section_goal

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.operations.GoalOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class GoalViewModel(
    val db: LifestyleDatabase,
    application: Application
) : LifestyleOpsViewModel(database = db, application = application) {

    //Application
    val app = getApplication<Application>()

    //Current state of the toggle button (Current button checked)
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

    //Fetch all goals from database
    private var _goals = GoalOperations.getAllOffline(database)
    val goalsMediatorLiveData = MediatorLiveData<List<Goal>>()


    private val _activeGoalsSize = MutableLiveData<Int>()

    val currentActiveButtonText: LiveData<String> = Transformations.map(_activeGoalsSize) { size ->
        if(size == null){
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Active)
        }else{
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Active_WithSize, size.toString())
        }
    }

    private val _completedGoalsSize = MutableLiveData<Int>()

    val currentCompletedButtonText: LiveData<String> = Transformations.map(_completedGoalsSize) { size ->
        if(size == null){
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Completed)
        }else{
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Completed_WithSize, size.toString())
        }
    }

    init {
        _activeGoalsSize.value = 0
        _completedGoalsSize.value = 0
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
                    val activeGoals = FilterOperations<Goal>(it).getActive()
                    goalsMediatorLiveData.value = activeGoals
                    _activeGoalsSize.value = activeGoals.size
                    _completedGoalsSize.value = null
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                goalsMediatorLiveData.addSource(_goals){
                    val completedGoals = FilterOperations<Goal>(it).getCompleted()
                    goalsMediatorLiveData.value = completedGoals
                    _completedGoalsSize.value = completedGoals.size
                    _activeGoalsSize.value = null
                }
            }
        }
    }


}