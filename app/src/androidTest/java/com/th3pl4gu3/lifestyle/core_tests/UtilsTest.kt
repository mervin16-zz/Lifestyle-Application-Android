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
        val expectedResult = 2
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        val result: Int

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
        val expectedResult = 0
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        val result: Int

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


    @Test
    @Throws(Exception::class)
    fun toCurrency1Of3(){

        //Arrange
        val expectedResult = "Rs 100.50"
        val value = 100.5
        val result: String?

        //Act
        result = Utils.toCurrency(value)

        //Assert
        assertEquals(expectedResult, result)
    }


    @Test
    @Throws(Exception::class)
    fun toCurrency2Of3(){

        //Arrange
        val expectedResult = "Rs 1,000.12"
        val value = 1000.122546
        val result: String?

        //Act
        result = Utils.toCurrency(value)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun toCurrency3Of3(){

        //Arrange
        val expectedResult = "Rs 1,000,050.00"
        val value = 1000050.0023
        val result: String?

        //Act
        result = Utils.toCurrency(value)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_Recently(){

        //Arrange
        val daysActive = 0
        val expectedResult = "Added Recently"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_InvalidDays1Of2(){

        //Arrange
        val daysActive = -56
        val expectedResult = "An error has occurred while processing the active days in your task. Please try again or refresh the screen."
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_InvalidDays2Of2(){

        //Arrange
        val daysActive = -10
        val expectedResult = "An error has occurred while processing the active days in your task. Please try again or refresh the screen."
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingDays1Of4(){

        //Arrange
        val daysActive = 1
        val expectedResult = "1 Day"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingDays2Of4(){

        //Arrange
        val daysActive = 2
        val expectedResult = "2 Days"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingDays3Of4(){

        //Arrange
        val daysActive = 30
        val expectedResult = "30 Days"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }


    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingDays4Of4(){

        //Arrange
        val daysActive = 25
        val expectedResult = "25 Days"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingMonths1Of7(){

        //Arrange
        val daysActive = 31
        val expectedResult = "1 Month"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingMonths2Of7(){

        //Arrange
        val daysActive = 32
        val expectedResult = "1 Month"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingMonths3Of7(){

        //Arrange
        val daysActive = 61
        val expectedResult = "1 Month"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingMonths4Of7(){

        //Arrange
        val daysActive = 60
        val expectedResult = "1 Month"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingMonths5Of7(){

        //Arrange
        val daysActive = 62
        val expectedResult = "2 Months"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingMonths6Of7(){

        //Arrange
        val daysActive = 364
        val expectedResult = "11 Months"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingMonths7Of7(){

        //Arrange
        val daysActive = 126
        val expectedResult = "4 Months"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingYears1Of3(){

        //Arrange
        val daysActive = 365
        val expectedResult = "1 Year"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingYears2Of3(){

        //Arrange
        val daysActive = 500
        val expectedResult = "1 Year"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun formatDaysActive_CalculatingYears3Of3(){

        //Arrange
        val daysActive = 1401
        val expectedResult = "3 Years"
        val result: String?

        //Act
        result = try{
            Utils.formatDaysActive(daysActive)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun capitalizeEachWords1Of3(){

        //Arrange
        val sentence = "longituDe"
        val expectedResult = "Longitude"
        val result: String?

        //Act
        result = try{
            Utils.capitalizeEachWords(sentence)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun capitalizeEachWords2Of3(){

        //Arrange
        val sentence = "this is a Sentence that i Am prouD of ."
        val expectedResult = "This Is A Sentence That I Am Proud Of ."
        val result: String?

        //Act
        result = try{
            Utils.capitalizeEachWords(sentence)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun capitalizeEachWords3Of3(){

        //Arrange
        val sentence = "this is a Sentence     Game"
        val expectedResult = "This Is A Sentence Game"
        val result: String?

        //Act
        result = try{
            Utils.capitalizeEachWords(sentence)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun abbreviate1Of2(){

        //Arrange
        val sentence = "I ahs aasdj"
        val length = 8
        val expectedResult = "I ahs..."
        val result: String?

        //Act
        result = try{
            Utils.abbreviate(sentence, length)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun abbreviate2Of2(){

        //Arrange
        val sentence = "I ahs aasdj"
        val length = 50
        val expectedResult = "I ahs aasdj"
        val result: String?

        //Act
        result = try{
            Utils.abbreviate(sentence, length)
        }catch (ex: Exception){
            ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }
}