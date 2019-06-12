package com.th3pl4gu3.lifestyle.ui.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.th3pl4gu3.lifestyle.core.utils.VALUE_WHITESPACE

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun String.capitalizeEachWords(): String = split(VALUE_WHITESPACE).joinToString(VALUE_WHITESPACE) { it.capitalize() }

inline fun View.snackBarWithAction(message: String, length: Int = Snackbar.LENGTH_SHORT, anchorView: View? = null, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.anchorView = anchorView
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

fun View.snackBar(message: String, length: Int = Snackbar.LENGTH_SHORT, anchorView: View? = null) {
    Snackbar.make(this, message, length).setAnchorView(anchorView).show()
}