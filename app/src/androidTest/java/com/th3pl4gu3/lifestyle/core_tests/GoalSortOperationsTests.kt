package com.th3pl4gu3.lifestyle.core_tests

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.operations.SortOperations
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class GoalSortOperationsTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"
    private val goals: ArrayList<Goal> = ArrayList()
    private var theTitle1 = "Do the dishes "
    private var theTitle2 = "Wash the car "
    private var theTitle3 = "Clean the house "

    private var theCategory1 = "House Chores"
    private var theCategory2 = "Personal"
    private var theCategory3 = "My Hobby"

    @Before
    fun createArrayList() {
        //Add some goals to arraylist
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var goal: Goal

        //Get today's date
        val twoDaysFromNow = Calendar.getInstance()
        val fourDaysFromNow = Calendar.getInstance()

        //Get 2 days from now
        twoDaysFromNow.add(Calendar.DAY_OF_MONTH, 2)

        //Get 4 days from now
        fourDaysFromNow.add(Calendar.DAY_OF_MONTH, 4)

        //Add several to do's in a for loop
        for (x in 1..10) {
            title1 += x
            goal = Goal(title = title1, category = theCategory1)

            //Change the date added
            goal.dateAdded = twoDaysFromNow

            goals.add(goal)

            title1 = theTitle1
        }

        for (x in 1..10) {
            title2 += x
            goal = Goal(title = title2, category = theCategory2)
            goals.add(goal)

            title2 = theTitle2
        }

        for (x in 1..10) {
            title3 += x
            goal = Goal(title = title3, category = theCategory3)

            //Change the date added
            goal.dateAdded = fourDaysFromNow

            goals.add(goal)

            title3 = theTitle3
        }

        //Shuffle the list 3 times for better testing
        goals.shuffle()
        goals.shuffle()
        goals.shuffle()

        //Mark some of them as completed
        for (x in 1..8) {
            goals[x].dateCompleted = Calendar.getInstance()
        }
    }

    @After
    @Throws(IOException::class)
    fun clearArrayList() {
        goals.clear()
    }

    @Test
    @Throws(Exception::class)
    fun sortByTitle_ASC() {

        //Arrange
        val expectedResultRowFirst = "Clean the house 1"
        val expectedResultRowSecond = "Clean the house 10"
        val expectedResultRowBeforeLast = "Wash the car 8"
        val expectedResultRowLast = "Wash the car 9"

        var resultRowFirst: String?
        var resultRowSecond: String?
        var resultRowBeforeLast: String?
        var resultRowLast: String?

        val filteredGoals: List<Goal>
        val order = SortingOrder.ASC

        //Act
        try {
            val sutSort = SortOperations<Goal>()
            sutSort.list = goals
            filteredGoals = sutSort.byTitle(order)

            resultRowFirst = filteredGoals[0].title
            resultRowSecond = filteredGoals[1].title
            resultRowBeforeLast = filteredGoals[filteredGoals.size - 2].title
            resultRowLast = filteredGoals[filteredGoals.size - 1].title
        } catch (ex: Exception) {
            resultRowFirst = ex.message
            resultRowSecond = ex.message
            resultRowBeforeLast = ex.message
            resultRowLast = ex.message
        }

        //Assert
        assertEquals(expectedResultRowFirst, resultRowFirst)
        assertEquals(expectedResultRowSecond, resultRowSecond)
        assertEquals(expectedResultRowBeforeLast, resultRowBeforeLast)
        assertEquals(expectedResultRowLast, resultRowLast)
    }

    @Test
    @Throws(Exception::class)
    fun sortByTitle_DESC() {

        //Arrange
        val expectedResultRowFirst = "Wash the car 9"
        val expectedResultRowSecond = "Wash the car 8"
        val expectedResultRowBeforeLast = "Clean the house 10"
        val expectedResultRowLast = "Clean the house 1"

        var resultRowFirst: String?
        var resultRowSecond: String?
        var resultRowBeforeLast: String?
        var resultRowLast: String?

        val filteredGoals: List<Goal>
        val order = SortingOrder.DESC

        //Act
        try {
            val sutSort = SortOperations<Goal>()
            sutSort.list = goals
            filteredGoals = sutSort.byTitle(order)

            resultRowFirst = filteredGoals[0].title
            resultRowSecond = filteredGoals[1].title
            resultRowBeforeLast = filteredGoals[filteredGoals.size - 2].title
            resultRowLast = filteredGoals[filteredGoals.size - 1].title
        } catch (ex: Exception) {
            resultRowFirst = ex.message
            resultRowSecond = ex.message
            resultRowBeforeLast = ex.message
            resultRowLast = ex.message
        }

        //Assert
        assertEquals(expectedResultRowFirst, resultRowFirst)
        assertEquals(expectedResultRowSecond, resultRowSecond)
        assertEquals(expectedResultRowBeforeLast, resultRowBeforeLast)
        assertEquals(expectedResultRowLast, resultRowLast)
    }

    @Test
    @Throws(Exception::class)
    fun sortByTitle_NoData() {

        //Arrange
        val expectedResult = "Passed"

        var result: String?

        val filteredGoals: List<Goal>
        val order = SortingOrder.DESC

        //Act
        //Clear the list first
        goals.clear()
        //Test the function
        try {
            val sutSort = SortOperations<Goal>()
            sutSort.list = goals
            filteredGoals = sutSort.byTitle(order)
            result = "Passed"
        } catch (ex: Exception) {
            result = ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun sortByCategory_ASC() {

        //Arrange
        val expectedResultRowFirst = "House Chores"
        val expectedResultRowLast = "Personal"

        var resultRowFirst: String?
        var resultRowLast: String?

        val filteredGoals: List<Goal>
        val order = SortingOrder.ASC

        //Act
        try {
            val sutSort = SortOperations<Goal>()
            sutSort.list = goals
            filteredGoals = sutSort.byCategory(order)

            resultRowFirst = filteredGoals[0].category
            resultRowLast = filteredGoals[filteredGoals.size - 1].category
        } catch (ex: Exception) {
            resultRowFirst = ex.message
            resultRowLast = ex.message
        }

        //Assert
        assertEquals(expectedResultRowFirst, resultRowFirst)
        assertEquals(expectedResultRowLast, resultRowLast)
    }

    @Test
    @Throws(Exception::class)
    fun sortByCategory_DESC() {

        //Arrange
        val expectedResultRowFirst = "Personal"
        val expectedResultRowLast = "House Chores"

        var resultRowFirst: String?
        var resultRowLast: String?

        val filteredGoals: List<Goal>
        val order = SortingOrder.DESC

        //Act
        try {
            val sutSort = SortOperations<Goal>()
            sutSort.list = goals
            filteredGoals = sutSort.byCategory(order)

            resultRowFirst = filteredGoals[0].category
            resultRowLast = filteredGoals[filteredGoals.size - 1].category
        } catch (ex: Exception) {
            resultRowFirst = ex.message
            resultRowLast = ex.message
        }

        //Assert
        assertEquals(expectedResultRowFirst, resultRowFirst)
        assertEquals(expectedResultRowLast, resultRowLast)
    }

    @Test
    @Throws(Exception::class)
    fun sortByCategory_NoData() {

        //Arrange
        val expectedResult = "Passed"

        var result: String?

        val filteredGoals: List<Goal>
        val order = SortingOrder.DESC

        //Act
        //Clear the list first
        goals.clear()
        //Test the function
        try {
            val sutSort = SortOperations<Goal>()
            sutSort.list = goals
            filteredGoals = sutSort.byCategory(order)
            result = "Passed"
        } catch (ex: Exception) {
            result = ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

}