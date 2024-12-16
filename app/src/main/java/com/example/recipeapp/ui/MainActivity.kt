package com.example.recipeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for ActivityMainBinding most not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFavorite.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.favoritesFragment,
                null,
                navOptions {
                    launchSingleTop = true
                })
        }
        binding.btnCategories.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.categoriesListFragment,
                null,
                navOptions {
                    launchSingleTop = true
                })
        }
    }
}