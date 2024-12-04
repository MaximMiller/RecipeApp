package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemMethodBinding
import com.example.recipeapp.model.Ingredient

class MethodAdapter(
    private var dataSet: List<String>
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun updateMethod(newDataSet: List<String>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMethodBinding.bind(itemView)
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
        val methodDescription = String.format(
            holder.itemView.context.getString(R.string.item_description_format),
            position + 1,
            dataSet[position]
        )

        holder.tvDescriptionMethod.text = methodDescription

    }
}