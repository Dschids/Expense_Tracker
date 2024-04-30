package com.example.expensetracker.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.model.Expense
import com.example.expensetracker.data.ExpenseDao

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val readAllData: LiveData<List<Expense>> = expenseDao.readAllData()

    suspend fun addUser(user: Expense){
        expenseDao.addExpense(user)
    }

    suspend fun updateUser(user: Expense){
        expenseDao.updateExpense(user)
    }

    suspend fun deleteUser(user: Expense){
        expenseDao.deleteExpense(user)
    }

    suspend fun deleteAllUsers(){
        expenseDao.deleteAllExpenses()
    }
}