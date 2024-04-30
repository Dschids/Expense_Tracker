package com.example.expensetracker.fragments.mainlist

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.CustomExpCatListBinding
import com.example.expensetracker.model.Expense
import java.text.DecimalFormat

var grandTotal: Double = 0.00
class MainListAdapter: RecyclerView.Adapter<MainListAdapter.MyViewHolder>() {

    private var expCatsList = arrayListOf("Housing", "Transportation", "Utilities", "Food", "Entertainment", "Other")
    private var expenseList = emptyList<Expense>()

    class MyViewHolder(val _binding: CustomExpCatListBinding): RecyclerView.ViewHolder(_binding.root) {

        fun bind(ourItem: String, ourAmount: Double){
            var dec = DecimalFormat("#,###.00")
            var formatted = dec.format(ourAmount)
            grandTotal += ourAmount
            Log.d(TAG, "bind: $grandTotal")
            _binding.tvExpCat.text = ourItem
            _binding.tvTotalAmount.text = "$ $formatted"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = CustomExpCatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return expCatsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = expCatsList[position]
        var currentAmount = getTotal(currentItem)

        holder.bind(currentItem, currentAmount)


        // holder._binding.expCatLayout.setOnClickListener{
            // passing the data from the item in the list to the update fragment
            // val action = MainListFragmentDirections.actionMainListFragmentToUpdateFragment(currentItem)
            // itemView comes from the ViewHolder library
            // holder.itemView.findNavController().navigate(action)
        }

    fun setData (expense: List<Expense>){
        this.expenseList = expense
        notifyDataSetChanged()
    }

    fun getTotal(category: String): Double{
        var total = 0.00
        for (item in expenseList){
            if (item.expenseType == category){
                total += item.amount
            }

        }
        return total
    }
}