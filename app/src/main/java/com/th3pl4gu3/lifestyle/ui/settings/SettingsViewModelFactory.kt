package com.th3pl4gu3.lifestyle.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class SettingsViewModelFactory(
    private val dataSource: LifestyleDatabase,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress(VALUE_SUPPRESSED_UNCHECKED_CAST)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(dataSource, application) as T
        }else if(modelClass.isAssignableFrom(LifestyleOpsViewModel::class.java)) {
            return LifestyleOpsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException(MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_SETTINGS)
    }
}