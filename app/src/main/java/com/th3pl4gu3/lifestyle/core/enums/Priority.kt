package com.th3pl4gu3.lifestyle.core.enums

import com.th3pl4gu3.lifestyle.core.utils.*

/**
 * Priority Enum Class to determine the level of priority of Lifestyle Items who has the priority field.
 * Priority has 4 severity levels namely:
 * @see Priority.P1
 * @see Priority.P2
 * @see Priority.P3
 * @see Priority.P4
 **/
enum class Priority(val value: Int) {
    P1(ENUM_PRIORITY_P1),
    P2(ENUM_PRIORITY_P2),
    P3(ENUM_PRIORITY_P3),
    P4(ENUM_PRIORITY_P4)
}