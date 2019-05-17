package com.th3pl4gu3.lifestyle.core.utils


/********************************* Enums *********************************/

// Lifestyle Item //
const val ENUM_ITEM_LIFESTYLE_TODO = 1
const val ENUM_ITEM_LIFESTYLE_TOBUY = 2
const val ENUM_ITEM_LIFESTYLE_GOAL = 3

// Priority Item //
const val ENUM_PRIORITY_P1 = 1
const val ENUM_PRIORITY_P2 = 2
const val ENUM_PRIORITY_P3 = 3
const val ENUM_PRIORITY_P4 = 4


/********************************* Database *********************************/

const val DATABASE_NAME = "lifestyle_history_database"

// Table:: Goal //
const val DATABASE_TABLE_GOAL = "goal_table"
const val DATABASE_TABLE_GOAL_TITLE = "goal_title"
const val DATABASE_TABLE_GOAL_CATEGORY = "goal_category"
const val DATABASE_TABLE_GOAL_DATEADDED = "goal_dateAdded"
const val DATABASE_TABLE_GOAL_DATECOMPLETED = "goal_dateCompleted"

// Table:: ToBuy //
const val DATABASE_TABLE_TOBUY = "toBuy_table"
const val DATABASE_TABLE_TOBUY_TITLE = "toBuy_title"
const val DATABASE_TABLE_TOBUY_CATEGORY = "toBuy_category"
const val DATABASE_TABLE_TOBUY_PRICE = "toBuy_price"
const val DATABASE_TABLE_TOBUY_QTY = "toBuy_quantity"
const val DATABASE_TABLE_TOBUY_PRIORITY = "toBuy_priority"
const val DATABASE_TABLE_TOBUY_DATEADDED = "toBuy_dateAdded"
const val DATABASE_TABLE_TOBUY_DATECOMPLETED = "toBuy_dateCompleted"

// Table:: To Do //
const val DATABASE_TABLE_TODO = "toDo_table"
const val DATABASE_TABLE_TODO_TITLE = "toDo_title"
const val DATABASE_TABLE_TODO_CATEGORY = "toDo_category"
const val DATABASE_TABLE_TODO_PRIORITY = "toDo_priority"
const val DATABASE_TABLE_TODO_DATEADDED = "toDo_dateAdded"
const val DATABASE_TABLE_TODO_DATECOMPLETED = "toDo_dateCompleted"


/********************************* Placeholders *********************************/

// Lifestyle Items //
const val PLACEHOLDER_ITEM_LIFESTYLE_TITLE = "Title"
const val PLACEHOLDER_ITEM_LIFESTYLE_CATEGORY = "Category"
const val PLACEHOLDER_ITEM_LIFESTYLE_ESTIMATEDPRICE = 0.0
const val PLACEHOLDER_ITEM_LIFESTYLE_QTY = 0