package com.example.recipeapp.ui.recipes.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.R
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.Constants
import com.example.recipeapp.ui.recipes.listrecipes.RecipesListAdapter
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentFavoritesBinding must not be null")
    private lateinit var adapter: RecipesListAdapter
    private var favoriteRecipes: List<Recipe> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        loadFavoriteRecipes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        adapter = RecipesListAdapter(favoriteRecipes)
        binding.rvFavoriteRecipes.adapter = adapter
        adapter.setOnClickListener { recipesId -> openRecipeByRecipeId(recipesId) }
    }

    private fun loadFavoriteRecipes() {
        val favoriteIds = getFavorites().mapNotNull { it.toIntOrNull() }
            .toSet()
        if (favoriteIds.isNotEmpty()) {
            favoriteRecipes = STUB.getRecipesByIds(favoriteIds)
            adapter.updateRecipes(favoriteRecipes)
            showRecipes()
        } else {
            showStub()
        }
    }

    private fun getFavorites(): Set<String> {
        val sharedPrefs = requireContext().getSharedPreferences(
            RecipeFragment.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
        return sharedPrefs.getStringSet(RecipeFragment.FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    private fun openRecipeByRecipeId(recipesId: Int) {
        val bundle = bundleOf(Constants.ARG_CATEGORY_ID to recipesId)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun showRecipes() {
        binding.rvFavoriteRecipes.visibility = View.VISIBLE
        binding.tvNoFavorites.visibility = View.GONE
    }

    private fun showStub() {
        binding.rvFavoriteRecipes.visibility = View.GONE
        binding.tvNoFavorites.visibility = View.VISIBLE
    }
}