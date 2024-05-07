package com.example.expensetracker.fragments.mainlist

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.CustomTotalExpRowBinding
import com.example.expensetracker.model.Expense
import com.example.expensetracker.model.ExpenseTypes
import java.text.DecimalFormat

var grandTotal: Double = 0.00
class MainListAdapter: RecyclerView.Adapter<MainListAdapter.MyViewHolder>() {

    private var expCatsList = arrayListOf<ExpenseTypes>(ExpenseTypes("Housing"), ExpenseTypes("Transportation"),
        ExpenseTypes("Utilities"), ExpenseTypes("Food"), ExpenseTypes("Entertainment"), ExpenseTypes("Other"))
    private var expenseList = emptyList<Expense>()

    class MyViewHolder(val _binding: CustomTotalExpRowBinding): RecyclerView.ViewHolder(_binding.root) {

        fun bind(ourItem: ExpenseTypes, ourAmount: Double){
            var dec = DecimalFormat("#,###.00")
            var formatted = dec.format(ourAmount)
            grandTotal += ourAmount
            Log.d(TAG, "bind: $grandTotal")
            _binding.tvExpCat.text = ourItem.type
            _binding.tvTotalAmount.text = "$ $formatted"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = CustomTotalExpRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return expCatsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = expCatsList[position]
        var currentAmount = getTotal(currentItem)

        holder.bind(currentItem, currentAmount)


        holder._binding.expCatLayout.setOnClickListener {
            // passing the data from the item in the list to the update fragment
            val action = MainListFragmentDirections.actionMainListFragmentToSpecCatListFragment(currentItem)
            // itemView comes from the ViewHolder library
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData (expense: List<Expense>){
        this.expenseList = expense
        notifyDataSetChanged()
    }

    fun getTotal(category: ExpenseTypes): Double{
        var total = 0.00
        for (item in expenseList){
            if (item.expenseType == category.type){
                total += item.amount
            }

        }
        return total
    }
}