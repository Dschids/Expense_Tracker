package com.example.expensetracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "expense_table")
class Expense (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val expenseType: String,
    val business: String,
    val amount: Double,
    val description: String
): Parcelable {
}