package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment.Companion.FAVORITES_KEY
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment.Companion.SHARED_PREFS_NAME

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val _recipeState = MutableLiveData<RecipeState>()
    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    init {
        Log.i("tag", "RecipeViewModel init")
    }

    fun loadRecipe(recipeId: Int) {
        // TODO: load from network
        val recipe = STUB.getRecipeById(recipeId)
        val favorites = getFavorites()
        val isFavorite = favorites.contains(recipeId.toString())
        val imageUrl = "file:///android_asset/${recipe?.imageUrl}"
        _recipeState.value =
            RecipeState(
                recipe = recipe,
                isFavorite = isFavorite,
                favorites = favorites,
                imageUrl = imageUrl
            )
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            getApplication<Application>().getSharedPreferences(
                SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPrefs?.getStringSet(FAVORITES_KEY, emptySet())?.toMutableSet()
            ?: mutableSetOf()
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
        saveFavorites(newFavorites)
    }

    private fun saveFavorites(favorites: Set<String>) {
        val sharedPrefs = getApplication<Application>().getSharedPreferences(
            SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
        sharedPrefs.edit().putStringSet(FAVORITES_KEY, favorites).apply()
    }

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val favorites: Set<String> = emptySet(),
        val portionCount: Int = 1,
        val imageUrl: String? = null
    )
}