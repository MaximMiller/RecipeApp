package com.example.recipeapp.ui.recipes.listrecipes

import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

class RecipeListViewModel : ViewModel() {

    data class RecipesListState(
        val category: Category? = null,
        val recipes: List<Recipe> = emptyList()
    )
}