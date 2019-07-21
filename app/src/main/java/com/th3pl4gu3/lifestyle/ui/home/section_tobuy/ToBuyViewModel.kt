package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class ToBuyViewModel(
    val db: LifestyleDatabase,
    application: Application
) : LifestyleOpsViewModel(database = db, application = application)  {

    /**
     * Private Global Variables
     **/
    private val _activeToBuySize = MutableLiveData<Int>()
    private val _completedToBuySize = MutableLiveData<Int>()


    /**
     * Public Global Variables
     **/
    var currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE //Current state of the toggle button (Current button checked)
    val toBuysMediatorLiveData = MediatorLiveData<List<ToBuy>>() //Fetch all tobuys from database
    var sort: Sort<ToBuy> = Sort()
        set(value) {
            field = value
            filterToBuys()
        }

    val currentActiveButtonText: LiveData<String> = Transformations.map(_activeToBuySize) { size ->
        if (size == null) {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Active)
        } else {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Active_WithSize, size.toString())
        }
    }

    val currentCompletedButtonText: LiveData<String> = Transformations.map(_completedToBuySize) { size ->
        if (size == null) {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Completed)
        } else {
            application.getString(R.string.Button_forLifestyleList_ToggleButton_Completed_WithSize, size.toString())
        }
    }

    init {
        _activeToBuySize.value = 0
        _completedToBuySize.value = 0
    }

    /**
     * Public functions that are accessible from the outside
     **/

    fun updateList(toggleButton: ToggleButtonStates) {

        toBuysMediatorLiveData.removeSource(toBuys)

        when (toggleButton) {
            ToggleButtonStates.BUTTON_ALL -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_ALL

                toBuysMediatorLiveData.addSource(toBuys) {
                    addAllToBuysToSource(it)
                }
            }

            ToggleButtonStates.BUTTON_ACTIVE -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_ACTIVE

                toBuysMediatorLiveData.addSource(toBuys) {
                    addActiveToBuysToSource(it)
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE -> {
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                toBuysMediatorLiveData.addSource(toBuys) {
                    addCompletedToBuysToSource(it)
                }
            }
        }
    }


    /**
     * Private functions for internal use ONLY
     **/
    private fun filterToBuys() {

        toBuysMediatorLiveData.removeSource(toBuys)

        toBuysMediatorLiveData.addSource(toBuys) {

            when(currentToggleButtonState){
                ToggleButtonStates.BUTTON_ALL -> {
                    addAllToBuysToSource(it)
                }

                ToggleButtonStates.BUTTON_ACTIVE -> {
                    addActiveToBuysToSource(it)
                }

                ToggleButtonStates.BUTTON_COMPLETE -> {
                    addCompletedToBuysToSource(it)
                }
            }


        }
    }

    private fun addActiveToBuysToSource(toBuys: List<ToBuy>){
        sort.list = FilterOperations<ToBuy>(toBuys).getActive()
        toBuysMediatorLiveData.value = sort.getSortedList()

        _activeToBuySize.value = sort.list.size
        _completedToBuySize.value = null
    }

    private fun addCompletedToBuysToSource(toBuys: List<ToBuy>){
        sort.list = FilterOperations<ToBuy>(toBuys).getCompleted()
        toBuysMediatorLiveData.value = sort.getSortedList()

        _activeToBuySize.value = null
        _completedToBuySize.value = sort.list.size
    }

    private fun addAllToBuysToSource(toBuys: List<ToBuy>){
        sort.list = toBuys
        toBuysMediatorLiveData.value = sort.getSortedList()
    }
}