package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.operations.Filter
import com.th3pl4gu3.lifestyle.core.operations.ToBuyOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.enums.ToggleButtonStates
import kotlinx.coroutines.*

class ToBuyViewModel(
    val database: LifestyleDatabase,
    application: Application
) : AndroidViewModel(application) {

    private var _viewModelJob = Job()

    private val _uiScope = CoroutineScope(Dispatchers.Main + _viewModelJob)

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
                    toBuysMediatorLiveData.value = Filter<ToBuy>(it).getActive()
                }
            }
            ToggleButtonStates.BUTTON_COMPLETE ->{
                currentToggleButtonState = ToggleButtonStates.BUTTON_COMPLETE

                toBuysMediatorLiveData.addSource(_toBuys){
                    toBuysMediatorLiveData.value = Filter<ToBuy>(it).getCompleted()
                }
            }
        }
    }

    fun insertItem(toBuy: ToBuy) {
        _uiScope.launch {
            insert(toBuy)
        }
    }

    fun markAsDeleted(toBuy: ToBuy) {
        _uiScope.launch {
            remove(toBuy)
        }
    }

    fun markItem(toBuy: ToBuy){
        if(toBuy.dateCompleted == null){
            markAsCompleted(toBuy)
        }else{
            markAsIncomplete(toBuy)
        }
    }


    /**
     * Private functions for internal use ONLY
     **/

    private fun markAsCompleted(newToBuy: ToBuy) {
        _uiScope.launch {
            newToBuy.markAsComplete()
            update(newToBuy)
        }
    }

    private fun markAsIncomplete(newToBuy: ToBuy) {
        _uiScope.launch {
            newToBuy.markAsIncomplete()
            update(newToBuy)
        }
    }

    private suspend fun insert(toBuy: ToBuy) {
        withContext(Dispatchers.IO) {
            toBuy.add(database)
        }
    }

    private suspend fun remove(toBuy: ToBuy) {
        withContext(Dispatchers.IO) {
            toBuy.delete(database)
        }
    }

    private suspend fun update(newToBuy: ToBuy) {
        withContext(Dispatchers.IO) {
            newToBuy.update(database)
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