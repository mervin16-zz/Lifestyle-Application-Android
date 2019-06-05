package com.th3pl4gu3.lifestyle.core.utils

import java.text.DateFormat
import java.time.temporal.ChronoUnit
import java.util.*

sealed class Utils{
    companion object{
        //Return date in format eg. 16 March 1996
        fun dateToFormattedString(cal: Calendar) = DateFormat.getDateInstance(DateFormat.MEDIUM).format(cal.time)

        //Returns a Long object counting days between the two time
        fun countDays(startDate: Calendar, endDate: Calendar) = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant())
    }
}