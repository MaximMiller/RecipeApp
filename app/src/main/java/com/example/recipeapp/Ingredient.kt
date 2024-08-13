package com.example.recipeapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    private val quantity: String,
    private val unitOfMeasure: String,
    private val description: String,
) : Parcelable
