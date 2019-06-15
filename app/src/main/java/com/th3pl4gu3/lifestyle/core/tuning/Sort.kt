package com.th3pl4gu3.lifestyle.core.tuning

import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.operations.SortOperations

class Sort<L>(var order: Order = Order.ASC,
              var value: Value,
              override var list: ArrayList<out Lifestyle>) : SortOperations<L>(list = list) {

    //TODO("Test Method in ToDo")
    //TODO("Test Method in ToBuy")
    //TODO("Test Method in Goal")
    fun getSortedList(): List<L> {
        return when(value){
            Value.TITLE -> byTitle(order)
            Value.CATEGORY -> byCategory(order)
            Value.DATE_ADDED -> byDateAdded(order)
            Value.DATE_COMPLETED -> byDateCompleted(order)
            Value.DAYS_ALIVE -> byDaysAlive(order)
            Value.PRIORITY -> byPriority(order)
        }
    }
}