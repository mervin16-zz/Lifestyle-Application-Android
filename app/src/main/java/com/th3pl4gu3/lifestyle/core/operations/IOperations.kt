
package com.th3pl4gu3.lifestyle.core.operations

interface IOperations<L>{

    fun removeAllOnline()

    fun removeAllOffline()

    fun getRecentlyAdded(): List<L>

    fun getRecentlyCompleted(): List<L>

    fun getOlderPendingItems(): List<L>
}