package com.th3pl4gu3.lifestyle.core.tuning

import android.os.Environment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.*
import com.th3pl4gu3.lifestyle.files.ExternalPublicFileOperations

class Settings {

    private val DIRECTORY_TYPE = Environment.DIRECTORY_DOCUMENTS

    /**
     * Public functions that are accessible from the outside
     **/

    fun createBackupLocally(list: List<Lifestyle>, type: Int){
        val fileOps = ExternalPublicFileOperations(APPLICATION_DIRECTORY_BACKUP_NAME, DIRECTORY_TYPE)

        if(ExternalPublicFileOperations.isExternalStorageWritable()){
            when(type){
                LifestyleItem.GOAL.value -> {fileOps.createFile(APPLICATION_FILES_BACKUP_GOALS_NAME, createJsonBackup(list))}
                LifestyleItem.TO_DO.value -> {fileOps.createFile(APPLICATION_FILES_BACKUP_TODOS_NAME, createJsonBackup(list))}
                LifestyleItem.TO_BUY.value -> {fileOps.createFile(APPLICATION_FILES_BACKUP_TOBUYS_NAME, createJsonBackup(list))}
            }
        }

        else{throw Exception(MESSAGE_EXCEPTION_BACKUP_CREATION_LOCALLY_SETTINGS)}
    }

    fun restoreLocally(type: Int): List<Lifestyle>{
        when(type){
            LifestyleItem.GOAL.value -> {
                val json = ExternalPublicFileOperations(APPLICATION_DIRECTORY_BACKUP_NAME, DIRECTORY_TYPE).readFile(APPLICATION_FILES_BACKUP_GOALS_NAME)
                return restoreGoalsToList(json)
            }

            LifestyleItem.TO_DO.value -> {
                val json = ExternalPublicFileOperations(APPLICATION_DIRECTORY_BACKUP_NAME, DIRECTORY_TYPE).readFile(APPLICATION_FILES_BACKUP_TODOS_NAME)
                return restoreToDoToList(json)
            }

            LifestyleItem.TO_BUY.value -> {
                val json = ExternalPublicFileOperations(APPLICATION_DIRECTORY_BACKUP_NAME, DIRECTORY_TYPE).readFile(APPLICATION_FILES_BACKUP_TOBUYS_NAME)
                return restoreToBuyToList(json)
            }

            else -> {throw Exception(MESSAGE_EXCEPTION_RESTORATION_LOCALLY_SETTINGS)}
        }
    }


    /**
     * Private functions for internal use ONLY
     **/

    private fun createJsonBackup(list: List<Lifestyle>): String {
        return Gson().toJson(list)
    }

    private fun restoreGoalsToList(list: String): List<Goal>{
        val listType = object : TypeToken<List<Goal>>() {}.type
        return Gson().fromJson(list, listType) as List<Goal>
    }

    private fun restoreToDoToList(list: String): List<ToDo>{
        val listType = object : TypeToken<List<ToDo>>() {}.type
        return Gson().fromJson(list, listType) as List<ToDo>
    }

    private fun restoreToBuyToList(list: String): List<ToBuy>{
        val listType = object : TypeToken<List<ToBuy>>() {}.type
        return Gson().fromJson(list, listType) as List<ToBuy>
    }
}