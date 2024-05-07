package com.example.expensetracker.fragments.speccatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.CustomSpecCatRowBinding
import com.example.expensetracker.model.Expense
import java.text.DecimalFormat

class SpecCatListAdapter: RecyclerView.Adapter<SpecCatListAdapter.SpecCatViewHolder>() {

    private var expenseList = emptyList<Expense>()

    class SpecCatViewHolder(val _spec_cat_binding: CustomSpecCatRowBinding): RecyclerView.ViewHolder(_spec_cat_binding.root) {

        fun specCatBind(ourItem: Expense){


            var dec = DecimalFormat("#,###.00")
            var formatted = dec.format(ourItem.amount)

            _spec_cat_binding.tvExpBusName.text = ourItem.business
            _spec_cat_binding.tvExpAmount.text = formatted
            _spec_cat_binding.tvExpDescr.text = ourItem.description

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecCatViewHolder {
        val specCatItemBinding = CustomSpecCatRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecCatViewHolder(specCatItemBinding)
    }

    override fun onBindViewHolder(holder: SpecCatViewHolder, position: Int) {
        val currentItem = expenseList[position]

        holder.specCatBind(currentItem)

        holder._spec_cat_binding.specCatRowLayout.setOnClickListener() {
            // passing the data from the item in the list to the update fragment
            val action = SpecCatListFragmentDirections.actionSpecCatListFragmentToUpdateFragment(currentItem)
            // itemView comes from the ViewHolder library
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    fun specCatSetData (expense: List<Expense>, selectedExpense: String){
        var selectedExpenseList: ArrayList<Expense> = ArrayList()
        for (item in expense){
            if (item.expenseType == selectedExpense){
                selectedExpenseList.add(item)
            }
        }
        this.expenseList = selectedExpenseList
        notifyDataSetChanged()
    }
}