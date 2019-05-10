package com.th3pl4gu3.lifestyle.core.lifestyle

import java.util.*

interface ILifestyle{

    //Variables
    var type: Int
    var id: String
    var title: String
    var category: String
    var dateAdded: Date
    var dateCompleted: Date


    //Functions
    fun isCompleted(): Boolean

    fun daysActive(): Int

    fun update()

    fun delete()
}
