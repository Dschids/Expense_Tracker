package com.example.expensetracker.fragments.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentUpdateBinding
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

        // onclick listener calls the updateItem function
        _update_binding.btnUpdateExp.setOnClickListener{
            // updateItem()
        }


        return view
    }
}