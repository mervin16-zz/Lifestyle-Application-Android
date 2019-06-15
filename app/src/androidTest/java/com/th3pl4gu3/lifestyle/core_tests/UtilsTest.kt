package com.th3pl4gu3.lifestyle.core_tests

import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

@RunWith(AndroidJUnit4::class)
class UtilsTest {

    @Test
    @Throws(Exception::class)
    fun dateToFormattedString(){

        //Arrange
        val expectedResult = "May 19, 2019"
        val calendar = Calendar.getInstance()
        val result:String

        //Act
        //Set date
        calendar.set(2019, 4, 19)
        //Test the function
        result = Utils.dateToFormattedString(calendar)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun countDaysBetween1Of2(){

        //Arrange
        val expectedResult = 2L
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        val result: Long

        //Act
        //Add days
        startDate.add(Calendar.DAY_OF_MONTH, 2)
        endDate.add(Calendar.DAY_OF_MONTH, 4)
        //Test function
        result = Utils.countDays(startDate, endDate)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun countDaysBetween2Of2(){

        //Arrange
        val expectedResult = 0L
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        val result: Long

        //Act
        result = Utils.countDays(startDate, endDate)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formattedStringToLifestyleEnum_Goal(){

        //Arrange
        val expectedResultItem = LifestyleItem.GOAL
        val lifestyleItem = VALUE_LIFESTYLE_ITEM_GOAL
        var resultItem: LifestyleItem?
        var resultMessage: String?

        //Act
        try{
            resultItem = Utils.formattedStringToLifestyleEnum(lifestyleItem)
            resultMessage = null
        }catch(ex: Exception){
            resultMessage = ex.message
            resultItem = null
        }

        //Assert
        assertEquals(expectedResultItem, resultItem)
        assertNull(resultMessage)
    }

    @Test
    @Throws(Exception::class)
    fun formattedStringToLifestyleEnum_ToDo(){

        //Arrange
        val expectedResultItem = LifestyleItem.TO_DO
        val lifestyleItem = VALUE_LIFESTYLE_ITEM_TODO
        var resultItem: LifestyleItem?
        var resultMessage: String?

        //Act
        try{
            resultItem = Utils.formattedStringToLifestyleEnum(lifestyleItem)
            resultMessage = null
        }catch(ex: Exception){
            resultMessage = ex.message
            resultItem = null
        }

        //Assert
        assertEquals(expectedResultItem, resultItem)
        assertNull(resultMessage)
    }

    @Test
    @Throws(Exception::class)
    fun formattedStringToLifestyleEnum_ToBuy(){

        //Arrange
        val expectedResultItem = LifestyleItem.TO_BUY
        val lifestyleItem = VALUE_LIFESTYLE_ITEM_TOBUY
        var resultItem: LifestyleItem?
        var resultMessage: String?

        //Act
        try{
            resultItem = Utils.formattedStringToLifestyleEnum(lifestyleItem)
            resultMessage = null
        }catch(ex: Exception){
            resultMessage = ex.message
            resultItem = null
        }

        //Assert
        assertEquals(expectedResultItem, resultItem)
        assertNull(resultMessage)
    }

    @Test
    @Throws(Exception::class)
    fun formattedStringToLifestyleEnum_Unknown(){

        //Arrange
        val expectedResultMessage = MESSAGE_EXCEPTION_REQUEST_PROCESSING
        val lifestyleItem = "SomethingElse"
        var resultItem: LifestyleItem?
        var resultMessage: String?

        //Act
        try{
            resultItem = Utils.formattedStringToLifestyleEnum(lifestyleItem)
            resultMessage = null
        }catch(ex: Exception){
            resultMessage = ex.message
            resultItem = null
        }

        //Assert
        assertEquals(expectedResultMessage, resultMessage)
        assertNull(resultItem)
    }

    @Test
    @Throws(Exception::class)
    fun getLifestyleItemEnumsToFormattedString(){

        //Arrange
        val expectedSize = 3
        val resultSize: Int?

        //Act
        val list = Utils.getLifestyleItemEnumsToFormattedString()
        val goal = list.contains(VALUE_LIFESTYLE_ITEM_GOAL)
        val toDo = list.contains(VALUE_LIFESTYLE_ITEM_TODO)
        val toBuy = list.contains(VALUE_LIFESTYLE_ITEM_TOBUY)
        resultSize = list.size

        //Assert
        assertEquals(expectedSize, resultSize)
        assertTrue(goal)
        assertTrue(toDo)
        assertTrue(toBuy)
    }
}