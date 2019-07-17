package com.th3pl4gu3.lifestyle.ui.add_item

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class AddItemViewModel(
    db: LifestyleDatabase,
    application: Application) : LifestyleOpsViewModel(db, application) {

    //Screen Messages
    private var _showSnackbarEvent = MutableLiveData<String>()

    var itemPriority: Priority? = null
        private set


    val showSnackBarEvent: LiveData<String>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }


    fun setPriority(priorityValue: Int){
        Priority.values().forEach {priority ->
            if(priority.value == priorityValue){
                itemPriority = priority
                return
            }
        }

        itemPriority = null
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
}