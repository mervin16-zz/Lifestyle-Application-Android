package com.th3pl4gu3.lifestyle.core.enums

import com.th3pl4gu3.lifestyle.core.utils.*

/**
 * LifestyleItem Enum Class to determine which Lifestyle Item is in use
 * LifestyleItem has 3 types for now namely:
 * @see LifestyleItem.TO_DO -> A to do list
 * @see LifestyleItem.TO_BUY -> A to buy list
 * @see LifestyleItem.GOAL -> A goal list
 **/
enum class LifestyleItem(val value: Int) {
    TO_DO(ENUM_ITEM_LIFESTYLE_TODO),
    TO_BUY(ENUM_ITEM_LIFESTYLE_TOBUY),
    GOAL(ENUM_ITEM_LIFESTYLE_GOAL)
}