package com.th3pl4gu3.lifestyle.ui.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Lifestyle
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.Utils

/**
* Lifestyle Item Data Bindings
 **/
@BindingAdapter(VALUE_BINDING_LIFESTYLE_ITEM_TITLE)
fun TextView.setLifestyleItemTitle(lifestyle: Lifestyle){
    lifestyle.let {
        text = lifestyle.title
    }
}

@BindingAdapter(VALUE_BINDING_LIFESTYLE_ITEM_CATEGORY)
fun TextView.setLifestyleItemCategory(lifestyle: Lifestyle){
    lifestyle.let {
        text = lifestyle.category
    }
}

@BindingAdapter(VALUE_BINDING_LIFESTYLE_ITEM_DATEADDED)
fun TextView.setLifestyleItemDateAdded(lifestyle: Lifestyle){
    lifestyle.let {
        text = Utils.dateToFormattedString(lifestyle.dateAdded)
    }
}

@BindingAdapter(VALUE_BINDING_LIFESTYLE_ITEM_DAYSOLD)
fun TextView.setLifestyleItemDaysOld(lifestyle: Lifestyle){
    lifestyle.let {
        text = "Dummy"
    }
}

@BindingAdapter(VALUE_BINDING_LIFESTYLE_ITEM_SWIPINGMARKINGICON)
fun ImageView.setLifestyleItemSwipingMarkingIcon(lifestyle: Lifestyle) {
    if(lifestyle.dateCompleted != null){
        setImageResource(R.drawable.ic_close_light)
    }else{
        setImageResource(R.drawable.ic_done_light)
    }
}

@BindingAdapter(VALUE_BINDING_LIFESTYLE_ITEM_PRIORITY)
fun ImageView.setLifestyleItemPriority(toDo: ToDo) {
    setImageResource(when (toDo.priority) {
        Priority.P1 -> R.drawable.ic_priority_1
        Priority.P2 -> R.drawable.ic_priority_2
        Priority.P3 -> R.drawable.ic_priority_3
        Priority.P4 -> R.drawable.ic_priority_4
    })
}

@BindingAdapter(VALUE_BINDING_LIFESTYLE_ITEM_PRIORITY)
fun ImageView.setLifestyleItemPriority(toBuy: ToBuy) {
    setImageResource(when (toBuy.priority) {
        Priority.P1 -> R.drawable.ic_priority_1
        Priority.P2 -> R.drawable.ic_priority_2
        Priority.P3 -> R.drawable.ic_priority_3
        Priority.P4 -> R.drawable.ic_priority_4
    })
}

/**
 * To Buy Item Data Bindings
 **/

@BindingAdapter(VALUE_BINDING_TOBUY_ITEM_PRICE)
fun TextView.setToBuyPrice(toBuy: ToBuy){
    toBuy.let {
        text = toBuy.estimatedPrice.toString()
    }
}

@BindingAdapter(VALUE_BINDING_TOBUY_ITEM_QTY)
fun TextView.setToBuyQuantity(toBuy: ToBuy){
    toBuy.let {
        text = toBuy.quantity.toString()
    }
}

@BindingAdapter(VALUE_BINDING_TOBUY_ITEM_TOTAL)
fun TextView.setToBuyTotal(toBuy: ToBuy){
    toBuy.let {
        text = "Dummy"
    }
}