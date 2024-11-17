package com.example.recipeapp.ui.recipes.favorites

import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class FavoritesViewModel: ViewModel() {

    data class FavoritesState(
        val favoriteRecipes: List<Recipe> = emptyList(),
        val hasFavorites: Boolean = false
    )
}