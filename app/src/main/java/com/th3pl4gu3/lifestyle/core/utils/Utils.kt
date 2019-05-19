package com.th3pl4gu3.lifestyle.core.utils

import java.text.DateFormat
import java.util.*

sealed class Utils{
    companion object{
        //Return date in format eg. 16 March 1996
        fun dateToFormattedString(cal: Calendar): String{
            return DateFormat.getDateInstance(DateFormat.MEDIUM).format(cal.time)
        }
    }
}