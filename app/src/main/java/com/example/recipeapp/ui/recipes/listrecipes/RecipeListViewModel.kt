package com.example.recipeapp.ui.recipes.listrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

class RecipeListViewModel : ViewModel() {
    private val _recipesListState = MutableLiveData<RecipesListState>()
    val recipesListState: LiveData<RecipesListState>
        get() = _recipesListState

    fun initBundleData(categoryId: Int) {
        loadListRecipe(categoryId)
    }

    private fun loadListRecipe(categoryId: Int) {
        val category = STUB.getCategories().find { it.id == categoryId }
        val recipes = STUB.getRecipesByCategoryId(categoryId)
        _recipesListState.value = RecipesListState(recipes = recipes, category = category)

    }

    data class RecipesListState(
        val category: Category? = null,
        val recipes: List<Recipe> = emptyList()
    )
}