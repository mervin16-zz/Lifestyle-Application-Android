package com.th3pl4gu3.lifestyle.core.tuning

import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations

class Filter<L>(private var priorities: ArrayList<Priority> = ArrayList(),
                private var categories:ArrayList<String> = ArrayList(),
                override var list: List<Lifestyle>): FilterOperations<L>(list = list){

    //TODO("Test Method in ToBuy")
    fun filterPrioritiesInToBuy(): List<ToBuy> {
        val list: ArrayList<ToBuy> = ArrayList()

        priorities.forEach { priority ->
            list += byToBuyPriority(priority)
        }

        return list
    }

    //TODO("Test Method in ToDo")
    fun filterPrioritiesInToDo(): List<ToDo> {
        val list: ArrayList<ToDo> = ArrayList()

        priorities.forEach { priority ->
            list += byToDoPriority(priority)
        }

        return list
    }

    //TODO("Test Method in ToDo")
    //TODO("Test Method in ToBuy")
    //TODO("Test Method in Goal")
    fun filterCategoriesList(): List<L> {
        val list: ArrayList<L> = ArrayList()

        categories.forEach { category ->
            list += byCategory(category)
        }

        return list
    }


    //TODO("Test Method in ToDo")
    //TODO("Test Method in ToBuy")
    //TODO("Test Method in Goal")
    fun toggle(priority: Priority){
        if(!priorities.contains(priority)){
            priorities.add(priority)
        }else{
            priorities.remove(priority)
        }
    }

    //TODO("Test Method in ToDo")
    //TODO("Test Method in ToBuy")
    //TODO("Test Method in Goal")
    fun toggle(category: String){
        if(!categories.contains(category)){
            categories.add(category)
        }else{
            categories.remove(category)
        }
    }

    //TODO("Test Method in ToDo")
    //TODO("Test Method in ToBuy")
    //TODO("Test Method in Goal")
    fun isChecked(priority: Priority) = priorities.contains(priority)

    //TODO("Test Method in ToDo")
    //TODO("Test Method in ToBuy")
    //TODO("Test Method in Goal")
    fun isChecked(category: String) = categories.contains(category)
}