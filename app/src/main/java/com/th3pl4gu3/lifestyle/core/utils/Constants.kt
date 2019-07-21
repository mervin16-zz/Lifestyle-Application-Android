package com.th3pl4gu3.lifestyle.core.utils


/********************************* Application Files & Directories *********************************/

// Directories //
const val APPLICATION_DIRECTORY_BACKUP_NAME = "Lifestyle_Backup"

// Files //
const val APPLICATION_FILES_BACKUP_GOALS_NAME= "Goals.bkp"
const val APPLICATION_FILES_BACKUP_TODOS_NAME= "ToDos.bkp"
const val APPLICATION_FILES_BACKUP_TOBUYS_NAME= "ToBuys.bkp"


/********************************* Values *********************************/

// Regex Values //
const val VALUE_REGEX_WHITESPACES_SEQUENCE = "\\s+"

// Suppressed Values //
const val VALUE_SUPPRESSED_UNCHECKED_CAST = "unchecked_cast"

// General Values //
const val VALUE_WHITESPACE = " "
const val VALUE_UNDERSCORE = "_"
const val VALUE_SLASH_FRONT = "/"

// Lifestyle Items String Values //
const val VALUE_LIFESTYLE_ITEM_GOAL = "Goal"
const val VALUE_LIFESTYLE_ITEM_TODO = "To Do"
const val VALUE_LIFESTYLE_ITEM_TOBUY = "To Buy"

// Currency //
const val VALUE_CURRENCY_SYMBOL_RS= "Rs "

// Notifications //
const val VALUE_NOTIFICATION_ID_BACKUP_RESTORE_ = 16
const val VALUE_NOTIFICATION_CHANNEL_ID_BACKUP_RESTORE_ = "backup_&_restore_notification_channel"
const val VALUE_NOTIFICATION_CHANNEL_NAME_BACKUP_RESTORE_ = "Backup & Restoration"
const val VALUE_NOTIFICATION_CHANNEL_DESCRIPTION_BACKUP_RESTORE_ = "Performing Backup and Restoration on your Tasks."

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

// Days //
const val PLACEHOLDER_DAYS_RECENTLY = "Added Recently"
const val PLACEHOLDER_DAYS_1 = "1 Day"
const val PLACEHOLDER_DAYS_MULTIPLE = "Days"
const val PLACEHOLDER_MONTHS_1 = "1 Month"
const val PLACEHOLDER_MONTHS_MULTIPLE = "Months"
const val PLACEHOLDER_YEARS_1 = "1 Year"
const val PLACEHOLDER_YEARS_MULTIPLE = "Years"


/********************************* Messages *********************************/

// Error Messages //
const val MESSAGE_EXCEPTION_GOAL_NOT_EXIST = "Error while fetching your Goal. This Goal doesn't exist."
const val MESSAGE_EXCEPTION_TODO_NOT_EXIST = "Error while fetching your To Do. This To Do task doesn't exist."
const val MESSAGE_EXCEPTION_TOBUY_NOT_EXIST = "Error while fetching your To Buy. This To Buy task doesn't exist."
const val MESSAGE_EXCEPTION_DAYSACTIVE_ERROR = "An error has occurred while processing the active days in your task. Please try again or refresh the screen."
const val MESSAGE_EXCEPTION_REQUEST_PROCESSING = "An error has occurred while processing your request. Please try again."
const val MESSAGE_EXCEPTION_BACKUP_CREATION_LOCALLY_SETTINGS = "An error has occurred while creating your backup. Please try again."
const val MESSAGE_EXCEPTION_RESTORATION_LOCALLY_SETTINGS = "An error has occurred while performing your restore. Please try again."
const val MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_ADDITEM = "An internal error occurred: Unknown View Model when trying to fetch item."
const val MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_GOALS = "An internal error occurred: Unknown View Model when trying to fetch your goals."
const val MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_TODOS = "An internal error occurred: Unknown View Model when trying to fetch your to dos."
const val MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_TOBUYS = "An internal error occurred: Unknown View Model when trying to fetch your to buys."
const val MESSAGE_EXCEPTION_UNKNOWN_VIEWMODEL_SETTINGS = "An internal error occurred: Unknown View Model when trying to fetch your settings."
const val MESSAGE_EXCEPTION_SORT_GOALS = "An internal error occurred: Cannot process field 'priority' for your Goals"
const val MESSAGE_EXCEPTION_SORT_OTHERS = "An internal error occurred: Cannot process field 'priority'"