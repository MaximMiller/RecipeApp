package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemMethodBinding

class MethodAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMethodBinding.bind(itemView)
        val tvNumberMethod = binding.tvNumberMethod
        val tvDescriptionMethod = binding.tvDescriptionMethod

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_method, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val index = "${position + 1}."
        holder.tvNumberMethod.text = index
        holder.tvDescriptionMethod.text = dataSet[position]
    }
}