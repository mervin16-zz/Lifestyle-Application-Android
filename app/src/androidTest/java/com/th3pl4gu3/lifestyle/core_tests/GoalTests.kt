package com.th3pl4gu3.lifestyle.core_tests


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.GoalDao
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class GoalTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var goalDao: GoalDao
    private lateinit var db: LifestyleDatabase

    private var theTitle1 = "Do the dishes "
    private var theTitle2 = "Wash the car "
    private var theTitle3 = "Clean the house "

    private var theCategory1 = "House Chores"
    private var theCategory2 = "Personal"
    private var theCategory3 = "My Hobby"

    private fun initializeDbWithData(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, LifestyleDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        goalDao = db.goalDao
    }

    private fun generateGoalsInDb(){
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var goal: Goal

        //Add several goals in a for loop
        for (x in 1..10) {
            title1 += x
            goal = Goal(title = title1, category = theCategory1)
            //Make changes in some ids for better testing
            goal.id = x.toString()
            goalDao.insert(goal)

            title1 = theTitle1
        }

        for (x in 1..10) {
            title2 += x
            goal = Goal(title = title2, category = theCategory2)
            goalDao.insert(goal)

            title2 = theTitle2
        }

        for (x in 1..10) {
            title3 += x
            goal = Goal(title = title3, category = theCategory3)
            goalDao.insert(goal)

            title3 = theTitle3
        }
    }

    @Before
    fun createDb() {
        //Create the database
        initializeDbWithData()

        //Add some Goals in the database
        generateGoalsInDb()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addGoal() {

        //Arrange
        val goal = Goal()
        val id = goal.id
        val dateNow = Utils.dateToFormattedString(goal.dateAdded)
        val expectedId = id
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedDateAdded = dateNow
        val expectedTypeValue = LifestyleItem.GOAL.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        goal.add(db)
        //Fetch the goal for testing
        val sutGoal = goalDao.get(id)

        resultTypeValue = sutGoal!!.type
        resultId = sutGoal.id
        resultTitle = sutGoal.title
        resultCategory = sutGoal.category
        resultDateAdded = Utils.dateToFormattedString(sutGoal.dateAdded)
        resultDateCompleted = sutGoal.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedDateAdded, resultDateAdded)
        assertNull(resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun updateGoal() {

        //Arrange
        val id = "4"
        val title = "Do the dishes 44"
        val category = "House Chores 44"

        val newGoal = Goal(title = title, category = category)

        //set the ID
        newGoal.id = id

        val expectedId = id
        val expectedTitle = title
        val expectedCategory = category
        val expectedDateAdded = Utils.dateToFormattedString(newGoal.dateAdded)
        val expectedTypeValue = LifestyleItem.GOAL.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        newGoal.update(db)
        //Get the goal for testing
        val sutGoal = goalDao.get(id)

        resultTypeValue = sutGoal!!.type
        resultId = sutGoal.id
        resultTitle = sutGoal.title
        resultCategory = sutGoal.category
        resultDateAdded = Utils.dateToFormattedString(sutGoal.dateAdded)
        resultDateCompleted = sutGoal.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedDateAdded, resultDateAdded)
        assertNull(resultDateCompleted)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeOne_DataExists() {

        //Arrange
        val idToRemove = "3"
        val resultGoal: Goal?

        //Act
        //Get the goal to remove
        val sutGoal = goalDao.get(idToRemove)
        //Test the function
        sutGoal!!.delete(db)
        //Try to get the object again
        resultGoal = goalDao.get(idToRemove)

        //Assert
        assertNull(resultGoal)
    }

}