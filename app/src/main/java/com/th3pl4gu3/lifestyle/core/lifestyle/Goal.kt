package com.th3pl4gu3.lifestyle.core.lifestyle

import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import java.util.*

data class Goal constructor(
    override var id: String = UUID.randomUUID().toString(),
    override var title: String = "Title",
    override var category: String = "Category"
) : ILifestyle {

    override var type: Int = LifestyleItem.GOAL.value
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