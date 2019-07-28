package com.th3pl4gu3.lifestyle.ui.add_item

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class AddItemViewModel(
    db: LifestyleDatabase,
    application: Application) : LifestyleOpsViewModel(db, application) {

    /**
     * Live Data Variables
     **/
    private var _priority1 = MutableLiveData<Boolean>()
    private var _priority2 = MutableLiveData<Boolean>()
    private var _priority3 = MutableLiveData<Boolean>()
    private var _priority4 = MutableLiveData<Boolean>()

    private var prioritySection = MutableLiveData<Boolean>()
    private var toBuySection = MutableLiveData<Boolean>()
    var calendarSection = MutableLiveData<Boolean>()

    //Screen Messages
    private var _showSnackbarEvent = MutableLiveData<String>()

    val showSnackBarEvent: LiveData<String>
        get() = _showSnackbarEvent

    var itemPriority: Priority? = null
        private set

    /**
     * Transformation Maps
     **/
    var calendarSectionVisibility: LiveData<Int> = Transformations.map(calendarSection){
        if(it) View.VISIBLE else View.GONE
    }

    var prioritySectionVisibility: LiveData<Int> = Transformations.map(prioritySection){
        if(it) View.VISIBLE else View.GONE
    }

    var toBuySectionVisibility: LiveData<Int> = Transformations.map(toBuySection){
        if(it) View.VISIBLE else View.GONE
    }

    var priority1Visibility: LiveData<Int> = Transformations.map(_priority1){
        if(it) View.VISIBLE else View.GONE
    }

    var priority2Visibility: LiveData<Int> = Transformations.map(_priority2){
        if(it) View.VISIBLE else View.GONE
    }

    var priority3Visibility: LiveData<Int> = Transformations.map(_priority3){
        if(it) View.VISIBLE else View.GONE
    }

    var priority4Visibility: LiveData<Int> = Transformations.map(_priority4){
        if(it) View.VISIBLE else View.GONE
    }

    //Init Block
    init{
        _priority1.value = true
        _priority2.value = true
        _priority3.value = true
        _priority4.value = true

        prioritySection.value = false
        calendarSection.value = false
        toBuySection.value = false
    }

    /**
     * Public functions that are accessible from the outside
     **/
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    fun handleLifestyleItemSelections(selectedItem: String){
        when (Utils.formattedStringToLifestyleEnum(selectedItem)) {
            LifestyleItem.GOAL -> {
                toBuySection.value = false
                prioritySection.value = false
            }
            LifestyleItem.TO_BUY -> {
                toBuySection.value = true
                prioritySection.value = true
            }
            LifestyleItem.TO_DO -> {
                toBuySection.value = false
                prioritySection.value = true
            }
        }
    }

    fun updatePrioritiesVisibility(priority: Priority){

        if(itemPriority == priority){
            _priority1.value = true
            _priority2.value = true
            _priority3.value = true
            _priority4.value = true

            setPriority(0)

            return
        }

        when(priority){

            Priority.P1 -> {
                _priority1.value = true
                _priority2.value = false
                _priority3.value = false
                _priority4.value = false
            }

            Priority.P2 -> {
                _priority1.value = false
                _priority2.value = true
                _priority3.value = false
                _priority4.value = false
            }
            Priority.P3 -> {
                _priority1.value = false
                _priority2.value = false
                _priority3.value = true
                _priority4.value = false
            }
            Priority.P4 -> {
                _priority1.value = false
                _priority2.value = false
                _priority3.value = false
                _priority4.value = true
            }

        }

        setPriority(priority.value)
    }

    fun addToDo(title: String, category: String) {
        val toDo = ToDo(title = title, category = category, priority = itemPriority!!)
        insertItem(toDo)

        _showSnackbarEvent.value = getApplication<Application>().getString(R.string.Message_Success_fromActivityAddItem_ItemAdded, title)

    }

    fun addGoal(title: String, category: String) {
        val goal = Goal(title = title, category = category)
        insertItem(goal)

        _showSnackbarEvent.value = getApplication<Application>().getString(R.string.Message_Success_fromActivityAddItem_ItemAdded, title)
    }

    fun addToBuy(title: String, category: String, quantity: Int, price: Double) {
        val toBuy = ToBuy(title = title, category = category, priority = itemPriority!!, quantity = quantity, estimatedPrice = price)
        insertItem(toBuy)

        _showSnackbarEvent.value = getApplication<Application>().getString(R.string.Message_Success_fromActivityAddItem_ItemAdded, title)
    }


    /**
     * Private functions for internal use ONLY
     **/
    private fun setPriority(priorityValue: Int){
        Priority.values().forEach {priority ->
            if(priority.value == priorityValue){
                itemPriority = priority
                return
            }
        }

        itemPriority = null
    }
}