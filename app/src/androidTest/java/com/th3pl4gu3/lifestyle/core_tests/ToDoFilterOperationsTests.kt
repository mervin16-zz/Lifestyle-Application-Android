package com.th3pl4gu3.lifestyle.core_tests

import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class ToDoFilterOperationsTests {

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
        var todo: ToDo

        //Add several to do's in a for loop
        for (x in 1..10) {
            title1 += x
            todo = ToDo(title = title1, category = theCategory1)
            toDos.add(todo)

            title1 = theTitle1
        }

        for (x in 1..10) {
            title2 += x
            todo = ToDo(title = title2, category = theCategory2)
            toDos.add(todo)

            title2 = theTitle2
        }

        for (x in 1..10) {
            title3 += x
            todo = ToDo(title = title3, category = theCategory3)
            toDos.add(todo)

            title3 = theTitle3
        }

        //Shuffle the list for better testing
        toDos.shuffle()

        //Mark some of them as completed
        for(x in 1..8){
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
    fun getByCategory_CategoryExists() {

        //Arrange
        val searchCategory = "house chores"
        val expectedSize = 10 //do the dishes 1 -10
        val resultSize: Int
        val filteredToDos: List<ToDo>

        //Act
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.byCategory(searchCategory)
        resultSize = filteredToDos.size

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
        val filteredToDos: List<ToDo>

        //Act
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.byCategory(searchCategory)
        resultSize = filteredToDos.size

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
        val filteredToDos: List<ToDo>

        //Act
        //Clear the arrayList
        toDos.clear()
        // Test the function
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.byCategory(searchCategory)
        resultSize = filteredToDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_SomeHaveBeenCompleted() {

        //Arrange
        val expectedSize = 8
        val resultSize: Int
        val filteredToDos: List<ToDo>

        //Act
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.getCompleted()
        resultSize = filteredToDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_NoneHaveBeenCompleted() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToDos: List<ToDo>

        //Act
        //Mark all as not completed
        for(toDo in toDos){
            toDo.dateCompleted = null
        }

        //Test the function
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.getCompleted()
        resultSize = filteredToDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getCompleted_NoDataAvailable() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToDos: List<ToDo>

        //Act
        //Clear the arrayList
        toDos.clear()
        // Test the function
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.getCompleted()
        resultSize = filteredToDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_SomeHaveBeenCompleted() {

        //Arrange
        val expectedSize = 22
        val resultSize: Int
        val filteredToDos: List<ToDo>

        //Act
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.getActive()
        resultSize = filteredToDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_NoneAreActive() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToDos: List<ToDo>

        //Act
        //Mark all as completed
        for(toDo in toDos){

            if(toDo.dateCompleted == null){
                toDo.dateCompleted = Calendar.getInstance()
            }
        }

        //Test the function
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.getActive()
        resultSize = filteredToDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }

    @Test
    @Throws(Exception::class)
    fun getActive_NoDataAvailable() {

        //Arrange
        val expectedSize = 0
        val resultSize: Int
        val filteredToDos: List<ToDo>

        //Act
        //Clear the arrayList
        toDos.clear()
        // Test the function
        val sutFilter = FilterOperations<ToDo>(toDos)
        filteredToDos = sutFilter.getActive()
        resultSize = filteredToDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }
}