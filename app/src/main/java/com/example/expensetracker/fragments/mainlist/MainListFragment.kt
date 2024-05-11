package com.example.expensetracker.fragments.mainlist

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentAddBinding
import com.example.expensetracker.databinding.FragmentMainListBinding
import com.example.expensetracker.model.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel
import java.text.DecimalFormat


class MainListFragment : Fragment() {

    private lateinit var _main_list_binding: FragmentMainListBinding
    private lateinit var eclUserViewModel: ExpenseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _main_list_binding = FragmentMainListBinding.inflate(inflater, container, false)
        val view = _main_list_binding.root

        _main_list_binding.fabAddExp.setOnClickListener {
            findNavController().navigate(R.id.action_mainListFragment_to_addFragment)
        }

        // set the adapter to the ListAdapter I made
        val adapter = MainListAdapter()
        // create a recyclerview variable for ease of use (don't have to keep typing _binding.rvlist)
        val recyclerView = _main_list_binding.rvExpCatList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        eclUserViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        // observes for changes and updates the list when there are any
        eclUserViewModel.readAllData.observe(viewLifecycleOwner){
                expense ->
            // calls the setData function from ListAdapter
            adapter.setData(expense)
            calculateTotal(expense)
        }

        _main_list_binding.mainListToolbar.inflateMenu(R.menu.main_menu)
        _main_list_binding.mainListToolbar.setTitle("Expense Categories")
        _main_list_binding.mainListToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_delete -> {
                    deleteAllUsers()
                    true
                }
                else -> false
            }
        }

        return view
    }
    private fun calculateTotal(expenses: List<Expense>) {
        var total: Double = 0.00
        for (item in expenses) {
            total += item.amount
        }

        var dec = DecimalFormat("#,###.00")
        var formatted = dec.format(total)
        _main_list_binding.tvTotExpense.text = "$ $formatted"
    }

    private fun deleteAllUsers() {
        // create a alert dialog box
        val builder = AlertDialog.Builder(requireContext())
        // positive button appears as "yes", {_,_ shows that there will be two buttons
        builder.setPositiveButton("Yes"){_,_->
            eclUserViewModel.deleteAllUsers()
            Toast.makeText(requireContext(),
                "All entries deleted!",
                Toast.LENGTH_SHORT).show()
        }
        // negative button selection does nothing but get rid of alert box
        builder.setNegativeButton("No"){_,_ -> }
        // title of the alert box
        builder.setTitle("Delete all items?")
        // message inside alert box above positive and negative buttons
        builder.setMessage("Are you sure you want to delete all items?")
        // create and show the alert box
        builder.create().show()
    }
}