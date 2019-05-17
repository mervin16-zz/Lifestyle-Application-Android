
package com.th3pl4gu3.lifestyle.core.operations

import java.util.ArrayList

interface IOperations<L>{

    fun getById(id: String): L

    fun getAll(): ArrayList<L>

    fun getByTitle(title: String): ArrayList<L>

    fun getByCategory(category: String): ArrayList<L>

    fun getCompleted(): ArrayList<L>

    fun getActive(): ArrayList<L>

    fun sortDateAddedByAsc(): ArrayList<L>

    fun sortDateAddedByDsc(): ArrayList<L>

}