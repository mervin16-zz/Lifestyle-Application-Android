package com.th3pl4gu3.lifestyle.core_tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.database.ToBuyDao
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class ToBuyTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var toBuyDao: ToBuyDao
    private lateinit var db: LifestyleDatabase

    private var theTitle1 = "Gift for her birthday "
    private var theTitle2 = "A car "
    private var theTitle3 = "Little puppies "

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
        toBuyDao = db.toBuyDao
    }

    private fun generateToDosInDb(){
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var toBuy: ToBuy

        //Add several ToBuy's in a for loop
        for (x in 1..10) {
            title1 += x
            toBuy = ToBuy(title = title1, category = theCategory1)
            //Make changes in some ids for better testing
            toBuy.id = x.toString()
            toBuyDao.insert(toBuy)

            title1 = theTitle1
        }

        for (x in 1..10) {
            title2 += x
            toBuy = ToBuy(title = title2, category = theCategory2)
            toBuyDao.insert(toBuy)

            title2 = theTitle2
        }

        for (x in 1..10) {
            title3 += x
            toBuy = ToBuy(title = title3, category = theCategory3)
            toBuyDao.insert(toBuy)

            title3 = theTitle3
        }
    }

    @Before
    fun createDb() {
        //Create the database
        initializeDbWithData()

        //Add some ToBuy in the database
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
        val toBuy = ToBuy()
        val id = toBuy.id
        val dateNow = Utils.dateToFormattedString(toBuy.dateAdded)
        val expectedId = id
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedDateAdded = dateNow
        val expectedTypeValue = LifestyleItem.TO_BUY.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        toBuy.add(db)
        //Fetch the ToBuy for testing
        val sutToBuy = toBuyDao.get(id)

        resultTypeValue = sutToBuy!!.type
        resultId = sutToBuy.id
        resultTitle = sutToBuy.title
        resultCategory = sutToBuy.category
        resultDateAdded = Utils.dateToFormattedString(sutToBuy.dateAdded)
        resultDateCompleted = sutToBuy.dateCompleted

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
        val title = "A car 44"
        val category = "Personal 44"

        val newToBuy = ToBuy(title = title, category = category)

        //set the ID
        newToBuy.id = id

        val expectedId = id
        val expectedTitle = title
        val expectedCategory = category
        val expectedDateAdded = Utils.dateToFormattedString(newToBuy.dateAdded)
        val expectedTypeValue = LifestyleItem.TO_BUY.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        newToBuy.update(db)
        //Get the To Buy for testing
        val sutToBuy = toBuyDao.get(id)

        resultTypeValue = sutToBuy!!.type
        resultId = sutToBuy.id
        resultTitle = sutToBuy.title
        resultCategory = sutToBuy.category
        resultDateAdded = Utils.dateToFormattedString(sutToBuy.dateAdded)
        resultDateCompleted = sutToBuy.dateCompleted

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
        val resultToBuy: ToBuy?

        //Act
        //Get the To Buy to remove
        val sutToBuy = toBuyDao.get(idToRemove)
        //Test the function
        sutToBuy!!.delete(db)
        //Try to get the object again
        resultToBuy = toBuyDao.get(idToRemove)

        //Assert
        assertNull(resultToBuy)
    }

    @Test
    @Throws(Exception::class)
    fun markAsCompleted() {

        //Arrange
        val toBuy = ToBuy()
        val expectedDateCompleted = Utils.dateToFormattedString(Calendar.getInstance())

        val resultDateCompleted: String?

        //Act
        toBuy.markAsComplete()
        resultDateCompleted = Utils.dateToFormattedString(toBuy.dateCompleted!!)

        //Assert
        assertEquals(expectedDateCompleted, resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun markAsIncomplete() {

        //Arrange
        val toBuy = ToBuy()
        toBuy.dateCompleted = Calendar.getInstance()

        val resultDateCompleted: Calendar?

        //Act
        toBuy.markAsIncomplete()
        resultDateCompleted = toBuy.dateCompleted

        //Assert
        assertNull(resultDateCompleted)
    }
}