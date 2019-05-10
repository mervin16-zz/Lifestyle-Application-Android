
package com.th3pl4gu3.lifestyle.core.operations

interface IOperations<L>{

    fun getById(id: String): L

    fun getAll(): List<L>

    fun getByTitle(title: String): List<L>

    fun getByCategory(category: String): List<L>

    fun getCompleted(): List<L>

    fun getActive(): List<L>

    fun sortDateAddedByAsc(): List<L>

    fun sortDateAddedByDsc(): List<L>

}