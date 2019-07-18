package com.th3pl4gu3.lifestyle.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.utils.*


@Dao
interface GoalDao {

    @Insert
    fun insert(goal: Goal)

    @Update
    fun update(goal: Goal)

    @Query("DELETE FROM $DATABASE_TABLE_GOAL WHERE id = :key")
    fun remove(key: String)

    @Query("DELETE FROM $DATABASE_TABLE_GOAL")
    fun removeAll()

    @Query("SELECT * FROM $DATABASE_TABLE_GOAL WHERE id = :key")
    fun get(key: String): Goal?

    @Query("SELECT * FROM $DATABASE_TABLE_GOAL ORDER BY dateAdded")
    fun getAllGoalsLive(): LiveData<List<Goal>>

    @Query("SELECT * FROM $DATABASE_TABLE_GOAL")
    suspend fun getAllGoals(): List<Goal>

}