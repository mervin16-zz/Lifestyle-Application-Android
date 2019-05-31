package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.lifestyle.LifestyleFactory
import com.th3pl4gu3.lifestyle.core.utils.*

@Suppress(VALUE_SUPPRESSED_UNCHECKED_CAST)
class Filter<L>(var list: List<LifestyleFactory> = ArrayList()) {

    fun getByTitle(title: String): List<L> {
        return list.filter { (it.title.toLowerCase() == title.toLowerCase()) || (it.title.toLowerCase().contains(title.toLowerCase())) } as List<L>
    }

    fun getByCategory(category: String): List<L> {
        return list.filter {
            (it.category.toLowerCase() == category.toLowerCase()) || (it.category.toLowerCase().contains(
                category.toLowerCase()
            ))
        } as List<L>
    }

    fun getCompleted(): List<L> {
        return list.filter { it.dateCompleted != null } as List<L>
    }

    fun getActive(): List<L> {
        return list.filter { it.dateCompleted == null } as List<L>
    }

}