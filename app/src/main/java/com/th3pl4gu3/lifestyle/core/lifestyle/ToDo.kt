package com.th3pl4gu3.lifestyle.core.lifestyle

import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import java.util.*

data class ToDo constructor(
    override var id: String = UUID.randomUUID().toString(),
    override var title: String = "Title",
    override var category: String = "Category",
    var priority: Priority = Priority.P4
) : ILifestyle {

    override var type: Int = LifestyleItem.TO_DO.value
    override var dateAdded: Date = Date()

    override lateinit var dateCompleted: Date


    override fun isCompleted(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun daysActive(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}