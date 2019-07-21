package com.th3pl4gu3.lifestyle.ui.utils

import android.content.Context
import android.content.SharedPreferences
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder.*
import com.th3pl4gu3.lifestyle.core.enums.SortingValue.*
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder
import com.th3pl4gu3.lifestyle.core.enums.SortingValue
import com.th3pl4gu3.lifestyle.core.tuning.Sort

class SharedPrefUtils(private val context: Context, val name: String) {

    fun updateSortOrder(order: String){
        with(getSharedPrefs().edit()){
            putString(context.getString(R.string.ValuePair_forSortPreferences_Key_SortOrder), order)
            apply()
        }
    }

    fun updateSortValue(value: String){
        with(getSharedPrefs().edit()){
            putString(context.getString(R.string.ValuePair_forSortPreferences_Key_SortValue), value)
            apply()
        }
    }

    fun <L> getSort(): Sort<L>{
        val order = getSharedPrefs().getString(context.getString(R.string.ValuePair_forSortPreferences_Key_SortOrder), ASC.toString())!!
        val value = getSharedPrefs().getString(context.getString(R.string.ValuePair_forSortPreferences_Key_SortValue), DATE_ADDED.toString())!!

        return Sort(SortingOrder.valueOf(order), SortingValue.valueOf(value))
    }

    private fun getSharedPrefs(): SharedPreferences{
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}