package com.th3pl4gu3.lifestyle.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.*


@Dao
interface ToDoDao {

    @Insert
    fun insert(todo: ToDo)

    @Update
    fun update(todo: ToDo)

    @Query("DELETE FROM $DATABASE_TABLE_TODO WHERE id = :key")
    fun remove(key: String)

    @Query("DELETE FROM $DATABASE_TABLE_TODO")
    fun removeAll()

    @Query("SELECT * FROM $DATABASE_TABLE_TODO WHERE id = :key")
    fun get(key: String): ToDo?

    @Query("SELECT * FROM $DATABASE_TABLE_TODO")
    fun getAllToDos(): LiveData<List<ToDo>>

}