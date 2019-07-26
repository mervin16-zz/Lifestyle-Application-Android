package com.th3pl4gu3.lifestyle.database_tests


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
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
class ToBuyDaoDatabaseTests {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var toBuyDao: ToBuyDao
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
        toBuyDao = db.toBuyDao
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
        val toBuy = ToBuy()
        val id = toBuy.id
        val dateNow = toBuy.dateAdded
        val expectedId = id
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedEstimatedPrice = 0.0
        val expectedQuantity = 0
        val expectedPriority = Priority.P4
        val expectedDateAdded = dateNow
        val expectedTypeValue = LifestyleItem.TO_BUY.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultEstimatedPrice: Double
        val resultQuantity: Int
        val resultPriority: Priority
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert into db
        toBuyDao.insert(toBuy)
        //Get the item
        val sutToBuy = toBuyDao.get(id)

        resultTypeValue = sutToBuy!!.type
        resultId = sutToBuy.id
        resultTitle = sutToBuy.title
        resultCategory = sutToBuy.category
        resultEstimatedPrice = sutToBuy.estimatedPrice
        resultQuantity = sutToBuy.quantity
        resultPriority = sutToBuy.priority
        resultDateAdded = sutToBuy.dateAdded
        resultDateCompleted = sutToBuy.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedEstimatedPrice, resultEstimatedPrice, 10.0)
        assertEquals(expectedQuantity, resultQuantity)
        assertEquals(expectedPriority, resultPriority)
        assertEquals(expectedDateAdded, resultDateAdded)
        assertNull(resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGet_Modified() {

        //Arrange
        val title = "Do the dishes"
        val category = "House Chores"
        val estimatedPrice = 150.20
        val quantity = 2
        val dateCompleted = Calendar.getInstance()
        dateCompleted.add(Calendar.DAY_OF_YEAR, 10)

        val toBuy = ToBuy(title = title, category = category, estimatedPrice = estimatedPrice, quantity = quantity)
        //Mark this to do as completed
        toBuy.dateCompleted = dateCompleted

        val id = toBuy.id
        val dateNow = toBuy.dateAdded

        val expectedId = id
        val expectedTitle = title
        val expectedCategory = category
        val expectedEstimatedPrice = 150.20
        val expectedQuantity = 2
        val expectedPriority = Priority.P4
        val expectedDateAdded = dateNow
        val expectedDateCompleted = dateCompleted
        val expectedTypeValue = LifestyleItem.TO_BUY.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultEstimatedPrice: Double
        val resultQuantity: Int
        val resultPriority: Priority
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert into db
        toBuyDao.insert(toBuy)
        //Get the item
        val sutToBuy = toBuyDao.get(id)

        resultTypeValue = sutToBuy!!.type
        resultId = sutToBuy.id
        resultTitle = sutToBuy.title
        resultCategory = sutToBuy.category
        resultEstimatedPrice = sutToBuy.estimatedPrice
        resultQuantity = sutToBuy.quantity
        resultPriority = sutToBuy.priority
        resultDateAdded = sutToBuy.dateAdded
        resultDateCompleted = sutToBuy.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedEstimatedPrice, resultEstimatedPrice, 10.0)
        assertEquals(expectedQuantity, resultQuantity)
        assertEquals(expectedPriority, resultPriority)
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
        val priority = Priority.P3

        val oldToBuy = ToBuy()
        val newToBuy = oldToBuy.copy()

        //set the IDs
        oldToBuy.id = testID
        newToBuy.id = testID

        //Make changes to new To Do
        newToBuy.title = title
        newToBuy.category = category
        newToBuy.priority = priority

        val expectedId = testID
        val expectedTitle = title
        val expectedCategory = category
        val expectedEstimatedPrice = 0.0
        val expectedQuantity = 0
        val expectedPriority = priority
        val expectedDateAdded = newToBuy.dateAdded
        val expectedTypeValue = LifestyleItem.TO_BUY.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultEstimatedPrice: Double
        val resultQuantity: Int
        val resultPriority: Priority
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert an unmodified To Do into db
        toBuyDao.insert(oldToBuy)
        //Update To Do into db
        toBuyDao.update(newToBuy)
        //Get the new To Do
        val sutToBuy = toBuyDao.get(testID)

        resultTypeValue = sutToBuy!!.type
        resultId = sutToBuy.id
        resultTitle = sutToBuy.title
        resultCategory = sutToBuy.category
        resultEstimatedPrice = sutToBuy.estimatedPrice
        resultQuantity = sutToBuy.quantity
        resultPriority = sutToBuy.priority
        resultDateAdded = sutToBuy.dateAdded
        resultDateCompleted = sutToBuy.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
        assertEquals(expectedEstimatedPrice, resultEstimatedPrice, 10.0)
        assertEquals(expectedQuantity, resultQuantity)
        assertEquals(expectedPriority, resultPriority)
        assertEquals(expectedDateAdded, resultDateAdded)
        assertNull(resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun getOne_NoDataExists() {

        //Arrange
        val id = "d223v45t4ct"

        //Act
        val sutToBuy = toBuyDao.get(id)

        //Assert
        assertNull(sutToBuy)
    }

    @Test
    @Throws(Exception::class)
    fun getAll_NoDataExists() {

        //Arrange

        //Act
        val sutTodo = toBuyDao.getAllLive().value

        //Assert
        assertNull(sutTodo)
    }

    @Test
    @Throws(InterruptedException::class)
    fun getAll_DataExists() {

        //Arrange
        var title = "Do the dishes"
        val expectedSize = 11
        val resultSize: Int
        var toBuy:ToBuy



        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toBuy = ToBuy(title = title)
            toBuyDao.insert(toBuy)
        }

        //Get the item
        val sutToBuy = LiveDataTestUtil.getValue(toBuyDao.getAllLive())
        resultSize = sutToBuy.size

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
        var toBuy: ToBuy
        val resultToBuy: ToBuy?
        val toBuys: List<ToBuy>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toBuy = ToBuy(title = title)


            //Take a number at random
            if(x == 4){
                idToRemove = toBuy.id
            }

            toBuyDao.insert(toBuy)
        }

        //Remove the item
        toBuyDao.remove(idToRemove)
        //Try to get all objects
        toBuys = LiveDataTestUtil.getValue(toBuyDao.getAllLive())
        //Try to get the object
        resultToBuy = toBuyDao.get(idToRemove)

        resultSize = toBuys.size

        //Assert
        assertEquals(expectedSize, resultSize)
        assertNull(resultToBuy)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeOne_NoDataExists() {

        //Arrange
        var title = "Do the dishes"
        var idToRemove = "null"
        val expectedSize = 11
        val resultSize: Int
        var toBuy: ToBuy
        val resultToBuy: ToBuy?
        val toBuys: List<ToBuy>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toBuy = ToBuy(title = title)


            //Take a number at random
            if(x == 12){
                idToRemove = toBuy.id
            }

            toBuyDao.insert(toBuy)
        }

        //Remove the item
        toBuyDao.remove(idToRemove)
        //Try to get all objects
        toBuys = LiveDataTestUtil.getValue(toBuyDao.getAllLive())
        //Try to get the object
        resultToBuy = toBuyDao.get(idToRemove)

        resultSize = toBuys.size

        //Assert
        assertEquals(expectedSize, resultSize)
        assertNull(resultToBuy)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAll_DataExists() {

        //Arrange
        var title = "Do the dishes"
        val expectedSize = 0
        val resultSize: Int
        var toDo: ToBuy
        val toDos: List<ToBuy>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toDo = ToBuy(title = title)
            toBuyDao.insert(toDo)
        }

        //Remove the item
        toBuyDao.removeAll()
        //Try to get all objects
        toDos = LiveDataTestUtil.getValue(toBuyDao.getAllLive())

        resultSize = toDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAll_NoDataExists() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val toDos: List<ToBuy>?


        //Act
        toBuyDao.removeAll()
        //Try to get all objects
        toDos = LiveDataTestUtil.getValue(toBuyDao.getAllLive())
        resultSize = toDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }
}