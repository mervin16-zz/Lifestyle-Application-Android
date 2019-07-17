package com.th3pl4gu3.lifestyle.core_tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.operations.ToBuyOperations
import com.th3pl4gu3.lifestyle.core.utils.Utils
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.database.ToBuyDao
import com.th3pl4gu3.lifestyle.database_tests.LiveDataTestUtil
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class ToBuyOperationsTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var toBuyDao: ToBuyDao
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
        toBuyDao = db.toBuyDao
    }

    private fun initializeDbWithNoData(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        emptyDb = Room.inMemoryDatabaseBuilder(context, LifestyleDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        toBuyDao = emptyDb.toBuyDao
    }

    private fun generateToBuysInDb(){
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var toBuy: ToBuy

        //Add several to buy's in a for loop
        for (x in 1..10) {
            title1 += x
            toBuy = ToBuy(title = title1, category = theCategory1)
            //Change the IDs of some for testing
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

        //Add some To Buy in the database
        generateToBuysInDb()
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
        val expectedResult = "Error while fetching your To Buy. This To Buy task doesn't exist."
        var result: String?

        //Act
        try{
            val toBuy = ToBuyOperations.getByIdOffline(db, id)
            result = "Failed"
        }catch (ex: Exception){
            result = ex.message
        }

        //Assert
        Assert.assertEquals(expectedResult, result)
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
        val expectedTypeValue = LifestyleItem.TO_BUY.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultDateAdded: String
        val resultDateCompleted: Calendar?

        //Act
        val toBuy = ToBuyOperations.getByIdOffline(db, id)

        resultTypeValue = toBuy.type
        resultId = toBuy.id
        resultTitle = toBuy.title
        resultCategory = toBuy.category
        resultDateAdded = Utils.dateToFormattedString(toBuy.dateAdded)
        resultDateCompleted = toBuy.dateCompleted

        //Assert
        Assert.assertEquals(expectedTypeValue, resultTypeValue)
        Assert.assertEquals(expectedId, resultId)
        Assert.assertEquals(expectedTitle, resultTitle)
        Assert.assertEquals(expectedCategory, resultCategory)
        Assert.assertEquals(expectedDateAdded, resultDateAdded)
        Assert.assertNull(resultDateCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun getAllOffline_DataExists() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int

        //Act
        //Create the dummy Db
        initializeDbWithNoData()
        //Test the function
        val sutToBuy = LiveDataTestUtil.getValue(ToBuyOperations.getAllLiveOffline(emptyDb))
        resultSize = sutToBuy.size

        //Clear memory of dummy data
        emptyDb.close()

        //Assert
        Assert.assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAllOffline_DataExists() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int

        //Act
        ToBuyOperations.removeAllOffline(db)

        resultSize = LiveDataTestUtil.getValue(ToBuyOperations.getAllLiveOffline(db)).size

        //Assert
        Assert.assertEquals(expectedSize, resultSize)
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
        ToBuyOperations.removeAllOffline(emptyDb)

        resultSize = LiveDataTestUtil.getValue(ToBuyOperations.getAllLiveOffline(emptyDb)).size

        //Clear memory of dummy data
        emptyDb.close()

        //Assert
        Assert.assertEquals(expectedSize, resultSize)
    }
}