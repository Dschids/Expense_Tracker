package com.example.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.model.Expense

/** new kotlin class, add abstract modifier before class after creation */
@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object{
        @Volatile
        private var _instance: ExpenseDatabase? = null

        // function checks if instance exists
        fun getDatabase(context: Context): ExpenseDatabase{
            val tempInstance = _instance
            // if tempInstance is not zero return tempInstance which we set equal to _instance
            if (tempInstance != null){
                return tempInstance
            }
            // if there is no instance we create a new instance of user_database
            synchronized(this){
                // builds the room database named user_database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                ).build()
                _instance = instance
                return instance
            }
        }
    }
}