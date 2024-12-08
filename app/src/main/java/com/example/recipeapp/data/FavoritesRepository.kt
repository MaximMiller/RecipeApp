package com.example.recipeapp.data

import android.app.Application
import android.content.Context
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment.Companion.FAVORITES_KEY
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment.Companion.SHARED_PREFS_NAME

class FavoritesRepository(private val application: Application) {

    fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            application.getSharedPreferences(
                SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPrefs?.getStringSet(FAVORITES_KEY, emptySet())?.toMutableSet()
            ?: mutableSetOf()
    }

    fun saveFavorites(favorites: Set<String>) {
        val sharedPrefs = application.getSharedPreferences(
            SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
        sharedPrefs.edit().putStringSet(FAVORITES_KEY, favorites).apply()
    }
}