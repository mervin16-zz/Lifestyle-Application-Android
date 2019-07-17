package com.th3pl4gu3.lifestyle.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.*


@Dao
interface ToBuyDao {

    @Insert
    fun insert(toBuy: ToBuy)

    @Update
    fun update(toBuy: ToBuy)

    @Query("DELETE FROM $DATABASE_TABLE_TOBUY WHERE id = :key")
    fun remove(key: String)

    @Query("DELETE FROM $DATABASE_TABLE_TOBUY")
    fun removeAll()

    @Query("SELECT * FROM $DATABASE_TABLE_TOBUY WHERE id = :key")
    fun get(key: String): ToBuy?

    @Query("SELECT * FROM $DATABASE_TABLE_TOBUY")
    fun getAllToBuysLive(): LiveData<List<ToBuy>>

    @Query("SELECT * FROM $DATABASE_TABLE_TOBUY")
    suspend fun getAllToBuys(): List<ToBuy>
}