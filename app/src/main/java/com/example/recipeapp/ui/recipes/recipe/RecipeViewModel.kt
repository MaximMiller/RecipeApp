package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.FavoritesRepository
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository = FavoritesRepository(application)
    private val _recipeState = MutableLiveData<RecipeState>()
    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    fun loadRecipe(recipeId: Int) {
        // TODO: load from network
        val recipe = STUB.getRecipeById(recipeId)
        val favorites = favoritesRepository.getFavorites()
        val isFavorite = favorites.contains(recipeId.toString())
        _recipeState.value =
            RecipeState(
                recipe = recipe,
                isFavorite = isFavorite,
                favorites = favorites,
            )
    }

    fun onFavoritesClicked(recipeId: Int) {
        val currentState = _recipeState.value ?: return
        val newFavorites = currentState.favorites.toMutableSet()

        if (currentState.isFavorite) {
            newFavorites.remove(recipeId.toString())
        } else {
            newFavorites.add(recipeId.toString())
        }
        _recipeState.value =
            currentState.copy(isFavorite = !currentState.isFavorite, favorites = newFavorites)
        favoritesRepository.saveFavorites(newFavorites)
    }

    fun onPortionsCountChanged(newPortionCount: Int) {
        val currentState = _recipeState.value ?: return
        _recipeState.value = currentState.copy(portionCount = newPortionCount)
    }

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val favorites: Set<String> = emptySet(),
        val portionCount: Int = 1
    )
}