package com.th3pl4gu3.lifestyle.core.utils

import androidx.room.util.StringUtil
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.ui.utils.capitalizeEachWords
import org.apache.commons.lang3.StringUtils
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.floor

sealed class Utils {
    companion object {
        //Return date in format eg. 16 March 1996
        fun dateToFormattedString(cal: Calendar): String =
            DateFormat.getDateInstance(DateFormat.MEDIUM).format(cal.time)

        fun getLifestyleItemEnumsToFormattedString(): ArrayList<String> {

            val list = ArrayList<String>()

            for (enum in LifestyleItem.values()) {
                val removedUnderscore = enum.toString().toLowerCase().replace(VALUE_UNDERSCORE, VALUE_WHITESPACE)
                val capitalized = removedUnderscore.capitalizeEachWords()
                list.add(capitalized)
            }

            return list
        }

        fun formattedStringToLifestyleEnum(lifestyleItem: String): LifestyleItem {
            return when (lifestyleItem) {
                VALUE_LIFESTYLE_ITEM_GOAL -> LifestyleItem.GOAL
                VALUE_LIFESTYLE_ITEM_TOBUY -> LifestyleItem.TO_BUY
                VALUE_LIFESTYLE_ITEM_TODO -> LifestyleItem.TO_DO
                else -> throw Exception(MESSAGE_EXCEPTION_REQUEST_PROCESSING)
            }
        }

        //Returns a Long object counting days between the two time
        fun countDays(startDate: Calendar, endDate: Calendar) =
            ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant())

        //Convert to currency format
        fun toCurrency(money: Double): String {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
            val decimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols
            decimalFormatSymbols.currencySymbol = VALUE_CURRENCY_SYMBOL_RS
            numberFormat.decimalFormatSymbols = decimalFormatSymbols
            return numberFormat.format(money).trim()
        }

        //Convert number of days to formatted string
        fun formatDaysActive(days: Int): String {
            return when {
                days == 1 -> PLACEHOLDER_DAYS_1
                days in 2..30 -> "$days $PLACEHOLDER_DAYS_MULTIPLE"
                days == 31 || days in 32..61 -> PLACEHOLDER_MONTHS_1
                days in 62..364 -> "${floor(days / 31.00).toInt()} $PLACEHOLDER_MONTHS_MULTIPLE"
                days == 365 || days in 366..729 -> PLACEHOLDER_YEARS_1
                days > 730 -> "${floor(days / 365.00).toInt()} $PLACEHOLDER_YEARS_MULTIPLE"
                else -> throw Exception(MESSAGE_EXCEPTION_DAYSACTIVE_ERROR)
            }
        }

        //Capitalize each words in a sentence
        fun capitalizeEachWords(sentence: String): String {
            val formatted = sentence.toLowerCase().trim().replace(VALUE_REGEX_WHITESPACES_SEQUENCE.toRegex(), VALUE_WHITESPACE)
            return formatted.split(VALUE_WHITESPACE).joinToString(VALUE_WHITESPACE) { it.capitalize() }
        }

        //Minify String if it is too long
        fun abbreviate(sentence: String, length: Int): String{
            return StringUtils.abbreviate(sentence, length)
        }
    }
}