package com.example.expensetracker.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentUpdateBinding
import com.example.expensetracker.fragments.mainlist.MainListFragmentDirections
import com.example.expensetracker.model.Expense
import com.example.expensetracker.model.ExpenseTypes
import com.example.expensetracker.viewmodel.ExpenseViewModel
import java.text.DecimalFormat

class UpdateFragment : Fragment() {

    private lateinit var _update_binding: FragmentUpdateBinding
    private lateinit var ufUpdateViewModel: ExpenseViewModel
    val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _update_binding = FragmentUpdateBinding.inflate(inflater, container, false)
        // this is the value we return, could just put return _updateBinding.root, tomato potato
        val view = _update_binding.root
        var spIndex  = 0
        ufUpdateViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        when (args.expenseItem.expenseType) {
            "Housing" -> spIndex = 0
            "Transportation" -> spIndex = 1
            "Utilities" -> spIndex = 2
            "Food" -> spIndex = 3
            "Entertainment" -> spIndex = 4
            "Other" -> spIndex = 5
        }

        var rawDouble = args.expenseItem.amount
        var dec = DecimalFormat("#,###.00")
        var formatted = dec.format(rawDouble)

        _update_binding.spUpdateExpenseType.setSelection(spIndex)
        _update_binding.etUpdateBusName.setText(args.expenseItem.business)
        _update_binding.etUpdateExpAmount.setText(formatted)
        _update_binding.etUpdateExpDescr.setText(args.expenseItem.description)

        // use this val to send a safe arg back to the specific category list, with the seleced expense type
        val expenseType = _update_binding.spUpdateExpenseType.selectedItem.toString()

        // onclick listener calls the updateItem function
        _update_binding.btnUpdateExp.setOnClickListener{
            if (inputCheck()) {
                updateItem()
            }
        }

        // onclick listener for cancel button navigates back to the specific category fragment
        _update_binding.btnUpCancel.setOnClickListener {
            // creates a safe arg to be sent to the specific category list, containing the category selection
            val action = UpdateFragmentDirections.actionUpdateFragmentToSpecCatListFragment(ExpenseTypes(expenseType))
            findNavController().navigate(action)
        }

        // inflating the menu from our menu item
        _update_binding.updateToolbar.inflateMenu(R.menu.main_menu)
        // set the toolbar title
        _update_binding.updateToolbar.setTitle("Update Expense")
        // setting an on click listener for the menu items
        _update_binding.updateToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                // when the delete menu item is clicked run deleteUser
                R.id.menu_delete -> {
                    deleteUser()
                    true
                }
                else -> false
            }
        }
        return view
    }

    private fun updateItem(){
        // create variables from input fields, make sure to have toString()/toInt()
        val expenseType = _update_binding.spUpdateExpenseType.selectedItem.toString()
        val busName = _update_binding.etUpdateBusName.text.toString()
        val expAmount = _update_binding.etUpdateExpAmount.text.toString().toDouble()
        val expDescr = _update_binding.etUpdateExpDescr.text.toString()

        // create a User object, use args to get the id of the current user
        val updateUser = Expense(args.expenseItem.id, expenseType, busName, expAmount, expDescr)
        // calls updateUser function from UserViewModel
        ufUpdateViewModel.updateUser(updateUser)
        Toast.makeText(requireContext(), "Entry updated", Toast.LENGTH_SHORT).show()
        /* if you copy paste this check from the add fragment make sure you change
        action_addFragment to action_updateFragment or the app crashes
         */
        val action = UpdateFragmentDirections.actionUpdateFragmentToSpecCatListFragment(ExpenseTypes(expenseType))
        findNavController().navigate(action)

    }


    private fun inputCheck(): Boolean {
        var busFilled = false
        var amountFilled = false
        var descrFilled = false

        val business = _update_binding.etUpdateBusName.text.toString()
        val amount = _update_binding.etUpdateExpAmount.text.toString()
        val description = _update_binding.etUpdateExpDescr.text.toString()

        if (business.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a business name.", Toast.LENGTH_SHORT).show()
            _update_binding.etUpdateBusName.requestFocus()
        }else {
            busFilled = true
            if (amount.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter the expense amount", Toast.LENGTH_SHORT).show()
                _update_binding.etUpdateExpAmount.requestFocus()
            } else {
                amountFilled = true
                if (description.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter an expense description", Toast.LENGTH_SHORT).show()
                    _update_binding.etUpdateExpDescr.requestFocus()
                } else {
                    descrFilled = true
                }
            }
        }
        return (busFilled && amountFilled && descrFilled)
    }

    private fun deleteUser() {
        // create a alert dialog box
        val builder = AlertDialog.Builder(requireContext())
        // positive button appears as "yes", {_,_ shows that there will be two buttons
        builder.setPositiveButton("Yes"){_,_->
            ufUpdateViewModel.deleteUser(args.expenseItem)
            Toast.makeText(requireContext(),
                "Expense at ${args.expenseItem.business} deleted!",
                Toast.LENGTH_SHORT).show()
            val expenseType = _update_binding.spUpdateExpenseType.selectedItem.toString()
            val action = UpdateFragmentDirections.actionUpdateFragmentToSpecCatListFragment(ExpenseTypes(expenseType))
            findNavController().navigate(action)
        }
        // negative button selection does nothing but get rid of alert box
        builder.setNegativeButton("No"){_,_ -> }
        // title of the alert box
        builder.setTitle("Delete expense at ${args.expenseItem.business}?")
        // message inside alert box above positive and negative buttons
        builder.setMessage("Are you sure you want to delete ${args.expenseItem.business}?")
        // create and show the alert box
        builder.create().show()
    }
}