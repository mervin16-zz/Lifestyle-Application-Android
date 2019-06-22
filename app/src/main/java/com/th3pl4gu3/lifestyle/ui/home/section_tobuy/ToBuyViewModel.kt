package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.operations.ToBuyOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class ToBuyViewModel(
    val db: LifestyleDatabase,
    application: Application
) : LifestyleOpsViewModel(database = db, application = application)  {

    //Current state of the toggle button (Current button checked)
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

    //Fetch all To Buys from database
    private var _toBuys = ToBuyOperations.getAllOffline(database)
    val toBuysMediatorLiveData = MediatorLiveData<List<ToBuy>>()

    init {
        //Update the list of the recyclerview on INIT
        updateList(currentToggleButtonState)
    }

    /**
     * Public functions that are accessible from the outside
     **/
    fun updateList(toggleButton: ToggleButtonStates) {
        toBuysMediatorLiveData.removeSource(_toBuys)

        when(toggleButton){
            ToggleButtonStates.BUTTON_ALL ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ALL

                toBuysMediatorLiveData.addSource(_toBuys){
                    toBuysMediatorLiveData.value = it
                }
            }

            ToggleButtonStates.BUTTON_ACTIVE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

                toBuysMediatorLiveData.addSource(_toBuys){
                    toBuysMediatorLiveData.value = FilterOperations<ToBuy>(it).getActive()
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                toBuysMediatorLiveData.addSource(_toBuys){
                    toBuysMediatorLiveData.value = FilterOperations<ToBuy>(it).getCompleted()
                }
            }
        }
    }

}