package com.th3pl4gu3.lifestyle.core.lifestyle

import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import java.util.*

/**
 * Lifestyle Class is the parent class of the Lifestyle objects.
 * There are currently 3 lifestyle objects available namely:
 * @see Goal
 * @see ToDo
 * @see ToBuy
 * All Lifestyle objects inherit the Lifestyle class.
 **/
abstract class Lifestyle {

    var uniqueId: String = UUID.randomUUID().toString()
    open var dateAdded: Calendar = Calendar.getInstance()

    abstract var title: String
    abstract var category: String
    abstract var dateCompleted: Calendar?
    abstract var type: Int

    val daysActive: Long
        get() = Utils.countDays(dateAdded, Calendar.getInstance())

    val isCompleted
        get() = dateCompleted != null

    fun markAsIncomplete() {
        this.dateCompleted = null
    }

    fun markAsComplete() {
        this.dateCompleted = Calendar.getInstance()
    }

    abstract fun update(database: LifestyleDatabase)

    abstract fun delete(database: LifestyleDatabase)

    abstract fun add(database: LifestyleDatabase)
}