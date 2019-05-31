package com.th3pl4gu3.lifestyle.core.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun String.capitalizeEachWords(): String = split(VALUE_WHITESPACE).joinToString(VALUE_WHITESPACE) { it.capitalize() }