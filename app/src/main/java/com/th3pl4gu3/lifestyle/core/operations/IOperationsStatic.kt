package com.th3pl4gu3.lifestyle.core.operations

import androidx.lifecycle.LiveData
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

interface IOperationsStatic <LifestyleItem> {

    //Offline Operations
    fun getByIdOffline(database: LifestyleDatabase, id: String): LifestyleItem

    fun getAllOffline(database: LifestyleDatabase): LiveData<List<LifestyleItem>>

    fun removeAllOffline(database: LifestyleDatabase)
}