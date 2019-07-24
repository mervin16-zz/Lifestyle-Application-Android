package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.*

@Suppress(VALUE_SUPPRESSED_UNCHECKED_CAST)
open class FilterOperations<L>(open var list: List<Lifestyle> = ArrayList()) {

    //TODO Refactor whole class and Filter class.
    fun byCategory(category: String): List<L> {
        return list.filter {
            (it.category.toLowerCase() == category.toLowerCase())
        } as List<L>
    }

    //TODO("Test Method in ToDo")
    fun byToDoPriority(priority: Priority) = (list as ArrayList<ToDo>).filter { it.priority == priority }

    //TODO("Test Method in ToBuy")
    fun byToBuyPriority(priority: Priority) = (list as ArrayList<ToBuy>).filter { it.priority == priority }

    fun getActive() = list.filter { it.dateCompleted == null } as List<L>

    fun getCompleted() = list.filter { it.dateCompleted != null } as List<L>
}