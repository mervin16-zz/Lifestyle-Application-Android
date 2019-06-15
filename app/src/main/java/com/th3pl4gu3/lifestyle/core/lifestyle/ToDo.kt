package com.th3pl4gu3.lifestyle.core.lifestyle

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import java.util.*

/**
 * To Do Class is one of the child of [Lifestyle] class.
 * The purpose of the To Do class is to hold data about each to do task of the user.
 **/
@Entity(tableName = DATABASE_TABLE_TODO)
data class ToDo constructor(
    @ColumnInfo(name = DATABASE_TABLE_TODO_TITLE)
    override var title: String = PLACEHOLDER_ITEM_LIFESTYLE_TITLE,

    @ColumnInfo(name = DATABASE_TABLE_TODO_CATEGORY)
    override var category: String = PLACEHOLDER_ITEM_LIFESTYLE_CATEGORY,

    @ColumnInfo(name = DATABASE_TABLE_TODO_PRIORITY)
    var priority: Priority = Priority.P4
) : Lifestyle() {

    @PrimaryKey(autoGenerate = false)
    var id: String = super.uniqueId

    @ColumnInfo(name = DATABASE_TABLE_TODO_DATEADDED)
    override var dateAdded: Calendar = super.dateAdded

    @ColumnInfo(name = DATABASE_TABLE_TODO_DATECOMPLETED)
    override var dateCompleted: Calendar? = null

    override var type: Int = LifestyleItem.TO_DO.value

    override fun add(database: LifestyleDatabase) = database.toDoDao.insert(this)

    override fun update(database: LifestyleDatabase) = database.toDoDao.update(this)

    override fun delete(database: LifestyleDatabase) = database.toDoDao.remove(this.id)
}