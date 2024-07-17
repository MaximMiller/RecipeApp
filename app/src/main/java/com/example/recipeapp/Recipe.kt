package com.example.recipeapp

data class Recipe(
    private val id: Int,
    private val title: String,
    private val ingredients: List<Ingredient>,
    private val method: List<String>,
)