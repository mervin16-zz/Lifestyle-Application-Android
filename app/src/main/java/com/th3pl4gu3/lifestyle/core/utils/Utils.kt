package com.th3pl4gu3.lifestyle.core.utils

import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.ui.utils.capitalizeEachWords
import java.text.DateFormat
import java.time.temporal.ChronoUnit
import java.util.*

sealed class Utils{
    companion object{
        //Return date in format eg. 16 March 1996
        fun dateToFormattedString(cal: Calendar): String = DateFormat.getDateInstance(DateFormat.MEDIUM).format(cal.time)

        //TODO("Test method")
        fun getLifestyleItemEnumsToFormattedString(): ArrayList<String> {

            val list = ArrayList<String>()

            for(enum in LifestyleItem.values()){
                val removedUnderscore = enum.toString().toLowerCase().replace(VALUE_UNDERSCORE, VALUE_WHITESPACE)
                val capitalized = removedUnderscore.capitalizeEachWords()
                list.add(capitalized)
            }

            return list
        }

        //TODO("Test method")
        fun formattedStringToLifestyleEnum(lifestyleItem: String): LifestyleItem{
            return when (lifestyleItem) {
                VALUE_LIFESTYLE_ITEM_GOAL -> LifestyleItem.GOAL
                VALUE_LIFESTYLE_ITEM_TOBUY -> LifestyleItem.TO_BUY
                VALUE_LIFESTYLE_ITEM_TODO -> LifestyleItem.TO_DO
                else -> throw Exception(MESSAGE_EXCEPTION_REQUEST_PROCESSING)
            }
        }

        //Returns a Long object counting days between the two time
        fun countDays(startDate: Calendar, endDate: Calendar) = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant())
    }
}