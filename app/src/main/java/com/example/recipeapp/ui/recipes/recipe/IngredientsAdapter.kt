package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemIngredientBinding
import com.example.recipeapp.model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(
    private var dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var quantity = 1
    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(newDataSet: List<Ingredient>, progress: Int) {
        dataSet = newDataSet
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
        val adjustedQuantity = BigDecimal(ingredient.quantity) * BigDecimal(quantity)
        val displayQuantity = adjustedQuantity
            .setScale(1, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()
        holder.tvTitleIngredient.text = dataSet[position].description
        holder.tvQuantityIngredient.text = displayQuantity
        holder.tvUnitOfMeasure.text = dataSet[position].unitOfMeasure
    }
}