
package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

interface IOperations<L>{

    fun removeAllOffline(database: LifestyleDatabase)

    fun getRecentlyAdded(list: List<L>): List<L>

    fun getRecentlyCompleted(list: List<L>): List<L>

    fun getOlderPendingItems(list: List<L>): List<L>
}