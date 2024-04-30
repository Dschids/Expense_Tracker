package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.ExpenseDatabase
import com.example.expensetracker.model.Expense
import com.example.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/* ViewModel provides data to the ui and survives configuration changes. acts as a communication
center between the Repository and UI
*/
class ExpenseViewModel (application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Expense>>
    private val repository: ExpenseRepository

    init {
        val userDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

}
