package com.example.expensetracker.fragments.mainlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.CustomExpCatListBinding
import com.example.expensetracker.model.Expense

class MainListAdapter: RecyclerView.Adapter<MainListAdapter.MyViewHolder>() {

    private var expCatsList = arrayListOf("Housing", "Transportation", "Utilities", "Food", "Entertainment", "Other")
    private var expenseList = emptyList<Expense>()
    class MyViewHolder(val _binding: CustomExpCatListBinding): RecyclerView.ViewHolder(_binding.root) {
        fun bind(ourItem: String){
            _binding.tvExpCat.text = ourItem
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
        holder.bind(currentItem)

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
}