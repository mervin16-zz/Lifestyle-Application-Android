package com.th3pl4gu3.lifestyle.core_tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.enums.SortingOrder
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.operations.SortOperations
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class ToBuySortOperationsTests {

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
    fun sortByTitle_ASC() {

        //Arrange
        val expectedResultRowFirst = "Android Phone 1"
        val expectedResultRowSecond = "Android Phone 10"
        val expectedResultRowBeforeLast = "Vegetables 8"
        val expectedResultRowLast = "Vegetables 9"

        var resultRowFirst: String?
        var resultRowSecond: String?
        var resultRowBeforeLast: String?
        var resultRowLast: String?

        val filteredGoals: List<ToBuy>
        val order = SortingOrder.ASC

        //Act
        try {
            val sutSort = SortOperations<ToBuy>()
            sutSort.list = toBuys
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
        val expectedResultRowFirst = "Vegetables 9"
        val expectedResultRowSecond = "Vegetables 8"
        val expectedResultRowBeforeLast = "Android Phone 10"
        val expectedResultRowLast = "Android Phone 1"

        var resultRowFirst: String?
        var resultRowSecond: String?
        var resultRowBeforeLast: String?
        var resultRowLast: String?

        val filteredGoals: List<ToBuy>
        val order = SortingOrder.DESC

        //Act
        try {
            val sutSort = SortOperations<ToBuy>()
            sutSort.list = toBuys
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

        val filteredGoals: List<ToBuy>
        val order = SortingOrder.DESC

        //Act
        //Clear the list first
        toBuys.clear()
        //Test the function
        try {
            val sutSort = SortOperations<ToBuy>()
            sutSort.list = toBuys
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

        val filteredToBuys: List<ToBuy>
        val order = SortingOrder.ASC

        //Act
        try {
            val sutSort = SortOperations<ToBuy>()
            sutSort.list = toBuys
            filteredToBuys = sutSort.byCategory(order)

            resultRowFirst = filteredToBuys[0].category
            resultRowLast = filteredToBuys[filteredToBuys.size - 1].category
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

        val filteredToBuys: List<ToBuy>
        val order = SortingOrder.DESC

        //Act
        try {
            val sutSort = SortOperations<ToBuy>()
            sutSort.list = toBuys
            filteredToBuys = sutSort.byCategory(order)

            resultRowFirst = filteredToBuys[0].category
            resultRowLast = filteredToBuys[filteredToBuys.size - 1].category
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

        val filteredToBuys: List<ToBuy>
        val order = SortingOrder.DESC

        //Act
        //Clear the list first
        toBuys.clear()
        //Test the function
        try {
            val sutSort = SortOperations<ToBuy>()
            sutSort.list = toBuys
            filteredToBuys = sutSort.byCategory(order)
            result = "Passed"
        } catch (ex: Exception) {
            result = ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }
}