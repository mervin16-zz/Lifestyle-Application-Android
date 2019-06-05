package com.th3pl4gu3.lifestyle.core.lifestyle

import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import java.util.*

abstract class LifestyleFactory{

    //Variables
    var uniqueId: String = UUID.randomUUID().toString()
    open var dateAdded: Calendar = Calendar.getInstance()

    abstract var title: String
    abstract var category: String
    abstract var dateCompleted: Calendar?
    abstract var type: Int

    //TODO("Test property in Goal")
    //TODO("Test property in ToDo")
    //TODO("Test property in ToBuy")
    open val daysActive: Long
        get() = Utils.countDays(dateAdded, Calendar.getInstance())


    //Functions
    //TODO("Test property in Goal")
    //TODO("Test property in ToDo")
    //TODO("Test property in ToBuy")
    fun isCompleted() = dateCompleted != null

    abstract fun markAsIncomplete()

    abstract fun markAsComplete()

    abstract fun update(database: LifestyleDatabase)

    abstract fun delete(database: LifestyleDatabase)

    abstract fun add(database: LifestyleDatabase)
}