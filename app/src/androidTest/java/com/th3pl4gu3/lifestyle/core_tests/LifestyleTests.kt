package com.th3pl4gu3.lifestyle.core_tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.Utils
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Test
import java.util.*

@RunWith(AndroidJUnit4::class)
class LifestyleTests {


    @Test
    @Throws(Exception::class)
    fun markAsCompletedInToBuy() {

        //Arrange
        val toBuy = ToBuy()
        val expectedDateCompleted = Utils.dateToFormattedString(Calendar.getInstance())

        val resultDateCompleted: String?

        //Act
        toBuy.markAsComplete()
        resultDateCompleted = Utils.dateToFormattedString(toBuy.dateCompleted!!)

        //Assert
        assertEquals(expectedDateCompleted, resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun markAsIncompleteInToBuy() {

        //Arrange
        val toBuy = ToBuy()
        toBuy.dateCompleted = Calendar.getInstance()

        val resultDateCompleted: Calendar?

        //Act
        toBuy.markAsIncomplete()
        resultDateCompleted = toBuy.dateCompleted

        //Assert
        assertNull(resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun markAsCompletedInGoal() {

        //Arrange
        val goal = Goal()
        val expectedDateCompleted = Utils.dateToFormattedString(Calendar.getInstance())

        val resultDateCompleted: String?

        //Act
        goal.markAsComplete()
        resultDateCompleted = Utils.dateToFormattedString(goal.dateCompleted!!)

        //Assert
        assertEquals(expectedDateCompleted, resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun markAsIncompleteInGoal() {

        //Arrange
        val goal = Goal()
        goal.dateCompleted = Calendar.getInstance()

        val resultDateCompleted: Calendar?

        //Act
        goal.markAsIncomplete()
        resultDateCompleted = goal.dateCompleted

        //Assert
        assertNull(resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun markAsCompletedInToDo() {

        //Arrange
        val toDo = ToDo()
        val expectedDateCompleted = Utils.dateToFormattedString(Calendar.getInstance())

        val resultDateCompleted: String?

        //Act
        toDo.markAsComplete()
        resultDateCompleted = Utils.dateToFormattedString(toDo.dateCompleted!!)

        //Assert
        assertEquals(expectedDateCompleted, resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun markAsIncompleteInToDo() {

        //Arrange
        val toDo = ToDo()
        toDo.dateCompleted = Calendar.getInstance()

        val resultDateCompleted: Calendar?

        //Act
        toDo.markAsIncomplete()
        resultDateCompleted = toDo.dateCompleted

        //Assert
        assertNull(resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun daysActiveInToDo() {

        //Arrange
        val expectedDaysActive = 10
        val dateAdded = Calendar.getInstance()
        //Configure Date added
        dateAdded.add(Calendar.DAY_OF_MONTH, -10)
        val toDo = ToDo()
        toDo.dateAdded = dateAdded

        val resultDaysActive: Int?

        //Act
        resultDaysActive = toDo.daysActive

        //Assert
        assertEquals(expectedDaysActive, resultDaysActive)
    }

    @Test
    @Throws(Exception::class)
    fun daysActiveInToBuy() {

        //Arrange
        val expectedDaysActive = 10
        val dateAdded = Calendar.getInstance()
        //Configure Date added
        dateAdded.add(Calendar.DAY_OF_MONTH, -10)
        val toBuy = ToBuy()
        toBuy.dateAdded = dateAdded

        val resultDaysActive: Int?

        //Act
        resultDaysActive = toBuy.daysActive

        //Assert
        assertEquals(expectedDaysActive, resultDaysActive)
    }

    @Test
    @Throws(Exception::class)
    fun daysActiveInGoal() {

        //Arrange
        val expectedDaysActive = 10
        val dateAdded = Calendar.getInstance()
        //Configure Date added
        dateAdded.add(Calendar.DAY_OF_MONTH, -10)
        val goal = Goal()
        goal.dateAdded = dateAdded

        val resultDaysActive: Int?

        //Act
        resultDaysActive = goal.daysActive

        //Assert
        assertEquals(expectedDaysActive, resultDaysActive)
    }

    @Test
    @Throws(Exception::class)
    fun isCompletedInToDo_ItHasBeenCompleted() {

        //Arrange
        val dateCompleted = Calendar.getInstance()
        val toDo = ToDo()
        toDo.dateCompleted = dateCompleted

        val result: Boolean

        //Act
        result = toDo.isCompleted

        //Assert
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun isCompletedInToDo_ItHasNotBeenCompleted() {

        //Arrange
        val toDo = ToDo()

        val result: Boolean

        //Act
        result = toDo.isCompleted

        //Assert
        assertFalse(result)
    }


    @Test
    @Throws(Exception::class)
    fun isCompletedInToBuy_ItHasBeenCompleted() {

        //Arrange
        val dateCompleted = Calendar.getInstance()
        val toBuy = ToBuy()
        toBuy.dateCompleted = dateCompleted

        val result: Boolean

        //Act
        result = toBuy.isCompleted

        //Assert
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun isCompletedInToBuy_ItHasNotBeenCompleted() {

        //Arrange
        val toBuy = ToBuy()

        val result: Boolean

        //Act
        result = toBuy.isCompleted

        //Assert
        assertFalse(result)
    }


    @Test
    @Throws(Exception::class)
    fun isCompletedInGoal_ItHasBeenCompleted() {

        //Arrange
        val dateCompleted = Calendar.getInstance()
        val goal = Goal()
        goal.dateCompleted = dateCompleted

        val result: Boolean

        //Act
        result = goal.isCompleted

        //Assert
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun isCompletedInGoal_ItHasNotBeenCompleted() {

        //Arrange
        val goal = Goal()

        val result: Boolean

        //Act
        result = goal.isCompleted

        //Assert
        assertFalse(result)
    }
}