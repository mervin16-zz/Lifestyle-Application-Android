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

    @Throws(Exception::class)
    override fun add(database: LifestyleDatabase) {
        val dataSource = database.goalDao
        dataSource.insert(this)
    }

    @Throws(Exception::class)
    override fun update(database: LifestyleDatabase) {
        val dataSource = database.goalDao
        dataSource.update(this)
    }

    @Throws(Exception::class)
    override fun delete(database: LifestyleDatabase) {
        val dataSource = database.goalDao
        dataSource.remove(this.id)
    }
}