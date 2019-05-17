package com.th3pl4gu3.lifestyle.database

import androidx.room.TypeConverter
import com.th3pl4gu3.lifestyle.core.enums.Priority
import java.util.*


class Converters {

    companion object{

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Calendar? {

            return if (value == null)
                null
            else {
                val instance = Calendar.getInstance()
                instance.timeInMillis = value

                instance
            }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Calendar?): Long? {
            return date?.timeInMillis
        }

        @TypeConverter
        @JvmStatic
        fun fromEnumToInt(priority: Priority): Int? {
            return priority.value
        }

        @TypeConverter
        @JvmStatic
        fun fromIntToEnum(value: Int): Priority? {
            return Priority.values().find { it.value == value }
        }
    }
}