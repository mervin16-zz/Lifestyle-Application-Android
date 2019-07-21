package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder.*
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.*
import java.lang.Exception

@Suppress(VALUE_SUPPRESSED_UNCHECKED_CAST)
open class SortOperations<L>{

    open lateinit var list: List<Lifestyle>

    @Throws(Exception::class)
    fun byTitle(order: SortingOrder): List<L> {
        return when (order) {
            DESC -> list.sortedByDescending { it.title } as List<L>
            ASC -> list.sortedBy { it.title } as List<L>
        }
    }

    @Throws(Exception::class)
    fun byCategory(order: SortingOrder): List<L> {
        return when (order) {
            DESC -> list.sortedByDescending { it.category } as List<L>
            ASC -> list.sortedBy { it.category } as List<L>
        }
    }

    @Throws(Exception::class)
    fun byDateAdded(order: SortingOrder): List<L> {
        return when (order) {
            DESC -> list.sortedByDescending { it.dateAdded } as List<L>
            ASC -> list.sortedBy { it.dateAdded } as List<L>
        }
        //TODO("Test Method in ToDo")
        //TODO("Test Method in ToBuy")
        //TODO("Test Method in Goal")
    }

    fun byDateCompleted(order: SortingOrder): List<L> {
        return when (order) {
            DESC -> list.sortedByDescending { it.dateCompleted } as List<L>
            ASC -> list.sortedBy { it.dateCompleted } as List<L>
        }
        //TODO("Test Method in ToDo")
        //TODO("Test Method in ToBuy")
        //TODO("Test Method in Goal")
    }

    fun byDaysAlive(order: SortingOrder): List<L> {
        return when (order) {
            DESC -> list.sortedByDescending { it.daysActive } as List<L>
            ASC -> list.sortedBy { it.daysActive } as List<L>
        }
        //TODO("Test Method in ToDo")
        //TODO("Test Method in ToBuy")
        //TODO("Test Method in Goal")
    }

    fun byPriority(order: SortingOrder): List<L> {
        if(list.isNullOrEmpty()){
            return ArrayList()
        }

        when(list[0].type){
            LifestyleItem.TO_BUY.value -> {
                return when (order) {
                    DESC -> (list as List<ToBuy>).sortedByDescending { it.priority } as List<L>
                    ASC -> (list as List<ToBuy>).sortedBy { it.priority } as List<L>
                }
            }
            LifestyleItem.TO_DO.value -> {
                return when (order) {
                    DESC -> (list as List<ToDo>).sortedByDescending { it.priority } as List<L>
                    ASC -> (list as List<ToDo>).sortedBy { it.priority } as List<L>
                }
            }
            LifestyleItem.GOAL.value -> {throw Exception(MESSAGE_EXCEPTION_SORT_GOALS)}

            else -> {throw Exception(MESSAGE_EXCEPTION_SORT_OTHERS)}
        }
        //TODO("Test Method in ToDo")
        //TODO("Test Method in ToBuy")
    }
}