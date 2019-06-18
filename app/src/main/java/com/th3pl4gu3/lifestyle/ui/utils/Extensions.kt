package com.th3pl4gu3.lifestyle.ui.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.th3pl4gu3.lifestyle.core.utils.Utils
import java.util.*

//Extension to display a toast on the given context's screen
fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

//Extension to display a SnackBar with an action
inline fun View.snackBarWithAction(message: String, length: Int = Snackbar.LENGTH_SHORT, anchorView: View? = null, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.anchorView = anchorView
    snack.show()
}

//The action of the SnackBar
fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

//Extension to display a simple SnackBar with no action.
fun View.snackBar(message: String, length: Int = Snackbar.LENGTH_SHORT, anchorView: View? = null) {
    Snackbar.make(this, message, length).setAnchorView(anchorView).show()
}

//Extension function to format a Calendar object to a formatted String version
fun Calendar.toFormattedReadableDate() = Utils.dateToFormattedString(this)

//Extension function to capitalize each words in a sentence
fun String.capitalizeEachWords() = Utils.capitalizeEachWords(this)

//Extension function to abbreviate a string after a given length
fun String.abbreviate(length: Int) = Utils.abbreviate(this, length)

//Extension function to format number of days active
fun Int.formatDaysActive() = Utils.formatDaysActive(this)

//Extension function to format a double into a currency
fun Double.toCurrency() = Utils.toCurrency(this)