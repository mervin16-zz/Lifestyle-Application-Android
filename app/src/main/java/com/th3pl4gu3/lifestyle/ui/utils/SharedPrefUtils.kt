package com.th3pl4gu3.lifestyle.ui.utils

import android.content.Context
import android.content.SharedPreferences
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder.*
import com.th3pl4gu3.lifestyle.core.enums.SortingValue.*
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder
import com.th3pl4gu3.lifestyle.core.enums.SortingValue
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import java.util.*
import kotlin.collections.ArrayList

/**
 * SharedPrefUtils Class handles all query to the SharedPreferences API
 * It will query the SharedPreferences API and format the data into required object need by the application UI.
 **/
class SharedPrefUtils(private val context: Context, private val name: String) {

    fun getCategories(): ArrayList<String> {
        val list = ArrayList(
            getSharedPrefs().getStringSet(
                context.getString(R.string.ValuePair_forCategories_Key_Categories),
                ArrayList<String>().toSet()
            )
        )

        if(!list.contains("Master List")) list.add("Master List")

        list.sort()

        return list
    }

    fun removeCategory(category: String) {
        val categories = ArrayList(getCategories())
        categories.forEach {
            if (category == it) {
                categories.remove(it)
                return
            }
        }
    }

    fun updateCategories(category: String): Boolean {
        val categories = ArrayList(getCategories())

        if (categories.contains(category)) {
            return false
        }

        categories.add(category)

        with(getSharedPrefs().edit()) {
            putStringSet(context.getString(R.string.ValuePair_forCategories_Key_Categories), categories.toSet())
            apply()
            return true
        }
    }

    fun updateSortOrder(order: String) {
        with(getSharedPrefs().edit()) {
            putString(context.getString(R.string.ValuePair_forSortPreferences_Key_SortOrder), order)
            apply()
        }
    }

    fun updateSortValue(value: String) {
        with(getSharedPrefs().edit()) {
            putString(context.getString(R.string.ValuePair_forSortPreferences_Key_SortValue), value)
            apply()
        }
    }

    fun <L> getSort(): Sort<L> {
        val order = getSharedPrefs().getString(
            context.getString(R.string.ValuePair_forSortPreferences_Key_SortOrder),
            ASC.toString()
        )!!
        val value = getSharedPrefs().getString(
            context.getString(R.string.ValuePair_forSortPreferences_Key_SortValue),
            DATE_ADDED.toString()
        )!!

        return Sort(SortingOrder.valueOf(order), SortingValue.valueOf(value))
    }

    private fun getSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}