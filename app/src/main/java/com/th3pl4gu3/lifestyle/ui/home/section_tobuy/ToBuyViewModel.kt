package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.th3pl4gu3.lifestyle.R
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

    //Application
    val app = getApplication<Application>()

    //Current state of the toggle button (Current button checked)
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

    //Fetch all To Buys from database
    private var _toBuys = ToBuyOperations.getAllOffline(database)
    val toBuysMediatorLiveData = MediatorLiveData<List<ToBuy>>()

    private val _activeToBuySize = MutableLiveData<Int>()

    val currentActiveButtonText: LiveData<String> = Transformations.map(_activeToBuySize) { size ->
        if(size == null){
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Active)
        }else{
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Active_WithSize, size.toString())
        }
    }

    private val _completedToBuySize = MutableLiveData<Int>()

    val currentCompletedButtonText: LiveData<String> = Transformations.map(_completedToBuySize) { size ->
        if(size == null){
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Completed)
        }else{
            app.getString(R.string.Button_forLifestyleList_ToggleButton_Completed_WithSize, size.toString())
        }
    }

    init {
        _activeToBuySize.value = 0
        _completedToBuySize.value = 0
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
                    val activeToBuys = FilterOperations<ToBuy>(it).getActive()
                    toBuysMediatorLiveData.value = activeToBuys
                    _activeToBuySize.value = activeToBuys.size
                    _completedToBuySize.value = null
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                toBuysMediatorLiveData.addSource(_toBuys){
                    val completedToBuys = FilterOperations<ToBuy>(it).getCompleted()
                    toBuysMediatorLiveData.value = completedToBuys
                    _completedToBuySize.value = completedToBuys.size
                    _activeToBuySize.value = null
                }
            }
        }
    }

}