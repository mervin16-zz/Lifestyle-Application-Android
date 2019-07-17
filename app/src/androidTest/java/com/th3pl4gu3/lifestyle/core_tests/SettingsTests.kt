package com.th3pl4gu3.lifestyle.core_tests

import android.util.Log
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.core.tuning.Settings
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class SettingsTests {

    private val TESTING_KEYWORD = "LIFESTYLE_SETTINGS"
    private var theGoalTitle = "I am Goal "
    private var theToDoTitle = "I am ToDo "
    private var theToBuyTitle = "I am ToBuy "

    private var theCategory1 = "House Chores"

    private var thePriority1 = Priority.P1

    private var thePrice1 = 14.59

    private var theQuantity = 3

    private lateinit var goalsList: ArrayList<Goal>
    private lateinit var toDoList: ArrayList<ToDo>
    private lateinit var toBuyList: ArrayList<ToBuy>

    private fun generateGoalsList(){
        var title = theGoalTitle
        val category = theCategory1
        var goal: Goal
        goalsList = ArrayList()

        //Add several goals in a for loop
        for (x in 1..3) {
            title += x
            goal = Goal(title = title, category = category)
            //Make changes in some ids for better testing
            goal.id = x.toString()

            goalsList.add(goal)

            title = theGoalTitle
        }
    }
    private fun generateToDoList(){
        var title = theToDoTitle
        val category = theCategory1
        val priority = thePriority1
        var toDo: ToDo
        toDoList = ArrayList()

        //Add several goals in a for loop
        for (x in 1..3) {
            title += x
            toDo = ToDo(title = title, category = category, priority = priority)
            //Make changes in some ids for better testing
            toDo.id = x.toString()

            toDoList.add(toDo)

            title = theToDoTitle
        }
    }
    private fun generateToBuyList(){
        var title = theToBuyTitle
        val category = theCategory1
        val priority = thePriority1
        val price = thePrice1
        val qty = theQuantity
        var toBuy: ToBuy
        toBuyList = ArrayList()

        //Add several goals in a for loop
        for (x in 1..3) {
            title += x
            toBuy = ToBuy(title = title, category = category, priority = priority, estimatedPrice = price, quantity = qty)
            //Make changes in some ids for better testing
            toBuy.id = x.toString()

            toBuyList.add(toBuy)

            title = theToBuyTitle
        }
    }

    @Before
    fun createLists() {
        //Create the goals list
        generateGoalsList()

        //Create the To Do list
        generateToDoList()

        //Create the To Buy list
        generateToBuyList()
    }

    /**
     * Test functions that test individual PRIVATE functions.
     * To run these tests, perform the steps below:
         * 1. Make the private functions in the Settings Class that corresponds to the functions you want to test here public.
         * 2. Uncomment the test function's body.
         * 3. Run the tests.
     **/

    @Test
    fun createJsonBackupForGoals() {
        //Arrange
        val result: String?

        //Act
        val sut_settings = Settings()
        //result = sut_settings.createJsonBackup(goalsList)

        //Assert
        //Log.d(TESTING_KEYWORD, result)
    }

    @Test
    fun createJsonBackupForToDo() {
        //Arrange
        val result: String?

        //Act
        val sut_settings = Settings()
        //result = sut_settings.createJsonBackup(toDoList)

        //Assert
        //Log.d(TESTING_KEYWORD, result)
    }

    @Test
    fun createJsonBackupForToBuy() {
        //Arrange
        val result: String?

        //Act
        val sut_settings = Settings()
        //result = sut_settings.createJsonBackup(toBuyList)

        //Assert
        //Log.d(TESTING_KEYWORD, result)
    }

    @Test
    fun restoreJsonToGoalsList() {
        //Arrange
        val json = "[{\"category\":\"House Chores\",\"id\":\"92445ed9-2c82-4d77-8723-e6bbe959653c\",\"title\":\"I am Goal 1\",\"type\":3,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":13,\"minute\":33,\"second\":12}},{\"category\":\"House Chores\",\"id\":\"4953b74b-365d-450d-8f5e-407d14e6c2bd\",\"title\":\"I am Goal 2\",\"type\":3,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":13,\"minute\":33,\"second\":12}},{\"category\":\"House Chores\",\"id\":\"21717917-b119-47de-9dd5-d2513fbabe03\",\"title\":\"I am Goal 3\",\"type\":3,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":13,\"minute\":33,\"second\":12}}]\n"
        val expectedTitle = "I am Goal 1"
        val expectedCategory = theCategory1
        val expectedDateAdded = Calendar.getInstance()
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: Calendar
        val list: List<Goal>?

        //Set Date Added
        expectedDateAdded.set(2019, 6, 14)

        //Act
        /*val sut_settings = Settings()
        list = sut_settings.restoreGoalsToList(json)
        resultTitle = list[0].title
        resultCategory = list[0].category
        resultDateAdded = list[0].dateAdded

        //Assert
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedDateAdded.time.toString(), resultDateAdded.time.toString())*/
    }

    @Test
    fun restoreJsonToToDoList() {
        //Arrange
        val json = "[{\"category\":\"House Chores\",\"id\":\"1\",\"priority\":\"P1\",\"title\":\"I am ToDo 1\",\"type\":1,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":14,\"minute\":15,\"second\":53}},{\"category\":\"House Chores\",\"id\":\"2\",\"priority\":\"P1\",\"title\":\"I am ToDo 2\",\"type\":1,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":14,\"minute\":15,\"second\":53}},{\"category\":\"House Chores\",\"id\":\"3\",\"priority\":\"P1\",\"title\":\"I am ToDo 3\",\"type\":1,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":14,\"minute\":15,\"second\":53}}]"
        val expectedTitle = "I am ToDo 1"
        val expectedCategory = theCategory1
        val expectedPriority = thePriority1
        val expectedDateAdded = Calendar.getInstance()
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: Calendar
        val resultPriority: Priority
        val list: List<ToDo>?

        //Set Date Added
        expectedDateAdded.set(2019, 6, 14)

        //Act
        /*val sut_settings = Settings()
        list = sut_settings.restoreToDoToList(json)
        resultTitle = list[0].title
        resultCategory = list[0].category
        resultDateAdded = list[0].dateAdded
        resultPriority = list[0].priority

        //Assert
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedPriority, resultPriority)
        assertEquals(expectedDateAdded.time.toString(), resultDateAdded.time.toString())*/
    }

    @Test
    fun restoreJsonToToBuyList() {
        //Arrange
        /*val json = "[{\"category\":\"House Chores\",\"estimatedPrice\":14.59,\"id\":\"1\",\"priority\":\"P1\",\"quantity\":3,\"title\":\"I am ToBuy 1\",\"type\":2,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":14,\"minute\":23,\"second\":42}},{\"category\":\"House Chores\",\"estimatedPrice\":14.59,\"id\":\"2\",\"priority\":\"P1\",\"quantity\":3,\"title\":\"I am ToBuy 2\",\"type\":2,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":14,\"minute\":23,\"second\":42}},{\"category\":\"House Chores\",\"estimatedPrice\":14.59,\"id\":\"3\",\"priority\":\"P1\",\"quantity\":3,\"title\":\"I am ToBuy 3\",\"type\":2,\"dateAdded\":{\"year\":2019,\"month\":6,\"dayOfMonth\":14,\"hourOfDay\":14,\"minute\":23,\"second\":42}}]"
        val expectedTitle = "I am ToBuy 1"
        val expectedCategory = theCategory1
        val expectedPriority = thePriority1
        val expectedPrice = thePrice1
        val expectedQuantity = theQuantity
        val expectedDateAdded = Calendar.getInstance()
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: Calendar
        val resultPriority: Priority
        val resultPrice: Double
        val resultQuantity: Int
        val list: List<ToBuy>?

        //Set Date Added
        expectedDateAdded.set(2019, 6, 14)

        //Act
        val sut_settings = Settings()
        list = sut_settings.restoreToBuyToList(json)
        resultTitle = list[0].title
        resultCategory = list[0].category
        resultDateAdded = list[0].dateAdded
        resultPriority = list[0].priority
        resultPrice = list[0].estimatedPrice
        resultQuantity = list[0].quantity

        //Assert
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedPriority, resultPriority)
        assertEquals(expectedDateAdded.time.toString(), resultDateAdded.time.toString())
        assertEquals(expectedPrice, resultPrice, 0.0)
        assertEquals(expectedQuantity, resultQuantity)*/
    }
}