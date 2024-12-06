package com.example.recipeapp.ui.recipes.listrecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemRecipesBinding
import com.example.recipeapp.model.Recipe

class RecipesListAdapter(
    private var dataSet: List<Recipe>,
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {
    fun interface OnItemClickListener {
        fun onItemClick(recipesId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecipesBinding.bind(itemView)
        val ivRecipesImage = binding.ivRecipesImage
        val tvHeadingTitleRecipes = binding.tvHeadingTitleRecipes
        val cvItemRecipes = binding.cvItemRecipes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipes, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipesId = dataSet[position].id
        holder.cvItemRecipes.setOnClickListener { itemClickListener?.onItemClick(recipesId) }
        holder.tvHeadingTitleRecipes.text = dataSet[position].title
        val imageUrl = dataSet[position].imageUrl
        Glide.with(holder.itemView.context).load("file:///android_asset/$imageUrl")
            .into(holder.ivRecipesImage)
    }

    fun updateRecipes(newDataSet: List<Recipe>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }

}