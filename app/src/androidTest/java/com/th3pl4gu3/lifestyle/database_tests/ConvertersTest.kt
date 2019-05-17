package com.th3pl4gu3.lifestyle.database_tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.database.Converters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConvertersTest {

    @Test
    @Throws(Exception::class)
    fun fromEnumToInt_P1() {
        //Arrange
        val enum  = Priority.P1
        val expectedResult = 1
        val result: Int?

        //Act
        result = Converters.fromEnumToInt(enum)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun fromEnumToInt_P2() {
        //Arrange
        val enum  = Priority.P2
        val expectedResult = 2
        val result: Int?

        //Act
        result = Converters.fromEnumToInt(enum)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun fromEnumToInt_P3() {
        //Arrange
        val enum  = Priority.P3
        val expectedResult = 3
        val result: Int?

        //Act
        result = Converters.fromEnumToInt(enum)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun fromEnumToInt_P4() {
        //Arrange
        val enum  = Priority.P4
        val expectedResult = 4
        val result: Int?

        //Act
        result = Converters.fromEnumToInt(enum)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun fromIntToEnum_P1() {
        //Arrange
        val integer  = 1
        val expectedResult = Priority.P1
        val result: Priority?

        //Act
        result = Converters.fromIntToEnum(integer)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun fromIntToEnum_P2() {
        //Arrange
        val integer  = 2
        val expectedResult = Priority.P2
        val result: Priority?

        //Act
        result = Converters.fromIntToEnum(integer)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun fromIntToEnum_P3() {
        //Arrange
        val integer  = 3
        val expectedResult = Priority.P3
        val result: Priority?

        //Act
        result = Converters.fromIntToEnum(integer)

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun fromIntToEnum_P4() {
        //Arrange
        val integer  = 4
        val expectedResult = Priority.P4
        val result: Priority?

        //Act
        result = Converters.fromIntToEnum(integer)

        //Assert
        assertEquals(expectedResult, result)
    }
}