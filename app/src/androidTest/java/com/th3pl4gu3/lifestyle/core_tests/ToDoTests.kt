package com.th3pl4gu3.lifestyle.core_tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.database.ToDoDao
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class ToDoTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var toDoDao: ToDoDao
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
        toDoDao = db.toDoDao
    }

    private fun generateToDosInDb(){
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var toDo: ToDo

        //Add several to do's in a for loop
        for (x in 1..10) {
            title1 += x
            toDo = ToDo(title = title1, category = theCategory1)
            //Make changes in some ids for better testing
            toDo.id = x.toString()
            toDoDao.insert(toDo)

            title1 = theTitle1
        }

        for (x in 1..10) {
            title2 += x
            toDo = ToDo(title = title2, category = theCategory2)
            toDoDao.insert(toDo)

            title2 = theTitle2
        }

        for (x in 1..10) {
            title3 += x
            toDo = ToDo(title = title3, category = theCategory3)
            toDoDao.insert(toDo)

            title3 = theTitle3
        }
    }

    @Before
    fun createDb() {
        //Create the database
        initializeDbWithData()

        //Add some To Do in the database
        generateToDosInDb()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addToDo() {

        //Arrange
        val toDo = ToDo()
        val id = toDo.id
        val dateNow = Utils.dateToFormattedString(toDo.dateAdded)
        val expectedId = id
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedDateAdded = dateNow
        val expectedTypeValue = LifestyleItem.TO_DO.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        toDo.add(db)
        //Fetch the goal for testing
        val sutToDo = toDoDao.get(id)

        resultTypeValue = sutToDo!!.type
        resultId = sutToDo.id
        resultTitle = sutToDo.title
        resultCategory = sutToDo.category
        resultDateAdded = Utils.dateToFormattedString(sutToDo.dateAdded)
        resultDateCompleted = sutToDo.dateCompleted

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
    fun updateToDo() {

        //Arrange
        val id = "4"
        val title = "Do the dishes 44"
        val category = "House Chores 44"

        val newToDo = ToDo(title = title, category = category)

        //set the ID
        newToDo.id = id

        val expectedId = id
        val expectedTitle = title
        val expectedCategory = category
        val expectedDateAdded = Utils.dateToFormattedString(newToDo.dateAdded)
        val expectedTypeValue = LifestyleItem.TO_DO.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        newToDo.update(db)
        //Get the To Do for testing
        val sutToDo = toDoDao.get(id)

        resultTypeValue = sutToDo!!.type
        resultId = sutToDo.id
        resultTitle = sutToDo.title
        resultCategory = sutToDo.category
        resultDateAdded = Utils.dateToFormattedString(sutToDo.dateAdded)
        resultDateCompleted = sutToDo.dateCompleted

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
        val resultToDo: ToDo?

        //Act
        //Get the To Do to remove
        val sutToDo = toDoDao.get(idToRemove)
        //Test the function
        sutToDo!!.delete(db)
        //Try to get the object again
        resultToDo = toDoDao.get(idToRemove)

        //Assert
        assertNull(resultToDo)
    }

}