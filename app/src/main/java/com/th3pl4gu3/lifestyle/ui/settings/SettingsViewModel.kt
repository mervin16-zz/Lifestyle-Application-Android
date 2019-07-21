package com.th3pl4gu3.lifestyle.ui.settings

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.GoalOperations
import com.th3pl4gu3.lifestyle.core.operations.ToBuyOperations
import com.th3pl4gu3.lifestyle.core.operations.ToDoOperations
import com.th3pl4gu3.lifestyle.core.tuning.Settings
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel
import kotlinx.coroutines.*

@Suppress(VALUE_SUPPRESSED_UNCHECKED_CAST)
class SettingsViewModel(
    db: LifestyleDatabase,
    application: Application) : LifestyleOpsViewModel(db, application) {

    private var _viewModelJob = Job()
    private val _uiScope = CoroutineScope(Dispatchers.Main + _viewModelJob)
    private var _showBackupProgressNotification = MutableLiveData<Boolean>()
    private var _showRestoreProgressNotification = MutableLiveData<Boolean>()

    val showBackupProgressNotification: LiveData<Boolean>
        get() = _showBackupProgressNotification

    val showRestoreProgressNotification: LiveData<Boolean>
        get() = _showRestoreProgressNotification

    /**
     * Public functions that are accessible from the outside
     **/

    fun backupLocally() {

        _showBackupProgressNotification.value = true

        _uiScope.launch {

            Settings().createBackupLocally(GoalOperations.getAllOffline(database), LifestyleItem.GOAL.value)
            Settings().createBackupLocally(ToDoOperations.getAllOffline(database), LifestyleItem.TO_DO.value)
            Settings().createBackupLocally(ToBuyOperations.getAllOffline(database), LifestyleItem.TO_BUY.value)

            delay(3000)

            _showBackupProgressNotification.value = false
        }
    }

    fun restoreLocally() {

        _showRestoreProgressNotification.value = true

        _uiScope.launch {

            (Settings().restoreLocally(LifestyleItem.GOAL.value) as List<Goal>).forEach {
                if(!GoalOperations.getAllOffline(database).contains(it)){
                    insertItem(it)
                }

            }

            (Settings().restoreLocally(LifestyleItem.TO_DO.value) as List<ToDo>).forEach {
                if(!ToDoOperations.getAllOffline(database).contains(it)){
                    insertItem(it)
                }
            }

            (Settings().restoreLocally(LifestyleItem.TO_BUY.value) as List<ToBuy>).forEach {
                if(!ToBuyOperations.getAllOffline(database).contains(it)){
                    insertItem(it)
                }
            }


            delay(3000)

            _showRestoreProgressNotification.value = false
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