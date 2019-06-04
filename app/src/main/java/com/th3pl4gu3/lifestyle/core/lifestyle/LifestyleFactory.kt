package com.th3pl4gu3.lifestyle.core.lifestyle

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

    //Functions
    fun isCompleted(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun daysActive(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun markAsIncomplete()

    abstract fun markAsComplete()

    abstract fun update(database: LifestyleDatabase)

    abstract fun delete(database: LifestyleDatabase)

    abstract fun add(database: LifestyleDatabase)
}