package com.th3pl4gu3.lifestyle.core.operations

import com.th3pl4gu3.lifestyle.core.lifestyle.Goal

class GoalOperations(var goals: ArrayList<Goal> = ArrayList()) : IOperations<Goal> {

    companion object : IOperationsStatic<Goal>{

        override fun getByIdOffline(id: String): Goal {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAllOffline(): List<Goal> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getByIdOnline(id: String): Goal {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAllOnline(): List<Goal> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


    override fun getRecentlyAdded(): List<Goal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecentlyCompleted(): List<Goal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOlderPendingItems(): List<Goal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //Offline Database Operations
    override fun removeAllOffline() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //Online Database Operations
    override fun removeAllOnline() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}