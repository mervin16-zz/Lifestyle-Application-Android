package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.lifestyle.LifestyleFactory
import java.lang.Exception

class Sort<L>(var list: ArrayList<out LifestyleFactory>) {

    enum class Order {
        ASC,
        DESC
    }

    @Throws(Exception::class)
    fun byTitle(order: Order): List<L> {
        return when (order) {
            Order.DESC -> list.sortedByDescending { it.title } as List<L>
            Order.ASC -> list.sortedBy { it.title } as List<L>
        }
    }

    @Throws(Exception::class)
    fun byCategory(order: Order): List<L> {
        return when (order) {
            Order.DESC -> list.sortedByDescending { it.category } as List<L>
            Order.ASC -> list.sortedBy { it.category } as List<L>
        }
    }

    @Throws(Exception::class)
    fun byDateAdded(order: Order): List<L> {
        return when (order) {
            Order.DESC -> list.sortedByDescending { it.dateAdded } as List<L>
            Order.ASC -> list.sortedBy { it.dateAdded } as List<L>
        }
        //TODO("Test Method in ToDo")
        //TODO("Test Method in ToBuy")
        //TODO("Test Method in Goal")
    }

    fun byDateCompleted(order: Order): List<L> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //TODO("Test Method in ToDo")
        //TODO("Test Method in ToBuy")
        //TODO("Test Method in Goal")
    }
}