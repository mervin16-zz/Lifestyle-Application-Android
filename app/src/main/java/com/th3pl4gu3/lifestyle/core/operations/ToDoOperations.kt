package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

class ToDoOperations(list: List<ToDo>) : LifestyleOperations<ToDo>(list) {

    companion object : IOperationsStatic<ToDo>{

        override fun getByIdOffline(database: LifestyleDatabase, id: String) = database.toDoDao.get(id) ?: throw Exception(MESSAGE_EXCEPTION_TODO_NOT_EXIST)

        override fun getAllLiveOffline(database: LifestyleDatabase) = database.toDoDao.getAllToDosLive()

        //TODO: Test Function
        override suspend fun getAllOffline(database: LifestyleDatabase) = database.toDoDao.getAllToDos()

        override fun removeAllOffline(database: LifestyleDatabase) = database.toDoDao.removeAll()
    }
}
