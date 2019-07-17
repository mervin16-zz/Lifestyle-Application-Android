package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase

class ToBuyOperations(list: List<ToBuy>) : LifestyleOperations<ToBuy>(list) {

    companion object : IOperationsStatic<ToBuy>{

        override fun getByIdOffline(database: LifestyleDatabase, id: String) = database.toBuyDao.get(id) ?: throw Exception(MESSAGE_EXCEPTION_TOBUY_NOT_EXIST)

        override fun getAllLiveOffline(database: LifestyleDatabase) = database.toBuyDao.getAllToBuysLive()

        //TODO: Test Function
        override suspend fun getAllOffline(database: LifestyleDatabase) = database.toBuyDao.getAllToBuys()

        override fun removeAllOffline(database: LifestyleDatabase) = database.toBuyDao.removeAll()
    }
}
