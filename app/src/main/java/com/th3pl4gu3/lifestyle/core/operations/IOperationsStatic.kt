package com.th3pl4gu3.lifestyle.core.operations

import java.util.ArrayList

interface IOperationsStatic<L> {

    //Offline Operations
    fun getByIdOffline(id: String): L

    fun getAllOffline(): List<L>


    //Online Operations
    fun getByIdOnline(id: String): L

    fun getAllOnline(): List<L>
}