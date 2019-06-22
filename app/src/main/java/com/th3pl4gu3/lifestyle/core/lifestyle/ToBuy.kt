package com.th3pl4gu3.lifestyle.core.lifestyle

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * To Buy Class is one of the child of [Lifestyle] class.
 * The purpose of the To Buy class is to hold data about each to buy task of the user.
 **/
@Entity(tableName = DATABASE_TABLE_TOBUY)
@Parcelize
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
) : Lifestyle(), Parcelable{

    @PrimaryKey(autoGenerate = false)
    var id: String = super.uniqueId

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_DATEADDED)
    override var dateAdded: Calendar = super.dateAdded

    @ColumnInfo(name = DATABASE_TABLE_TOBUY_DATECOMPLETED)
    override var dateCompleted: Calendar? = null

    override var type: Int = LifestyleItem.TO_BUY.value

    val total: Double
        get() = quantity * estimatedPrice

    override fun add(database: LifestyleDatabase) = database.toBuyDao.insert(this)

    override fun update(database: LifestyleDatabase) = database.toBuyDao.update(this)

    override fun delete(database: LifestyleDatabase) = database.toBuyDao.remove(this.id)
}