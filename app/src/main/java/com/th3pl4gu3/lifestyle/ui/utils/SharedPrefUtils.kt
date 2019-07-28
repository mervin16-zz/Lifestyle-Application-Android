package com.th3pl4gu3.lifestyle.ui.utils

import android.content.Context
import android.content.SharedPreferences
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder.*
import com.th3pl4gu3.lifestyle.core.enums.SortingValue.*
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder
import com.th3pl4gu3.lifestyle.core.enums.SortingValue
import com.th3pl4gu3.lifestyle.core.tuning.Sort
import kotlin.collections.ArrayList

/**
 * SharedPrefUtils Class handles all query to the SharedPreferences API
 * It will query the SharedPreferences API and format the data into required object need by the application UI.
 **/
class SharedPrefUtils(private val context: Context, private val name: String) {


    /**
     * Lifestyle Items' Category Manipulations
     **/

    fun getCategories(): ArrayList<String> {
        val list = ArrayList(
            getSharedPrefs().getStringSet(
                context.getString(R.string.ValuePair_forCategories_Key_Categories),
                ArrayList<String>().toSet()
            )
        )

        val masterListValue = context.getString(R.string.Spinner_fromAddItemActivity_Category_Item_Master)

        if (!list.contains(masterListValue)) list.add(masterListValue)

        list.sort()

        return list
    }

    fun removeCategory(category: String) {
        val categories = getCategories()
        categories.remove(category)

        with(getSharedPrefs().edit()) {
            putStringSet(context.getString(R.string.ValuePair_forCategories_Key_Categories), categories.toSet())
            apply()
        }
    }

    fun updateCategories(category: String): Boolean {
        val categories = getCategories()

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


    /**
     * Lifestyle Items' Sorting Manipulations
     **/

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


    /**
     * Fetch the SharedPreferences
     **/
    private fun getSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}