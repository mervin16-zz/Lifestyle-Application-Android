package com.th3pl4gu3.lifestyle.core_tests

import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class GoalFilterOperationsTests {

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

        //Add several to do's in a for loop
        for (x in 1..10) {
            title1 += x
            goal = Goal(title = title1, category = theCategory1)
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
            goals.add(goal)

            title3 = theTitle3
        }

        //Shuffle the list for better testing
        goals.shuffle()

        //Mark some of them as completed
        for(x in 1..8){
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
    fun getByCategory_CategoryExists() {

        //Arrange
        val searchCategory = "house chores"
        val expectedSize = 10 //do the dishes 1 -10
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.byCategory(searchCategory)
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByCategory_CategoryDoesntExists() {

        //Arrange
        val searchCategory = "No Name"
        val expectedSize = 0
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.byCategory(searchCategory)
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByCategory_NoDataAvailable() {

        //Arrange
        val searchCategory = "No Name"
        val expectedSize = 0
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        //Clear the arrayList
        goals.clear()
        // Test the function
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.byCategory(searchCategory)
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_SomeHaveBeenCompleted() {

        //Arrange
        val expectedSize = 8
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.getCompleted()
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_NoneHaveBeenCompleted() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        //Mark all as not completed
        for(goal in goals){
            goal.dateCompleted = null
        }

        //Test the function
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.getCompleted()
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_NoDataAvailable() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        //Clear the arrayList
        goals.clear()
        // Test the function
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.getCompleted()
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_SomeHaveBeenCompleted() {

        //Arrange
        val expectedSize = 22
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.getActive()
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_NoneAreActive() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        //Mark all as completed
        for(goal in goals){

            if(goal.dateCompleted == null){
                goal.dateCompleted = Calendar.getInstance()
            }
        }

        //Test the function
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.getActive()
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_NoDataAvailable() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredGoals: List<Goal>

        //Act
        //Clear the arrayList
        goals.clear()
        // Test the function
        val sutFilter = FilterOperations<Goal>(goals)
        filteredGoals = sutFilter.getActive()
        resultSize = filteredGoals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }
}