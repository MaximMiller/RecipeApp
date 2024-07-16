package com.example.recipeapp

data class Ingredient(
    private val quantity:Double,
    private val unitOfMeasure:String,
    private val description:String,
) {
}