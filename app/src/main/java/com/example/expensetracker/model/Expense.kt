package com.example.expensetracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
class Expense (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val expenseType: String,
    val business: String,
    val amount: Double,
    val description: String
) {
}