package com.th3pl4gu3.lifestyle.ui.home.section_todo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.lifestyle.core.utils.MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_TODOS
import com.th3pl4gu3.lifestyle.core.utils.VALUE_SUPPRESSED_UNCHECKED_CAST
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class ToDoViewModelFactory(
    private val database: LifestyleDatabase,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress(VALUE_SUPPRESSED_UNCHECKED_CAST)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            return ToDoViewModel(database, application) as T
        }else if(modelClass.isAssignableFrom(LifestyleOpsViewModel::class.java)){
            return LifestyleOpsViewModel(database, application) as T
        }
        throw IllegalArgumentException(MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_TODOS)
    }
}