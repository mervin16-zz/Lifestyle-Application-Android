package com.th3pl4gu3.lifestyle.core.lifestyle

import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import java.lang.IllegalStateException

class LifestyleFactory {
    companion object {
        fun getLifestyleItem(value: Int) = when (value){
            LifestyleItem.TO_BUY.value -> ToBuy()
            LifestyleItem.TO_DO.value -> ToDo()
            LifestyleItem.GOAL.value -> Goal()
            else -> throw IllegalStateException("Unknown Format")
        }
    }
}