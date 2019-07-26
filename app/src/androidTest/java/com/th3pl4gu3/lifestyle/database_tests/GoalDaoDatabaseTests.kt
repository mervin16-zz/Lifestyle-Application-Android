package com.th3pl4gu3.lifestyle.database_tests


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
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
class GoalDaoDatabaseTests {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var goalDao: GoalDao
    private lateinit var db: LifestyleDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, LifestyleDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        goalDao = db.goalDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGet_Unmodified() {

        //Arrange
        val goal = Goal()
        val id = goal.id
        val dateNow = goal.dateAdded
        val expectedId = id
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedDateAdded = dateNow
        val expectedTypeValue = LifestyleItem.GOAL.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert into db
        goalDao.insert(goal)
        //Get the item
        val sutGoal = goalDao.get(id)

        resultTypeValue = sutGoal!!.type
        resultId = sutGoal.id
        resultTitle = sutGoal.title
        resultCategory = sutGoal.category
        resultDateAdded = sutGoal.dateAdded
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
    fun insertAndGet_Modified() {

        //Arrange
        val title = "Do the dishes"
        val category = "House Chores"
        val dateCompleted = Calendar.getInstance()
        dateCompleted.add(Calendar.DAY_OF_YEAR, 10)

        val goal = Goal(title = title, category = category)
        //Mark this to do as completed
        goal.dateCompleted = dateCompleted

        val id = goal.id
        val dateNow = goal.dateAdded

        val expectedId = id
        val expectedTitle = title
        val expectedCategory = category
        val expectedDateAdded = dateNow
        val expectedDateCompleted = dateCompleted
        val expectedTypeValue = LifestyleItem.GOAL.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert into db
        goalDao.insert(goal)
        //Get the item
        val sutGoal = goalDao.get(id)

        resultTypeValue = sutGoal!!.type
        resultId = sutGoal.id
        resultTitle = sutGoal.title
        resultCategory = sutGoal.category
        resultDateAdded = sutGoal.dateAdded
        resultDateCompleted = sutGoal.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedDateAdded, resultDateAdded)
        assertEquals(expectedDateCompleted, resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun update() {

        //Arrange
        val testID = "21h312oi3"
        val title = "Do the dishes"
        val category = "House Chores"

        val oldGoal = Goal()
        val newGoal = oldGoal.copy()

        //set the IDs
        oldGoal.id = testID
        newGoal.id = testID

        //Make changes to new To Do
        newGoal.title = title
        newGoal.category = category

        val expectedId = testID
        val expectedTitle = title
        val expectedCategory = category
        val expectedDateAdded = newGoal.dateAdded
        val expectedTypeValue = LifestyleItem.GOAL.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert an unmodified To Do into db
        goalDao.insert(oldGoal)
        //Update To Do into db
        goalDao.update(newGoal)
        //Get the new To Do
        val sutGoal = goalDao.get(testID)

        resultTypeValue = sutGoal!!.type
        resultId = sutGoal.id
        resultTitle = sutGoal.title
        resultCategory = sutGoal.category
        resultDateAdded = sutGoal.dateAdded
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
    fun getOne_NoDataExists() {

        //Arrange
        val id = "d223v45t4ct"

        //Act
        val sutGoal = goalDao.get(id)

        //Assert
        assertNull(sutGoal)
    }

    @Test
    @Throws(Exception::class)
    fun getAll_NoDataExists() {

        //Arrange

        //Act
        val sutGoal = goalDao.getAllLive().value

        //Assert
        assertNull(sutGoal)
    }

    @Test
    @Throws(InterruptedException::class)
    fun getAll_DataExists() {

        //Arrange
        var title = "Do the dishes"
        val expectedSize = 11
        val resultSize: Int
        var goal: Goal



        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            goal = Goal(title = title)
            goalDao.insert(goal)
        }

        //Get the item
        val sutGoal = LiveDataTestUtil.getValue(goalDao.getAllLive())
        resultSize = sutGoal.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeOne_DataExists() {

        //Arrange
        var title = "Do the dishes"
        var idToRemove = "null"
        val expectedSize = 10
        val resultSize: Int
        var goal: Goal
        val resultGoal: Goal?
        val goals: List<Goal>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            goal = Goal(title = title)

            //Take a number at random
            if(x == 4){
                idToRemove = goal.id
            }

            goalDao.insert(goal)
        }

        //Remove the item
        goalDao.remove(idToRemove)
        //Try to get all objects
        goals = LiveDataTestUtil.getValue(goalDao.getAllLive())
        //Try to get the object
        resultGoal = goalDao.get(idToRemove)

        resultSize = goals.size

        //Assert
        assertEquals(expectedSize, resultSize)
        assertNull(resultGoal)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeOne_NoDataExists() {

        //Arrange
        var title = "Do the dishes"
        var idToRemove = "null"
        val expectedSize = 11
        val resultSize: Int
        var goal: Goal
        val resultGoal: Goal?
        val goals: List<Goal>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            goal = Goal(title = title)

            //Take a number at random
            if(x == 12){
                idToRemove = goal.id
            }

            goalDao.insert(goal)
        }

        //Remove the item
        goalDao.remove(idToRemove)
        //Try to get all objects
        goals = LiveDataTestUtil.getValue(goalDao.getAllLive())
        //Try to get the object
        resultGoal = goalDao.get(idToRemove)

        resultSize = goals.size

        //Assert
        assertEquals(expectedSize, resultSize)
        assertNull(resultGoal)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAll_DataExists() {

        //Arrange
        var title = "Do the dishes"
        val expectedSize = 0
        val resultSize: Int
        var goal: Goal
        val goals: List<Goal>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            goal = Goal(title = title)
            goalDao.insert(goal)
        }

        //Remove the item
        goalDao.removeAll()
        //Try to get all objects
        goals = LiveDataTestUtil.getValue(goalDao.getAllLive())

        resultSize = goals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAll_NoDataExists() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val goals: List<Goal>?


        //Act
        goalDao.removeAll()
        //Try to get all objects
        goals = LiveDataTestUtil.getValue(goalDao.getAllLive())
        resultSize = goals.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }
}