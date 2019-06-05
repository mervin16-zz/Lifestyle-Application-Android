package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

class GoalOperations(list: List<Goal>) : LifestyleOperations<Goal>(list) {

    companion object : IOperationsStatic<Goal>{

        override fun getByIdOffline(database: LifestyleDatabase, id: String) = database.goalDao.get(id) ?: throw Exception(MESSAGE_EXCEPTION_GOAL_NOT_EXIST)

        override fun getAllOffline(database: LifestyleDatabase) = database.goalDao.getAllGoals()

        override fun removeAllOffline(database: LifestyleDatabase) = database.goalDao.removeAll()
    }
}