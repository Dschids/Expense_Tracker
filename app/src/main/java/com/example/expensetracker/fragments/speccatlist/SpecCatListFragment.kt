package com.example.expensetracker.fragments.speccatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentMainListBinding
import com.example.expensetracker.model.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel
import java.text.DecimalFormat

class SpecCatListFragment : Fragment() {

    private lateinit var _cat_list_binding: FragmentMainListBinding
    private lateinit var sclUserViewModel: ExpenseViewModel
    private val args by navArgs<SpecCatListFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _cat_list_binding = FragmentMainListBinding.inflate(inflater, container, false)
        val view = _cat_list_binding.root

        _cat_list_binding.tvTotExpenseTitle.text = "${args.expenseType.type} Total: "


        // set the adapter to the ListAdapter I made
        val adapter = SpecCatListAdapter()
        // create a recyclerview variable for ease of use (don't have to keep typing _binding.rvlist)
        val recyclerView = _cat_list_binding.rvExpCatList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        sclUserViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        // observes for changes and updates the list when there are any
        sclUserViewModel.readAllData.observe(viewLifecycleOwner){
                expense ->

                    // calls the setData function from ListAdapter
                    adapter.specCatSetData(expense, args.expenseType.type)
                    calculateTotal(expense, args.expenseType.type)
        }

        _cat_list_binding.fabAddExp.visibility

        return view
    }

    private fun calculateTotal(expenses: List<Expense>, selectedExpense: String) {
        var total: Double = 0.00
        for (item in expenses) {
            if (item.expenseType == selectedExpense) {
                total = +item.amount
            }
        }
        var dec = DecimalFormat("#,###.00")
        var formatted = dec.format(total)
        _cat_list_binding.tvTotExpense.text = "$ $formatted"
    }

}