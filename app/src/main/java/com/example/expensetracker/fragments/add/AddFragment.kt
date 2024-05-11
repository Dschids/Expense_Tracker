package com.example.expensetracker.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
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
            if (inputCheck()) {
                insertDataToDatabase()
            }
        }

        _add_binding.btnAfCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_mainListFragment)
        }

        _add_binding.myToolbar.inflateMenu(R.menu.main_menu)
        _add_binding.myToolbar.setTitle("Add Expense")
        var delete_item = _add_binding.myToolbar.menu.findItem(R.id.menu_delete)
        delete_item.isVisible = false

        return view
    }

    private fun insertDataToDatabase() {
        // get strings from edit text boxes
        val expCategory = _add_binding.spExpenseType.selectedItem.toString()
        val business = _add_binding.etBusName.text.toString()
        val amount = _add_binding.etExpAmount.text.toString().toDouble()
        val description = _add_binding.etExpDescr.text.toString()

        // create a User object
        val user = Expense(0, expCategory, business, amount, description)
        // add data to database
        aExpenseViewModel.addUser(user)
        Toast.makeText(requireContext(), "Entry added", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addFragment_to_mainListFragment)

    }

    private fun inputCheck(): Boolean {
        var busFilled = false
        var amountFilled = false
        var descrFilled = false

        val business = _add_binding.etBusName.text.toString()
        val amount = _add_binding.etExpAmount.text.toString()
        val description = _add_binding.etExpDescr.text.toString()

        if (business.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a business name.", Toast.LENGTH_SHORT).show()
            _add_binding.etBusName.requestFocus()
        }else {
            busFilled = true
            if (amount.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter the expense amount", Toast.LENGTH_SHORT).show()
                _add_binding.etExpAmount.requestFocus()
            } else {
                amountFilled = true
                if (description.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter an expense description", Toast.LENGTH_SHORT).show()
                    _add_binding.etExpDescr.requestFocus()
                } else {
                    descrFilled = true
                }

            }
        }
        return (busFilled && amountFilled && descrFilled)
    }

}

