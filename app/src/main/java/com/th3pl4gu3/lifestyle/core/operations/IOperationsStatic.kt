package com.th3pl4gu3.lifestyle.core.operations

import androidx.lifecycle.LiveData
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

interface IOperationsStatic<L> {

    //Offline Operations
    fun getByIdOffline(database: LifestyleDatabase, id: String): L

    fun getAllOffline(database: LifestyleDatabase): LiveData<List<L>>
}