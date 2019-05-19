package com.th3pl4gu3.lifestyle.core.lifestyle

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
        return true
    }

    fun daysActive(): Int {
        return 0
    }

    abstract fun update()

    abstract fun delete()

    abstract fun add()
}