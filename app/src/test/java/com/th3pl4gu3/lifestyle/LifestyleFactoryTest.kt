package com.th3pl4gu3.lifestyle

import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.LifestyleFactory
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.enums.Priority
import org.junit.Assert.*
import org.junit.Test
import java.util.*


class LifestyleFactoryTest {

    @Test
    fun getLifestyleItem_ItemIsToDo() {

        //Arrange
        val itemValue = 1
        val expectedTypeValue = 1
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedPriority = Priority.P4
        val expectedDateAdded = Date().toString()

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultPriority: Priority
        val resultDateAdded: String


        //Act
        val sutToDo = LifestyleFactory.getLifestyleItem(itemValue) as ToDo
        resultTypeValue = sutToDo.type
        resultId = sutToDo.id
        resultTitle = sutToDo.title
        resultCategory = sutToDo.category
        resultPriority = sutToDo.priority
        resultDateAdded = sutToDo.dateAdded.toString()

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedPriority, resultPriority)
        assertEquals(expectedDateAdded, resultDateAdded)

        System.out.println(resultId)
    }

    @Test
    fun getLifestyleItem_ItemIsToBuy() {

        //Arrange
        val itemValue = 2
        val expectedTypeValue = 2
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedEstimatedPrice = 0.0
        val expectedQuantity = 0
        val expectedPriority = Priority.P4
        val expectedDateAdded = Date().toString()

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultEstimatedPrice: Double
        val resultQuantity:Int
        val resultPriority: Priority
        val resultDateAdded: String


        //Act
        val sutToDo = LifestyleFactory.getLifestyleItem(itemValue) as ToBuy
        resultTypeValue = sutToDo.type
        resultId = sutToDo.id
        resultTitle = sutToDo.title
        resultCategory = sutToDo.category
        resultEstimatedPrice = sutToDo.estimatedPrice
        resultQuantity = sutToDo.quantity
        resultPriority = sutToDo.priority
        resultDateAdded = sutToDo.dateAdded.toString()

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedEstimatedPrice, resultEstimatedPrice, 10.0)
        assertEquals(expectedQuantity, resultQuantity)
        assertEquals(expectedPriority, resultPriority)
        assertEquals(expectedDateAdded, resultDateAdded)

        System.out.println(resultId)
    }

    @Test
    fun getLifestyleItem_ItemIsGoal() {

        //Arrange
        val itemValue = 3
        val expectedTypeValue = 3
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedDateAdded = Date().toString()

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String


        //Act
        val sutToDo = LifestyleFactory.getLifestyleItem(itemValue) as Goal
        resultTypeValue = sutToDo.type
        resultId = sutToDo.id
        resultTitle = sutToDo.title
        resultCategory = sutToDo.category
        resultDateAdded = sutToDo.dateAdded.toString()

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedDateAdded, resultDateAdded)

        System.out.println(resultId)
    }
}