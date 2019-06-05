package com.th3pl4gu3.lifestyle.core.lifestyle

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import java.util.*

@Entity(tableName = DATABASE_TABLE_GOAL)
data class Goal constructor(
    @ColumnInfo(name = DATABASE_TABLE_GOAL_TITLE)
    override var title: String = PLACEHOLDER_ITEM_LIFESTYLE_TITLE,

    @ColumnInfo(name = DATABASE_TABLE_GOAL_CATEGORY)
    override var category: String = PLACEHOLDER_ITEM_LIFESTYLE_CATEGORY
) : LifestyleFactory() {

    @PrimaryKey(autoGenerate = false)
    var id: String = super.uniqueId

    @ColumnInfo(name = DATABASE_TABLE_GOAL_DATEADDED)
    override var dateAdded: Calendar = super.dateAdded

    @ColumnInfo(name = DATABASE_TABLE_GOAL_DATECOMPLETED)
    override var dateCompleted: Calendar? = null

    override var type: Int = LifestyleItem.GOAL.value


    override fun markAsIncomplete() {
        this.dateCompleted = null
    }

    override fun markAsComplete() {
        this.dateCompleted = Calendar.getInstance()
    }

    override fun add(database: LifestyleDatabase) = database.goalDao.insert(this)

    override fun update(database: LifestyleDatabase) = database.goalDao.update(this)

    override fun delete(database: LifestyleDatabase) = database.goalDao.remove(this.id)
}