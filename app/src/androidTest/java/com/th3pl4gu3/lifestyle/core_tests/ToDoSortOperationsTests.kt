package com.th3pl4gu3.lifestyle.core_tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.SortOperations
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class ToDoSortOperationsTests {

    private val TESTING_KEYWORD = "LIFESTYLE_ITEM"
    private val toDos: ArrayList<ToDo> = ArrayList()
    private var theTitle1 = "Do the dishes "
    private var theTitle2 = "Wash the car "
    private var theTitle3 = "Clean the house "

    private var theCategory1 = "House Chores"
    private var theCategory2 = "Personal"
    private var theCategory3 = "My Hobby"

    @Before
    fun createArrayList() {
        //Add some toDos to arraylist
        var title1 = theTitle1
        var title2 = theTitle2
        var title3 = theTitle3
        var toDo: ToDo

        //Get today's date and add 2 days ahead
        val twoDaysFromNow = Calendar.getInstance()
        twoDaysFromNow.add(Calendar.DAY_OF_MONTH, 2)

        //Add several to do's in a for loop
        for (x in 1..10) {
            title1 += x
            toDo = ToDo(title = title1, category = theCategory1)

            //Modify the date added
            toDo.dateAdded = twoDaysFromNow

            toDos.add(toDo)

            title1 = theTitle1
        }

        for (x in 1..10) {
            title2 += x
            toDo = ToDo(title = title2, category = theCategory2)
            toDos.add(toDo)

            title2 = theTitle2
        }

        for (x in 1..10) {
            title3 += x
            toDo = ToDo(title = title3, category = theCategory3)
            toDos.add(toDo)

            title3 = theTitle3
        }

        //Shuffle the list 3 times for better testing
        toDos.shuffle()
        toDos.shuffle()
        toDos.shuffle()

        //Mark some of them as completed
        for (x in 1..8) {
            toDos[x].dateCompleted = Calendar.getInstance()
        }
    }

    @After
    @Throws(IOException::class)
    fun clearArrayList() {
        toDos.clear()
    }

    @Test
    @Throws(Exception::class)
    fun sortByTitle_ASC() {

        //Arrange
        val expectedResultRowFirst = "Clean the house 1"
        val expectedResultRowSecond = "Clean the house 10"
        val expectedResultRowBeforeLast = "Wash the car 8"
        val expectedResultRowLast = "Wash the car 9"

        var resultRowFirst:String?
        var resultRowSecond:String?
        var resultRowBeforeLast:String?
        var resultRowLast:String?

        val filteredToDos: List<ToDo>
        val order = SortOperations.Order.ASC

        //Act
        try{
            val sutSort = SortOperations<ToDo>(toDos)
            filteredToDos = sutSort.byTitle(order)

            resultRowFirst = filteredToDos[0].title
            resultRowSecond = filteredToDos[1].title
            resultRowBeforeLast = filteredToDos[filteredToDos.size - 2].title
            resultRowLast = filteredToDos[filteredToDos.size - 1].title
        }catch (ex:Exception){
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
        val expectedResultRowFirst = "Wash the car 9"
        val expectedResultRowSecond = "Wash the car 8"
        val expectedResultRowBeforeLast = "Clean the house 10"
        val expectedResultRowLast = "Clean the house 1"

        var resultRowFirst:String?
        var resultRowSecond:String?
        var resultRowBeforeLast:String?
        var resultRowLast:String?

        val filteredToDos: List<ToDo>
        val order = SortOperations.Order.DESC

        //Act
        try{
            val sutSort = SortOperations<ToDo>(toDos)
            filteredToDos = sutSort.byTitle(order)

            resultRowFirst = filteredToDos[0].title
            resultRowSecond = filteredToDos[1].title
            resultRowBeforeLast = filteredToDos[filteredToDos.size - 2].title
            resultRowLast = filteredToDos[filteredToDos.size - 1].title
        }catch (ex:Exception){
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

        var result:String?

        val filteredToDos: List<ToDo>
        val order = SortOperations.Order.DESC

        //Act
        //Clear the list first
        toDos.clear()
        //Test the function
        try{
            val sutSort = SortOperations<ToDo>(toDos)
            filteredToDos = sutSort.byTitle(order)
            result = "Passed"
        }catch (ex: Exception){
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

        var resultRowFirst:String?
        var resultRowLast:String?

        val filteredToDo: List<ToDo>
        val order = SortOperations.Order.ASC

        //Act
        try{
            val sutSort = SortOperations<ToDo>(toDos)
            filteredToDo = sutSort.byCategory(order)

            resultRowFirst = filteredToDo[0].category
            resultRowLast = filteredToDo[filteredToDo.size - 1].category
        }catch (ex:Exception){
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

        var resultRowFirst:String?
        var resultRowLast:String?

        val filteredToDo: List<ToDo>
        val order = SortOperations.Order.DESC

        //Act
        try{
            val sutSort = SortOperations<ToDo>(toDos)
            filteredToDo = sutSort.byCategory(order)

            resultRowFirst = filteredToDo[0].category
            resultRowLast = filteredToDo[filteredToDo.size - 1].category
        }catch (ex:Exception){
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

        var result:String?

        val filteredToDo: List<ToDo>
        val order = SortOperations.Order.DESC

        //Act
        //Clear the list first
        toDos.clear()
        //Test the function
        try{
            val sutSort = SortOperations<ToDo>(toDos)
            filteredToDo = sutSort.byCategory(order)
            result = "Passed"
        }catch (ex: Exception){
            result = ex.message
        }

        //Assert
        assertEquals(expectedResult, result)
    }

}