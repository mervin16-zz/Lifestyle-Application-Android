package com.th3pl4gu3.lifestyle.ui.Utils

import android.content.Context
import android.widget.Toast
import com.th3pl4gu3.lifestyle.core.utils.VALUE_WHITESPACE

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun String.capitalizeEachWords(): String = split(VALUE_WHITESPACE).joinToString(VALUE_WHITESPACE) { it.capitalize() }