package com.th3pl4gu3.lifestyle.core.lifestyle

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import java.util.*

@Entity(tableName = DATABASE_TABLE_TOBUY)
data class ToBuy constructor(
    @ColumnInfo(name = DATABASE_TABLE_TOBUY_TITLE)
    override var title: String = PLACEHOLDER_ITEM_LIFESTYLE_TITLE,

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_CATEGORY)
    override var category: String = PLACEHOLDER_ITEM_LIFESTYLE_CATEGORY,

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_PRICE)
    var estimatedPrice: Double = PLACEHOLDER_ITEM_LIFESTYLE_ESTIMATEDPRICE,

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_QTY)
    var quantity: Int = PLACEHOLDER_ITEM_LIFESTYLE_QTY,

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_PRIORITY)
    var priority: Priority = Priority.P4
) : LifestyleFactory() {

    @PrimaryKey(autoGenerate = false)
    var id: String = super.uniqueId

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_DATEADDED)
    override var dateAdded: Calendar = super.dateAdded

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_DATECOMPLETED)
    override var dateCompleted: Calendar? = null

    override var type: Int = LifestyleItem.TO_BUY.value


    override fun markAsIncomplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun markAsComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(database: LifestyleDatabase) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(database: LifestyleDatabase) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(database: LifestyleDatabase) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}