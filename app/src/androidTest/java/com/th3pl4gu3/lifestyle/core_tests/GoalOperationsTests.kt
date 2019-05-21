package com.th3pl4gu3.lifestyle.core_tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.operations.GoalOperations
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.GoalDao
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.database_tests.LiveDataTestUtil
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class GoalOperationsTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var goalDao: GoalDao
    private lateinit var db: LifestyleDatabase
    private lateinit var emptyDb: LifestyleDatabase
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

    private fun initializeDbWithNoData(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        emptyDb = Room.inMemoryDatabaseBuilder(context, LifestyleDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        goalDao = emptyDb.goalDao
    }

    private fun generateGoalsInDb(){
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var goal: Goal

        //Add several to do's in a for loop
        for (x in 1..10) {
            title1 += x
            goal = Goal(title = title1, category = theCategory1)
            //Change the IDs of some for testing
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
    fun getByIdOffline_IdDoesntExist() {

        //Arrange
        val id = "uo32ih4i4o23h4"
        val expectedResult = "Error while fetching your Goal. This Goal doesn't exist."
        var result: String?

        //Act
        try{
            val goal = GoalOperations.getByIdOffline(db, id)
            result = "Failed"
        }catch (ex: Exception){
            result = ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun getByIdOffline_IdExist() {

        //Arrange
        val id = "4"
        val title = "Do the dishes 4"
        val category = "House Chores"

        val expectedId = id
        val expectedTitle = title
        val expectedCategory = category
        val expectedDateAdded = Utils.dateToFormattedString(Calendar.getInstance())
        val expectedTypeValue = LifestyleItem.GOAL.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        val goal = GoalOperations.getByIdOffline(db, id)

        resultTypeValue = goal.type
        resultId = goal.id
        resultTitle = goal.title
        resultCategory = goal.category
        resultDateAdded = Utils.dateToFormattedString(goal.dateAdded)
        resultDateCompleted = goal.dateCompleted

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
    fun getAllOffline_DataExists() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        var goal: Goal



        //Act
        //Create the dummy Db
        initializeDbWithNoData()
        //Test the function
        val sutGoal = LiveDataTestUtil.getValue(GoalOperations.getAllOffline(emptyDb))
        resultSize = sutGoal.size

        //Clear memory of dummy data
        emptyDb.close()

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAllOffline_DataExists() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int


        //Act
        val sutGoalOperations = GoalOperations()
        sutGoalOperations.removeAllOffline(db)

        resultSize = LiveDataTestUtil.getValue(GoalOperations.getAllOffline(db)).size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAllOffline_NoDataExists() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int


        //Act
        //Initialize dummy Db
        initializeDbWithNoData()
        //Test the function
        val sutGoalOperations = GoalOperations()
        sutGoalOperations.removeAllOffline(emptyDb)

        resultSize = LiveDataTestUtil.getValue(GoalOperations.getAllOffline(emptyDb)).size

        //Clear memory of dummy data
        emptyDb.close()

        //Assert
        assertEquals(expectedSize, resultSize)
    }
}