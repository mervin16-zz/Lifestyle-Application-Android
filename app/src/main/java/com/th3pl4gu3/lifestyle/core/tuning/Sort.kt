package com.th3pl4gu3.lifestyle.core.tuning

import com.th3pl4gu3.lifestyle.core.enums.SortingOrder
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder.*
import com.th3pl4gu3.lifestyle.core.enums.SortingValue
import com.th3pl4gu3.lifestyle.core.enums.SortingValue.*
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.operations.SortOperations
import java.lang.Exception

class Sort<L>(var order: SortingOrder = ASC,
              var value: SortingValue = DATE_ADDED
) : SortOperations<L>() {

    //TODO("Test Method in ToDo")
    //TODO("Test Method in ToBuy")
    //TODO("Test Method in Goal")
    override lateinit var list: List<Lifestyle>

    fun getSortedList(): List<L> {
        if(list.isNullOrEmpty())
            throw Exception("Error")
        return when(value){
            TITLE -> byTitle(order)
            CATEGORY -> byCategory(order)
            DATE_ADDED -> byDateAdded(order)
            DATE_COMPLETED -> byDateCompleted(order)
            DAYS_ALIVE -> byDaysAlive(order)
            PRIORITY -> byPriority(order)
        }
    }
}