package com.th3pl4gu3.lifestyle.ui.utils

import android.widget.EditText

class Validation {

    val errors = HashMap<EditText, String>()

    fun checkIfEmpty(editText: EditText): Boolean{
        if(editText.text.isNullOrEmpty() || editText.text.isNullOrBlank()){
            errors[editText] = MESSAGE_ERROR_EMPTY_FIELD
            return true
        }

        return false
    }

    fun checkIfIntegerGreaterThanZero(editText: EditText){
        if(checkIfEmpty(editText)){
            errors[editText] = MESSAGE_ERROR_EMPTY_FIELD
            return
        }

        if(editText.text.toString().toInt() < 1){
            errors[editText] = MESSAGE_ERROR_NUMBER_VALID
        }
    }

    fun checkIfDoubleGreaterThanZero(editText: EditText){
        if(checkIfEmpty(editText)){
            errors[editText] = MESSAGE_ERROR_EMPTY_FIELD
            return
        }

        if(editText.text.toString().toDouble() < 1.0){
            errors[editText] = MESSAGE_ERROR_NUMBER_VALID
        }
    }

    fun hasErrors() = errors.size > 0

    fun clear() = errors.clear()
}

