package com.th3pl4gu3.lifestyle.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.th3pl4gu3.lifestyle.core.lifestyle.Goal
import com.th3pl4gu3.lifestyle.core.lifestyle.ToBuy
import com.th3pl4gu3.lifestyle.core.lifestyle.ToDo
import com.th3pl4gu3.lifestyle.core.utils.DATABASE_NAME


@Database(entities = [ToDo::class, ToBuy::class, Goal::class], version = 1, exportSchema = false)
@TypeConverters(com.th3pl4gu3.lifestyle.database.Converters::class)
abstract class LifestyleDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val toDoDao: ToDoDao
    abstract val toBuyDao: ToBuyDao
    abstract val goalDao: GoalDao

    companion object {

        @Volatile
        private var INSTANCE: LifestyleDatabase? = null

        fun getInstance(context: Context): LifestyleDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LifestyleDatabase::class.java,
                        DATABASE_NAME
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}