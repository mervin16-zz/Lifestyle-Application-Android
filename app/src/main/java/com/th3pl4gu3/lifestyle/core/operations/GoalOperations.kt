package com.th3pl4gu3.lifestyle.core.operations

import androidx.lifecycle.LiveData
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

class GoalOperations : IOperations<Goal> {

    companion object : IOperationsStatic<Goal>{

        override fun getByIdOffline(database: LifestyleDatabase, id: String): Goal {
            val dataSource = database.goalDao
            return dataSource.get(id) ?: throw Exception(MESSAGE_EXCEPTION_GOAL_NOT_EXIST)
        }

        override fun getAllOffline(database: LifestyleDatabase): LiveData<List<Goal>> {
            val dataSource = database.goalDao
            return dataSource.getAllGoals()
        }
    }

    override fun getRecentlyAdded(list: List<Goal>): List<Goal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecentlyCompleted(list: List<Goal>): List<Goal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOlderPendingItems(list: List<Goal>): List<Goal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //Offline Database Operations
    override fun removeAllOffline(database: LifestyleDatabase) {
        val dataSource = database.goalDao
        return dataSource.removeAll()
    }
}