package com.th3pl4gu3.lifestyle.core_tests

import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.operations.Filter
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class ToBuyFilterTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"
    private val toBuys: ArrayList<ToBuy> = ArrayList()
    private var theTitle1 = "Android Phone "
    private var theTitle2 = "Porsche Car "
    private var theTitle3 = "Vegetables "

    private var theCategory1 = "House Chores"
    private var theCategory2 = "Personal"
    private var theCategory3 = "My Hobby"

    private var estimatedPrice1 = 32000.00
    private var estimatedPrice2 = 40000.00
    private var estimatedPrice3 = 150.00

    @Before
    fun createArrayList() {
        //Add some goals to arraylist
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var toBuy: ToBuy

        //Add several to do's in a for loop
        for (x in 1..10) {
            title1 += x
            toBuy = ToBuy(title = title1, category = theCategory1, estimatedPrice = estimatedPrice1)
            toBuys.add(toBuy)

            title1 = theTitle1
        }

        for (x in 1..10) {
            title2 += x
            toBuy = ToBuy(title = title2, category = theCategory2, estimatedPrice = estimatedPrice2)
            toBuys.add(toBuy)

            title2 = theTitle2
        }

        for (x in 1..10) {
            title3 += x
            toBuy = ToBuy(title = title3, category = theCategory3, estimatedPrice = estimatedPrice3)
            toBuys.add(toBuy)

            title3 = theTitle3
        }

        //Shuffle the list 3 times for better testing
        toBuys.shuffle()
        toBuys.shuffle()
        toBuys.shuffle()

        //Mark some of them as completed
        for (x in 1..8) {
            toBuys[x].dateCompleted = Calendar.getInstance()
        }
    }

    @After
    @Throws(IOException::class)
    fun clearArrayList() {
        toBuys.clear()
    }

    @Test
    @Throws(Exception::class)
    fun getByTitle_TitleExists1Of3() {

        //Arrange
        val searchTitle = "android phone 1"
        val expectedSize = 2 //Android phone 1 & Android phone 10
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByTitle(searchTitle)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByTitle_TitleExists2Of3() {

        //Arrange
        val searchTitle = "table"
        val expectedSize = 10 //Vegetables 1 - 10
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByTitle(searchTitle)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByTitle_TitleExists3Of3() {

        //Arrange
        val searchTitle = "car"
        val expectedSize = 10 //Porsche car 1 - 10
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByTitle(searchTitle)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByTitle_TitleDoesntExists() {

        //Arrange
        val searchTitle = "No Name"
        val expectedSize = 0
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByTitle(searchTitle)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByTitle_NoDataAvailable() {

        //Arrange
        val searchTitle = "No Name"
        val expectedSize = 0
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        //Clear the arraylist
        toBuys.clear()
        //Test the function
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByTitle(searchTitle)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByCategory_CategoryExists1Of3() {

        //Arrange
        val searchCategory = "house chores"
        val expectedSize = 10 //do the dishes 1 -10
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByCategory(searchCategory)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByCategory_CategoryExists2Of3() {

        //Arrange
        val searchCategory = "sonal"
        val expectedSize = 10 //Wash the car 1 - 10
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByCategory(searchCategory)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getByCategory_CategoryExists3Of3() {

        //Arrange
        val searchCategory = "hobby"
        val expectedSize = 10 //Clean the house 1 - 10
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByCategory(searchCategory)
        resultSize = filteredToBuy.size

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
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByCategory(searchCategory)
        resultSize = filteredToBuy.size

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
        val filteredToBuy: List<ToBuy>

        //Act
        //Clear the arrayList
        toBuys.clear()
        // Test the function
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getByCategory(searchCategory)
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_SomeHaveBeenCompleted() {

        //Arrange
        val expectedSize = 8
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getCompleted()
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_NoneHaveBeenCompleted() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        //Mark all as not completed
        for(toBuy in toBuys){
            toBuy.dateCompleted = null
        }

        //Test the function
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getCompleted()
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_NoDataAvailable() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        //Clear the arrayList
        toBuys.clear()
        // Test the function
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getCompleted()
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_SomeHaveBeenCompleted() {

        //Arrange
        val expectedSize = 22
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getActive()
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_NoneAreActive() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        //Mark all as completed
        for(toBuy in toBuys){

            if(toBuy.dateCompleted == null){
                toBuy.dateCompleted = Calendar.getInstance()
            }
        }

        //Test the function
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getActive()
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_NoDataAvailable() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToBuy: List<ToBuy>

        //Act
        //Clear the arrayList
        toBuys.clear()
        // Test the function
        val sutFilter = Filter<ToBuy>(toBuys)
        filteredToBuy = sutFilter.getActive()
        resultSize = filteredToBuy.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }
}