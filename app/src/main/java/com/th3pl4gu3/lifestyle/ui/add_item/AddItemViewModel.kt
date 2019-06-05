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

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //Screen Messages
    private var _showSnackbarEvent = MutableLiveData<String>()
    private var _showToastEvent = MutableLiveData<String>()

    //Edit Text Error Messages
    private var _errorMessageForItemTitle = MutableLiveData<String>()
    private var _errorMessageForItemPrice = MutableLiveData<String>()
    private var _errorMessageForItemQuantity = MutableLiveData<String>()

    private var _itemPriority: Priority? = null



    val showSnackBarEvent: LiveData<String>
        get() = _showSnackbarEvent

    val showToastEvent: LiveData<String>
        get() = _showToastEvent

    val showErrorMessageForItemTitle: LiveData<String>
        get() = _errorMessageForItemTitle

    val showErrorMessageForItemPrice: LiveData<String>
        get() = _errorMessageForItemPrice

    val showErrorMessageForItemQuantity: LiveData<String>
        get() = _errorMessageForItemQuantity

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    fun doneShowingToast() {
        _showToastEvent.value = null
    }

    fun doneShowingErrorMessageForItemTitle() {
        _errorMessageForItemTitle.value = null
    }

    fun doneShowingErrorMessageForItemPrice() {
        _errorMessageForItemPrice.value = null
    }

    fun doneShowingErrorMessageForItemQuantity() {
        _errorMessageForItemQuantity.value = null
    }

    fun setPriority(priorityValue: Int){
        Priority.values().forEach {priority ->
            if(priority.value == priorityValue){
                _itemPriority = priority
                return
            }
        }

        _itemPriority = null
    }

    fun addItem(itemType: String, itemTitle: String, itemCategory: String, itemPrice: Double?, itemQty: Int?) {

        uiScope.launch {

            try{
                when(Utils.formattedStringToLifestyleEnum(itemType)){
                    LifestyleItem.GOAL ->{
                        if(itemTitle.isEmpty()){
                            _errorMessageForItemTitle.value = "Please choose a title for your Goal."
                            return@launch
                        }

                        val goal = Goal(title = itemTitle, category = itemCategory)
                        insert(goal)

                        _showSnackbarEvent.value = "$itemTitle has been added successfully."
                    }

                    LifestyleItem.TO_DO ->{

                        if(itemTitle.isEmpty()){
                            _errorMessageForItemTitle.value = "Please choose a title for your To Do."
                            return@launch
                        }

                        if(_itemPriority == null){
                            _showSnackbarEvent.value = "Please choose a priority."
                            return@launch
                        }

                        val toDo = ToDo(title = itemTitle, category = itemCategory, priority = _itemPriority!!)
                        insert(toDo)

                        _showSnackbarEvent.value = "$itemTitle has been added successfully."
                    }

                    LifestyleItem.TO_BUY ->{

                        if(itemTitle.isEmpty()){
                            _errorMessageForItemTitle.value = "Please choose a title for your To Buy."
                            return@launch
                        }

                        if(itemPrice == null){
                            _errorMessageForItemPrice.value = "Please set and estimation price for your To Buy."
                            return@launch
                        }

                        if(itemQty == null){
                            _errorMessageForItemQuantity.value = "Please set a quantity for your To Buy."
                            return@launch
                        }

                        if(itemQty < 1){
                            _errorMessageForItemQuantity.value = "Your quantity cannot be less than 1."
                            return@launch
                        }

                        if(_itemPriority == null){
                            _showSnackbarEvent.value = "Please choose a priority."
                            return@launch
                        }

                        val toBuy = ToBuy(title = itemTitle, category = itemCategory)
                        insert(toBuy)

                        _showSnackbarEvent.value = "$itemTitle has been added successfully."
                    }
                }

            }catch (ex: Exception){
                _showToastEvent.value = ex.message.toString()
            }
        }
    }

    //Private Methods
    private suspend fun insert(goal: Goal) {
        withContext(Dispatchers.IO) {
            goal.add(database)
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

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}