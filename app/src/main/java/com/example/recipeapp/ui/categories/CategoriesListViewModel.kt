package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category

class CategoriesListViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _categories.value = STUB.getCategories()
    }

    fun getCategoryById(categoryId: Int): Category? {
        return _categories.value?.find { it.id == categoryId }
    }
}