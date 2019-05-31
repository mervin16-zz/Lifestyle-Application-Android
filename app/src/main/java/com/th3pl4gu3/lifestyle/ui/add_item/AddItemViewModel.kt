package com.th3pl4gu3.lifestyle.ui.add_item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import kotlinx.coroutines.*

class AddItemViewModel(
    private val database: LifestyleDatabase,
    application: Application) : AndroidViewModel(application) {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    private var mChosenPriority: Priority? = null

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _showSnackbarEvent = MutableLiveData<String>()
    private var _showToastEvent = MutableLiveData<String>()

    val showSnackBarEvent: LiveData<String>
        get() = _showSnackbarEvent

    val showToastEvent: LiveData<String>
        get() = _showToastEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    fun doneShowingToast() {
        _showToastEvent.value = null
    }

    fun setPriority(givenPriority: Int){
        Priority.values().forEach {priority ->
            if(givenPriority == priority.value){
                mChosenPriority = priority
            }
        }
    }

    fun addItem(itemType: String, itemTitle: String, itemCategory: String) {

        uiScope.launch {

            try{
                when(Utils.formattedStringToLifestyleEnum(itemType)){
                    LifestyleItem.GOAL ->{
                        //val goal = Goal(title = itemTitle, category = itemCategory)
                        //insert(goal)
                        _showToastEvent.value = "Goal object will be created."
                    }

                    LifestyleItem.TO_DO ->{
                        if(mChosenPriority == null){
                            _showSnackbarEvent.value = "Please choose a priority."
                            return@launch
                        }
                        _showToastEvent.value = "To Do object will be created."
                    }
                    LifestyleItem.TO_BUY ->{
                        if(mChosenPriority == null){
                            _showSnackbarEvent.value = "Please choose a priority."
                            return@launch
                        }
                        _showToastEvent.value = "To Buy object will be created."
                    }
                }

                // Show a snackbar message
                _showSnackbarEvent.value = "Item Added Successfully. Do you want to create another?."

            }catch (ex: Exception){
                _showToastEvent.value = ex.message.toString()
            }
        }
    }

    //Private Methods
    private suspend fun insert(goal: Goal) {
        withContext(Dispatchers.IO) {
            database.goalDao.insert(goal)
        }
    }

    private suspend fun insert(toDo: ToDo) {
        withContext(Dispatchers.IO) {
            database.toDoDao.insert(toDo)
        }
    }

    private suspend fun insert(toBuy: ToBuy) {
        withContext(Dispatchers.IO) {
            database.toBuyDao.insert(toBuy)
        }
    }
}