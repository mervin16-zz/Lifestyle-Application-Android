package com.th3pl4gu3.lifestyle.ui

import android.content.Context
import android.widget.ArrayAdapter

class SpinnerAdapter(context: Context, textViewResourceId: Int) : ArrayAdapter<String>(context, textViewResourceId) {

    override fun getCount(): Int {
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }

}