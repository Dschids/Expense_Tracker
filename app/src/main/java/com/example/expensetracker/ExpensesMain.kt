package com.example.expensetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.databinding.ActivityExpensesMainBinding


class ExpensesMain : AppCompatActivity() {

    private lateinit var _main_exp_binding: ActivityExpensesMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _main_exp_binding = ActivityExpensesMainBinding.inflate(layoutInflater)
        setContentView(_main_exp_binding.root)


    }
}