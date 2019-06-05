package com.th3pl4gu3.lifestyle.core_tests

import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.utils.Utils
import org.junit.After
import org.junit.Assert.assertEquals
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
}