package com.example.recipeapp.ui.recipes.listrecipes

import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeListViewModel: ViewModel() {

    data class RecipesListState(
        val categoryId: Int? = null,
        val categoryName: String? = null,
        val categoryImageUrl: String? = null,
        val recipes: List<Recipe> = emptyList()
    )
}