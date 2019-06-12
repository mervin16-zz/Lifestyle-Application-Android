package com.th3pl4gu3.lifestyle.ui.home.section_tobuy

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.th3pl4gu3.lifestyle.core.utils.MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_TOBUYS
import com.th3pl4gu3.lifestyle.core.utils.VALUE_SUPPRESSED_UNCHECKED_CAST
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

class ToBuyViewModelFactory(
    private val database: LifestyleDatabase,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress(VALUE_SUPPRESSED_UNCHECKED_CAST)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToBuyViewModel::class.java)) {
            return ToBuyViewModel(database, application) as T
        }
        throw IllegalArgumentException(MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_TOBUYS)
    }
}