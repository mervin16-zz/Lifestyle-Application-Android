package com.th3pl4gu3.lifestyle.ui.home.section_goal

import android.app.Application
import androidx.lifecycle.MediatorLiveData
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


}