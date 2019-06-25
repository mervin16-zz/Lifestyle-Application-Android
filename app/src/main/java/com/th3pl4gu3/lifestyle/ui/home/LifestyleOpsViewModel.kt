package com.th3pl4gu3.lifestyle.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import kotlinx.coroutines.*

open class LifestyleOpsViewModel(
    open val database: LifestyleDatabase,
    application: Application
) : AndroidViewModel(application) {

    private var _viewModelJob = Job()

    private val _uiScope = CoroutineScope(Dispatchers.Main + _viewModelJob)


    /**
     * Public functions that are accessible from the outside
     **/

    fun insertItem(lifestyle: Lifestyle) {
        _uiScope.launch {
            insert(lifestyle)
        }
    }

    fun markAsDeleted(lifestyle: Lifestyle) {
        _uiScope.launch {
            remove(lifestyle)
        }
    }

    fun markItem(lifestyle: Lifestyle) {
        if (lifestyle.dateCompleted == null) {
            markAsCompleted(lifestyle)
        } else {
            markAsIncomplete(lifestyle)
        }
    }

    /**
     * Private functions for internal use ONLY
     **/

    private fun markAsCompleted(newLifestyle: Lifestyle) {
        _uiScope.launch {
            newLifestyle.markAsComplete()
            update(newLifestyle)
        }
    }

    private fun markAsIncomplete(newLifestyle: Lifestyle) {
        _uiScope.launch {
            newLifestyle.markAsIncomplete()
            update(newLifestyle)
        }
    }

    private suspend fun insert(newLifestyle: Lifestyle) {
        withContext(Dispatchers.IO) {
            when (newLifestyle.type) {
                LifestyleItem.GOAL.value -> {
                    (newLifestyle as Goal).add(database)
                }
                LifestyleItem.TO_DO.value -> {
                    (newLifestyle as ToDo).add(database)
                }
                LifestyleItem.TO_BUY.value -> {
                    (newLifestyle as ToBuy).add(database)
                }
            }
        }
    }

    private suspend fun remove(lifestyle: Lifestyle) {
        withContext(Dispatchers.IO) {
            when (lifestyle.type) {
                LifestyleItem.GOAL.value -> {
                    (lifestyle as Goal).delete(database)
                }
                LifestyleItem.TO_DO.value -> {
                    (lifestyle as ToDo).delete(database)
                }
                LifestyleItem.TO_BUY.value -> {
                    (lifestyle as ToBuy).delete(database)
                }
            }
        }
    }

    private suspend fun update(lifestyle: Lifestyle) {
        withContext(Dispatchers.IO) {
            when (lifestyle.type) {
                LifestyleItem.GOAL.value -> {
                    (lifestyle as Goal).update(database)
                }
                LifestyleItem.TO_DO.value -> {
                    (lifestyle as ToDo).update(database)
                }
                LifestyleItem.TO_BUY.value -> {
                    (lifestyle as ToBuy).update(database)
                }
            }
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