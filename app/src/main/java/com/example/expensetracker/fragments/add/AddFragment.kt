package com.example.expensetracker.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentAddBinding
import com.example.expensetracker.model.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel

class AddFragment : Fragment() {

    private lateinit var _add_binding: FragmentAddBinding
    private lateinit var aExpenseViewModel: ExpenseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _add_binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = _add_binding.root

        aExpenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        _add_binding.btnAddExp.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        // get strings from edit text boxes
        val expCategory = _add_binding.spExpenseType.selectedItem.toString()
        val business = _add_binding.etBusName.text.toString()
        val amount = _add_binding.etExpAmount.text.toString().toDouble()
        val description = _add_binding.etExpDescr.text.toString()

        // if there is data in all three boxes
        if (inputCheck(expCategory, business, amount, description)){
            // create a User object
            val user = Expense(0, expCategory, business, amount, description)
            // add data to database
            aExpenseViewModel.addUser(user)
            Toast.makeText(requireContext(), "Entry added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_mainListFragment)
        }else{
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(expCategory: String, busName: String, amount: Double, descr: String): Boolean {
        return !(TextUtils.isEmpty(expCategory) && TextUtils.isEmpty(busName) && TextUtils.isEmpty(amount.toString()) && TextUtils.isEmpty(descr))
    }

}

