package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val favorites: Set<String> = emptySet()
    )
}