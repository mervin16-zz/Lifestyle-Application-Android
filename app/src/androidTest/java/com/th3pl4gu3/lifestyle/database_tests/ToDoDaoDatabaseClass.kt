package com.th3pl4gu3.lifestyle.database_tests


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.th3pl4gu3.lifestyle.core.enums.LifestyleItem
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
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
class ToDoDaoDatabaseClass {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var toDoDao: ToDoDao
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
        toDoDao = db.toDoDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //To Do's Tests
    @Test
    @Throws(Exception::class)
    fun insertAndGet_Unmodified() {

        //Arrange
        val toDo = ToDo()
        val id = toDo.id
        val dateNow = toDo.dateAdded
        val expectedId = id
        val expectedTitle = "Title"
        val expectedCategory = "Category"
        val expectedPriority = Priority.P4
        val expectedDateAdded = dateNow
        val expectedTypeValue = LifestyleItem.TO_DO.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultPriority: Priority
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert into db
        toDoDao.insert(toDo)
        //Get the item
        val sutTodo = toDoDao.get(id)

        resultTypeValue = sutTodo!!.type
        resultId = sutTodo.id
        resultTitle = sutTodo.title
        resultCategory = sutTodo.category
        resultPriority = sutTodo.priority
        resultDateAdded = sutTodo.dateAdded
        resultDateCompleted = sutTodo.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
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
        val dateCompleted = Calendar.getInstance()
        dateCompleted.add(Calendar.DAY_OF_YEAR, 10)

        val toDo = ToDo(title = title, category = category)
        //Mark this to do as completed
        toDo.dateCompleted = dateCompleted

        val id = toDo.id
        val dateNow = toDo.dateAdded

        val expectedId = id
        val expectedTitle = title
        val expectedCategory = category
        val expectedPriority = Priority.P4
        val expectedDateAdded = dateNow
        val expectedDateCompleted = dateCompleted
        val expectedTypeValue = LifestyleItem.TO_DO.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultPriority: Priority
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert into db
        toDoDao.insert(toDo)
        //Get the item
        val sutTodo = toDoDao.get(id)

        resultTypeValue = sutTodo!!.type
        resultId = sutTodo.id
        resultTitle = sutTodo.title
        resultCategory = sutTodo.category
        resultPriority = sutTodo.priority
        resultDateAdded = sutTodo.dateAdded
        resultDateCompleted = sutTodo.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
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

        val oldToDo = ToDo()
        val newToDo = oldToDo.copy()

        //set the IDs
        oldToDo.id = testID
        newToDo.id = testID

        //Make changes to new To Do
        newToDo.title = title
        newToDo.category = category
        newToDo.priority = priority

        val expectedId = testID
        val expectedTitle = title
        val expectedCategory = category
        val expectedPriority = priority
        val expectedDateAdded = newToDo.dateAdded
        val expectedTypeValue = LifestyleItem.TO_DO.value

        val resultTypeValue: Int
        val resultId: String
        val resultTitle: String
        val resultCategory: String
        val resultPriority: Priority
        val resultDateAdded: Calendar
        val resultDateCompleted: Calendar?

        //Act
        //Insert an unmodified To Do into db
        toDoDao.insert(oldToDo)
        //Update To Do into db
        toDoDao.update(newToDo)
        //Get the new To Do
        val sutTodo = toDoDao.get(testID)

        resultTypeValue = sutTodo!!.type
        resultId = sutTodo.id
        resultTitle = sutTodo.title
        resultCategory = sutTodo.category
        resultPriority = sutTodo.priority
        resultDateAdded = sutTodo.dateAdded
        resultDateCompleted = sutTodo.dateCompleted

        //Assert
        assertEquals(expectedTypeValue, resultTypeValue)
        assertEquals(expectedId, resultId)
        assertEquals(expectedTitle, resultTitle)
        assertEquals(expectedCategory, resultCategory)
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
        val sutTodo = toDoDao.get(id)

        //Assert
        assertNull(sutTodo)
    }

    @Test
    @Throws(Exception::class)
    fun getAll_NoDataExists() {

        //Arrange

        //Act
        val sutTodo = toDoDao.getAllToDos().value

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
        var toDo:ToDo



        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toDo = ToDo(title = title)
            toDoDao.insert(toDo)
        }

        //Get the item
        val sutTodo = LiveDataTestUtil.getValue(toDoDao.getAllToDos())
        resultSize = sutTodo.size

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
        var toDo: ToDo
        val resultToDo: ToDo?
        val toDos: List<ToDo>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toDo = ToDo(title = title)

            //Take a number at random
            if(x == 4){
                idToRemove = toDo.id
            }

            toDoDao.insert(toDo)
        }

        //Remove the item
        toDoDao.remove(idToRemove)
        //Try to get all objects
        toDos = LiveDataTestUtil.getValue(toDoDao.getAllToDos())
        //Try to get the object
        resultToDo = toDoDao.get(idToRemove)

        resultSize = toDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
        assertNull(resultToDo)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeOne_NoDataExists() {

        //Arrange
        var title = "Do the dishes"
        var idToRemove = "null"
        val expectedSize = 11
        val resultSize: Int
        var toDo: ToDo
        val resultToDo: ToDo?
        val toDos: List<ToDo>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toDo = ToDo(title = title)


            //Take a number at random
            if(x == 12){
                idToRemove = toDo.id
            }

            toDoDao.insert(toDo)
        }

        //Remove the item
        toDoDao.remove(idToRemove)
        //Try to get all objects
        toDos = LiveDataTestUtil.getValue(toDoDao.getAllToDos())
        //Try to get the object
        resultToDo = toDoDao.get(idToRemove)

        resultSize = toDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
        assertNull(resultToDo)
    }

    @Test
    @Throws(InterruptedException::class)
    fun removeAll_DataExists() {

        //Arrange
        var title = "Do the dishes"
        val expectedSize = 0
        val resultSize: Int
        var toDo: ToDo
        val toDos: List<ToDo>?


        //Act
        //Add several to do's in a for loop
        for(x in 0..10){
            title += x
            toDo = ToDo(title = title)
            toDoDao.insert(toDo)
        }

        //Remove the item
        toDoDao.removeAll()
        //Try to get all objects
        toDos = LiveDataTestUtil.getValue(toDoDao.getAllToDos())

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
        val toDos: List<ToDo>?


        //Act
        toDoDao.removeAll()
        //Try to get all objects
        toDos = LiveDataTestUtil.getValue(toDoDao.getAllToDos())
        resultSize = toDos.size

        //Assert
        assertEquals(expectedSize, resultSize)
    }
}