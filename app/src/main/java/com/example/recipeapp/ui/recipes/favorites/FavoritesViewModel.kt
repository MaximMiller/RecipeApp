package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.FavoritesRepository
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe


class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository = FavoritesRepository(application)
    private val _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState>
        get() = _favoritesState

    fun loadFavoriteRecipes() {
        // TODO: load from network
        val favoritesIds =
            favoritesRepository.getFavorites().mapNotNull { it.toIntOrNull() }.toSet()
        val favoriteRecipes = if (favoritesIds.isNotEmpty()) {
            STUB.getRecipesByIds(favoritesIds)
        } else {
            emptyList()
        }
        _favoritesState.value = FavoritesState(favoriteRecipes)
    }

    data class FavoritesState(
        val favoriteRecipes: List<Recipe> = emptyList()
    )
}