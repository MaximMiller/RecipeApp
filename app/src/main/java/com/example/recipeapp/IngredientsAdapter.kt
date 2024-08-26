package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemIngredientBinding

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var quantity = 1
    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemIngredientBinding.bind(itemView)
        val tvTitleIngredient = binding.tvTitleIngredient
        val tvQuantityIngredient = binding.tvQuantityIngredient
        val tvUnitOfMeasure = binding.tvUnitOfMeasure
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = dataSet[position]
        val adjustedQuantity = (ingredient.quantity).toDouble() * quantity
        val displayQuantity = if ((adjustedQuantity % 1) == 0.0) {
            adjustedQuantity.toInt().toString()
        } else {
            String.format("%.1f", adjustedQuantity)
        }
        holder.tvTitleIngredient.text = dataSet[position].description
        holder.tvQuantityIngredient.text = displayQuantity
        holder.tvUnitOfMeasure.text = dataSet[position].unitOfMeasure
    }
}