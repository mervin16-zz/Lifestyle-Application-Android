package com.th3pl4gu3.lifestyle.ui.home.section_dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.th3pl4gu3.lifestyle.core.enums.Priority
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.operations.FilterOperations
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.ui.home.LifestyleOpsViewModel

class DashboardViewModel(
    db: LifestyleDatabase,
    application: Application
) : LifestyleOpsViewModel(db, application) {

    val allGoalsSize: LiveData<String> = Transformations.map(goals) {
        it.size.toString()
    }

    val allToBuysSize: LiveData<String> = Transformations.map(toBuys) {
        it.size.toString()
    }

    val allToDosSize: LiveData<String> = Transformations.map(toDos) {
        it.size.toString()
    }

    val activeGoalsSize: LiveData<String> = Transformations.map(goals) {
        FilterOperations<Goal>(it).getActive().size.toString()
    }

    val activeToBuysSize: LiveData<String> = Transformations.map(toBuys) {
        FilterOperations<ToBuy>(it).getActive().size.toString()
    }

    val activeToDosSize: LiveData<String> = Transformations.map(toDos) {
        FilterOperations<ToDo>(it).getActive().size.toString()
    }

    val completedGoalsSize: LiveData<String> = Transformations.map(goals) {
        FilterOperations<Goal>(it).getCompleted().size.toString()
    }

    val completedToBuysSize: LiveData<String> = Transformations.map(toBuys) {
        FilterOperations<ToBuy>(it).getCompleted().size.toString()
    }

    val completedToDosSize: LiveData<String> = Transformations.map(toDos) {
        FilterOperations<ToDo>(it).getCompleted().size.toString()
    }

    val toDosPriority1: LiveData<Int> = Transformations.map(toDos) {
        calculatePercentage(FilterOperations<ToDo>(it).byToDoPriority(Priority.P1).size, it.size)
    }

    val toDosPriority2: LiveData<Int> = Transformations.map(toDos) {
        calculatePercentage(FilterOperations<ToDo>(it).byToDoPriority(Priority.P2).size, it.size)
    }

    val toDosPriority3: LiveData<Int> = Transformations.map(toDos) {
        calculatePercentage(FilterOperations<ToDo>(it).byToDoPriority(Priority.P3).size, it.size)
    }

    val toDosPriority4: LiveData<Int> = Transformations.map(toDos) {
        calculatePercentage(FilterOperations<ToDo>(it).byToDoPriority(Priority.P4).size, it.size)
    }

    val toBuysPriority1: LiveData<Int> = Transformations.map(toBuys) {
        calculatePercentage(FilterOperations<ToBuy>(it).byToBuyPriority(Priority.P1).size, it.size)
    }

    val toBuysPriority2: LiveData<Int> = Transformations.map(toBuys) {
        calculatePercentage(FilterOperations<ToBuy>(it).byToBuyPriority(Priority.P2).size, it.size)
    }

    val toBuysPriority3: LiveData<Int> = Transformations.map(toBuys) {
        calculatePercentage(FilterOperations<ToBuy>(it).byToBuyPriority(Priority.P3).size, it.size)
    }

    val toBuysPriority4: LiveData<Int> = Transformations.map(toBuys) {
        calculatePercentage(FilterOperations<ToBuy>(it).byToBuyPriority(Priority.P4).size, it.size)
    }

    private fun calculatePercentage(obtained: Int, total: Int): Int {
        return if(total == 0) 0 else (obtained * 100 / total)
    }
}